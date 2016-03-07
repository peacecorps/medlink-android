package gov.peacecorps.medlinkandroid.rest;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import gov.peacecorps.medlinkandroid.BuildConfig;
import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.HmacSigner;
import okio.Buffer;
import okio.ByteString;
import timber.log.Timber;

public class HmacInterceptor implements Interceptor {

    private final static String CONTENT_TYPE_APPLICATION_JSON = "application/json; charset=UTF-8";
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

    private final AppSharedPreferences appSharedPreferences;
    private final HmacSigner hmacSigner;

    public HmacInterceptor(HmacSigner hmacSigner, AppSharedPreferences appSharedPreferences) {
        this.hmacSigner = hmacSigner;
        this.appSharedPreferences = appSharedPreferences;
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (appSharedPreferences.hasUser()) {
            return chain.proceed(augmentRequestWithAuthenticationHeaders(request));
        }

        return chain.proceed(request);
    }

    private Request augmentRequestWithAuthenticationHeaders(Request request) throws IOException {
        String md5ContentHash = getContentMD5Hash(request);
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String path = request.url().getPath();

        String authHeader = buildAuthenticationHeader(path, md5ContentHash, date);

        Request augmentedRequest = request
                .newBuilder()
                .addHeader("Content-Type", CONTENT_TYPE_APPLICATION_JSON)
                .addHeader("Content-MD5", md5ContentHash)
                .addHeader("Date", date)
                .addHeader("Authorization", authHeader)
                .build();

        if (BuildConfig.DEBUG) {
            logHeaders(augmentedRequest);
        }

        return augmentedRequest;
    }

    private String buildAuthenticationHeader(String path, String md5ContentHash, String date) {
        return "APIAuth " + hmacSigner.getHmac(buildCanonicalString(path, md5ContentHash, date));
    }

    private String buildCanonicalString(String path, String md5ContentHash, String date) {
        String canonicalString = CONTENT_TYPE_APPLICATION_JSON + "," + md5ContentHash + "," + path + "," + date;;
        Timber.d("Canonical string: %s", canonicalString);

        return canonicalString;
    }

    private void logHeaders(Request request) {
        Headers headers = request.headers();
        for (String headerName : headers.names()) {
            Timber.d("%s: %s", headerName, headers.get(headerName));
        }
    }

    private String getContentMD5Hash(Request request) throws IOException {
        if (request.body() != null) {
            final Buffer buffer = new Buffer();
            request.body().writeTo(buffer);

            byte[] payloadBytes = buffer.readByteArray();

            Timber.d("Payload: %s", new String(payloadBytes));

            return ByteString.of(payloadBytes).md5().base64();
        } else {
            return "";
        }
    }
}
