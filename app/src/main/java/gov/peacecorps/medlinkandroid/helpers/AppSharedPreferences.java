package gov.peacecorps.medlinkandroid.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.peacecorps.medlinkandroid.data.models.Supply;
import gov.peacecorps.medlinkandroid.data.models.User;
import gov.peacecorps.medlinkandroid.helpers.exceptions.SupplyNotFoundException;
import gov.peacecorps.medlinkandroid.helpers.exceptions.UserNotFoundException;

public class AppSharedPreferences {

    private ObjectMapper objectMapper;
    private Map<Integer, Supply> suppliesMap;

    private enum PreferenceKey {
        USER,
        SUPPLIES_LIST;
    }

    private SharedPreferences sharedPreferences;

    public AppSharedPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        objectMapper = new ObjectMapper();
        suppliesMap = new HashMap<>();
    }

    private void setString(PreferenceKey preferenceKey, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceKey.name(), value).apply();
    }

    private String getString(PreferenceKey preferenceKey) {
        return sharedPreferences.getString(preferenceKey.name(), "");
    }

    public void setUser(User user) {
        try {
            setString(PreferenceKey.USER, objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
        }
    }

    public User getUser() throws UserNotFoundException {
        try {
            return objectMapper.readValue(getString(PreferenceKey.USER), User.class);
        } catch (IOException e) {
        }

        throw new UserNotFoundException();
    }

    public boolean hasUser() {
        try {
            getUser();
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    public void setSupplies(List<Supply> supplies) {
        try {
            setString(PreferenceKey.SUPPLIES_LIST, objectMapper.writeValueAsString(supplies));
        } catch (JsonProcessingException e) {
        }
    }

    public Supply getSupply(Integer id) {
        try {
            if (suppliesMap.isEmpty()) {
                initializeSupplyMap(); //save time for subsequent lookups
            }

            return suppliesMap.get(id);
        } catch (IOException e) {
        }

        throw new SupplyNotFoundException();
    }

    private void initializeSupplyMap() throws IOException {
        List<Supply> suppliesList = objectMapper.readValue(getString(PreferenceKey.SUPPLIES_LIST), new TypeReference<List<Supply>>(){});
        for (Supply supply : suppliesList) {
            suppliesMap.put(supply.getId(), supply);
        }
    }

    public void deleteUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PreferenceKey.USER.name()).apply();
        editor.remove(PreferenceKey.SUPPLIES_LIST.name()).apply();
    }
}
