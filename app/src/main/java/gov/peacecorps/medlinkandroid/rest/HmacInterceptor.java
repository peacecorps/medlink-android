package gov.peacecorps.medlinkandroid.rest;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import gov.peacecorps.medlinkandroid.helpers.AppSharedPreferences;
import gov.peacecorps.medlinkandroid.helpers.HmacSigner;
import okio.Buffer;

public class HmacInterceptor implements Interceptor {

    private final static String CONTENT_TYPE_APPLICATION_JSON = "application/json";
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
            return chain.proceed(augmentRequestWithHeaders(request));
        }

        return chain.proceed(request);
    }

    private Request augmentRequestWithHeaders(Request request) throws IOException {
        String md5ContentHash = getContentMD5Hash(request);
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String plainText = CONTENT_TYPE_APPLICATION_JSON + "," + md5ContentHash + "," + request.url().getPath() + "," + date;
        String authHeader = "APIAuth " + hmacSigner.getHmac(plainText);

        return request
                .newBuilder()
                .addHeader("Content-Type", CONTENT_TYPE_APPLICATION_JSON)
                .addHeader("Content-MD5", md5ContentHash)
                .addHeader("Date", date)
                .addHeader("Authorization", authHeader)
                .build();
    }

    private String getContentMD5Hash(Request request) throws IOException {
        if (request.body() != null) {
            final Buffer buffer = new Buffer();
            request.body().writeTo(buffer);

            return hmacSigner.getMD5Hash(buffer.readByteArray());
        } else {
            return "";
        }
    }
}
