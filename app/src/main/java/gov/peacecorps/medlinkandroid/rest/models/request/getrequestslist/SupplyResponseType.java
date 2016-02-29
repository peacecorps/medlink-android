package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SupplyResponseType {
    @JsonProperty("Delivery")
    DELIVERY,

    @JsonProperty("Denial")
    DENIAL,

    @JsonProperty("Pickup")
    PICKUP,

    @JsonProperty("Reimburse")
    REIMBURSE
}
