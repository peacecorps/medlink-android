package gov.peacecorps.medlinkandroid.helpers;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import timber.log.Timber;

public class HmacSigner {

    private static final String TAG = HmacSigner.class.getSimpleName();
    private AppSharedPreferences sharedPreferences;

    public HmacSigner(AppSharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getHmac(String input) {
        try {
            return prependUserId(getHmacSHA1Digest(input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private String prependUserId(byte[] digest) {
        return sharedPreferences.getUser().getUserId() + ":" + Base64.encodeToString(digest, Base64.NO_WRAP);
    }

    private byte[] getHmacSHA1Digest(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        return buildSHA1Mac().doFinal(input.getBytes());
    }

    private Mac buildSHA1Mac() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA1");
        String secretKey = sharedPreferences.getUser().getSecretKey();

        Timber.d("Secret key: %s", secretKey);

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("utf-8"), "RAW");
        mac.init(secretKeySpec);

        return mac;
    }

}
