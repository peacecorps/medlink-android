package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SupplyResponseType {
    @JsonProperty("delivery")
    DELIVERY,

    @JsonProperty("denial")
    DENIAL,

    @JsonProperty("pickup")
    PICKUP,

    @JsonProperty("reimburse")
    REIMBURSE,

    @JsonProperty("duplicate")
    DUPLICATE
}
