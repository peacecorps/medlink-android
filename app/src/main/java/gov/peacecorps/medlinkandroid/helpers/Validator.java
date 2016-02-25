package gov.peacecorps.medlinkandroid.helpers;

import android.util.Patterns;

public class Validator {
    public static boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
