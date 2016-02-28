package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import gov.peacecorps.medlinkandroid.data.models.User;
import gov.peacecorps.medlinkandroid.helpers.exceptions.UserNotFoundException;

public class AppSharedPreferences {

    private enum PreferenceKey {
        USER;
    }

    private SharedPreferences sharedPreferences;

    public AppSharedPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private void setString(PreferenceKey preferenceKey, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceKey.name(), value).apply();
    }

    private String getString(PreferenceKey preferenceKey){
        return sharedPreferences.getString(preferenceKey.name(), "");
    }

    public void setUser(User user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            setString(PreferenceKey.USER, objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
        }
    }

    public User getUser() throws UserNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(getString(PreferenceKey.USER), User.class);
        } catch (IOException e) {
        }

        throw new UserNotFoundException();
    }

    public void deleteUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PreferenceKey.USER.name()).apply();
    }
}
