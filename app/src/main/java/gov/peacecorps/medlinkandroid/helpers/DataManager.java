package gov.peacecorps.medlinkandroid.helpers;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import gov.peacecorps.medlinkandroid.data.models.Supply;
import gov.peacecorps.medlinkandroid.data.models.User;
import gov.peacecorps.medlinkandroid.helpers.exceptions.SupplyNotFoundException;
import gov.peacecorps.medlinkandroid.helpers.exceptions.UserNotFoundException;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;

public class DataManager {
    private AppSharedPreferences appSharedPreferences;
    private ObjectMapper objectMapper;
    private Map<Integer, Supply> suppliesMap;

    private enum PreferenceKey {
        USER,
        SUPPLIES_LIST,
        UNSUBMITTED_REQUESTS;
    }

    public DataManager(AppSharedPreferences appSharedPreferences) {
        this.appSharedPreferences = appSharedPreferences;
        objectMapper = new ObjectMapper();
        suppliesMap = new HashMap<>();
    }

    public void setUser(User user) {
        setString(PreferenceKey.USER, user);
    }

    public User getUser() throws UserNotFoundException {
        try {
            return objectMapper.readValue(appSharedPreferences.getString(PreferenceKey.USER.name()), User.class);
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
        setString(PreferenceKey.SUPPLIES_LIST, supplies);
    }

    public Supply getSupply(Integer id) {
        if (suppliesMap.isEmpty()) {
            initializeSupplyMap(); //save time for subsequent lookups
        }

        Supply supply = suppliesMap.get(id);
        if (supply == null)
            throw new SupplyNotFoundException();

        return supply;
    }

    private void initializeSupplyMap() {
        List<Supply> suppliesList = getSupplies();
        for (Supply supply : suppliesList) {
            suppliesMap.put(supply.getId(), supply);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Supply> getSupplies() {
        List<Supply> supplies = new LinkedList<>();

        try {
            supplies.addAll((List<Supply>) objectMapper.readValue(appSharedPreferences.getString(PreferenceKey.SUPPLIES_LIST.name()), new TypeReference<List<Supply>>() {
            }));
        } catch (IOException e) {
        }

        return supplies;
    }

    public void deleteUser() {
        for (PreferenceKey key : PreferenceKey.values()) {
            appSharedPreferences.remove(key.name());
        }
    }

    public void setUnsubmittedRequest(SubmitNewRequest submitNewRequest) {
        setUnsubmittedRequests(enqueueNewRequest(submitNewRequest));
    }

    @NonNull
    private List<SubmitNewRequest> enqueueNewRequest(SubmitNewRequest submitNewRequest) {
        List<SubmitNewRequest> unsubmittedRequests = new LinkedList<>();
        unsubmittedRequests.addAll(getUnsubmittedRequests());

        unsubmittedRequests.add(submitNewRequest);
        return unsubmittedRequests;
    }

    private void setUnsubmittedRequests(List<SubmitNewRequest> unsubmittedRequests) {
        setString(PreferenceKey.UNSUBMITTED_REQUESTS, unsubmittedRequests);
    }

    private void setString(PreferenceKey key, Object value) {
        try {
            appSharedPreferences.setString(key.name(), objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
        }
    }

    @SuppressWarnings("unchecked")
    public List<SubmitNewRequest> getUnsubmittedRequests() {
        List<SubmitNewRequest> newRequests = new LinkedList<>();
        try {
            newRequests.addAll((List<SubmitNewRequest>) objectMapper.readValue(appSharedPreferences.getString(PreferenceKey.UNSUBMITTED_REQUESTS.name()), new TypeReference<List<SubmitNewRequest>>() {
            }));
        } catch (IOException e) {
        }

        return newRequests;
    }
}
