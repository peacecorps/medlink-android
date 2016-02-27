package gov.peacecorps.medlinkandroid.helpers;

import java.util.ArrayList;
import java.util.List;

import gov.peacecorps.medlinkandroid.data.models.Supply;
import gov.peacecorps.medlinkandroid.data.models.User;
import gov.peacecorps.medlinkandroid.rest.models.response.getsupplies.GetSuppliesResponse;
import gov.peacecorps.medlinkandroid.rest.models.response.login.LoginResponse;

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
}
