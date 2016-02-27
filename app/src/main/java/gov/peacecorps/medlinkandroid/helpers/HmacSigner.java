package gov.peacecorps.medlinkandroid.helpers;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSigner {

    private AppSharedPreferences sharedPreferences;

    public HmacSigner(AppSharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getMD5Hash(byte[] inputBytes){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(inputBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }

            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }

        return "";
    }

    public String getHmac(String input) {
        try {
            return prependUserId(getDigest(input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private String prependUserId(byte[] digest) {
        return sharedPreferences.getUser().getUserId() + ":" + Base64.encodeToString(digest, Base64.NO_WRAP);
    }

    private byte[] getDigest(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        return buildMac().doFinal(input.getBytes());
    }

    private Mac buildMac() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA1");
        String secretKey = sharedPreferences.getUser().getSecretKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(secretKeySpec);

        return mac;
    }

}
