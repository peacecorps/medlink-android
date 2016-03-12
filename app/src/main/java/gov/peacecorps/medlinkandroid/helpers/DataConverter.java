package gov.peacecorps.medlinkandroid.helpers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gov.peacecorps.medlinkandroid.data.models.Supply;
import gov.peacecorps.medlinkandroid.data.models.User;
import gov.peacecorps.medlinkandroid.rest.models.request.createrequest.SubmitNewRequest;
import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;
import gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.GetSuppliesResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;
import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class DataConverter {
    public static User convertLoginResponseToUser(LoginResponse loginResponse) {
        User user = new User();
        user.setSecretKey(loginResponse.getSecretKey());
        user.setUserId(loginResponse.getId());

        return user;
    }

    public static List<Supply> convertGetSuppliesResponseToSupply(GetSuppliesResponse getSuppliesResponse) {
        List<Supply> toReturn = new ArrayList<>();
        for (gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.Supply getSupply : getSuppliesResponse.getSupplies()) {
            Supply supply = new Supply();
            supply.setId(getSupply.getId());
            supply.setName(getSupply.getName());
            supply.setShortCode(getSupply.getShortCode());

            toReturn.add(supply);
        }

        return toReturn;
    }

    public static RequestListItem convertRequestToRequestListItem(Request request){
        RequestListItem requestListItem = new RequestListItem();
        requestListItem.setIsSubSectionHeader(false);
        requestListItem.setCreatedAt(request.getCreatedAt());
        requestListItem.setSupplies(request.getSupplies());

        return requestListItem;
    }

    public static RequestListItem convertSubmitNewRequestToRequestListItem(SubmitNewRequest newRequest) {
        RequestListItem requestListItem = new RequestListItem();
        requestListItem.setIsSubSectionHeader(false);

        requestListItem.setSupplies(buildSupplyList(newRequest));

        return requestListItem;
    }

    public static List<gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply> buildSupplyList(SubmitNewRequest newRequest) {
        List<gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply> suppliesList = new LinkedList<>();
        for(Integer supplyId: newRequest.getSupplyIds()){
            suppliesList.add(convertSupplyToSupply(supplyId));
        }

        return suppliesList;
    }

    private static gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply convertSupplyToSupply(Integer supplyId) {
        gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply supply = new gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Supply();
        supply.setId(supplyId);

        return supply;
    }
}
