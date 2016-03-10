package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {

    private SharedPreferences sharedPreferences;

    public AppSharedPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void remove(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key).apply();
    }
}
