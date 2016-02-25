package gov.peacecorps.medlinkandroid.helpers;

import android.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSigner {

    private AppSharedPreferences sharedPreferences;

    public HmacSigner(AppSharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getEncryptedString(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKeySpec = new SecretKeySpec(sharedPreferences.getUser().getSecretKey().getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(input.getBytes());

            String enc = new String(digest);

            return Base64.encodeToString(enc.getBytes(), Base64.DEFAULT);
        } catch (Exception e) {
        }

        return null;
    }

}
