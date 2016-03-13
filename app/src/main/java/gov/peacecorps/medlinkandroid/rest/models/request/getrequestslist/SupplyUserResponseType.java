package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SupplyUserResponseType {
    @JsonProperty("denied")
    DENIED,

    @JsonProperty("received")
    RECEIVED,

    @JsonProperty("responded")
    RESPONDED,

    @JsonProperty("flagged")
    FLAGGED,

    @JsonProperty("pending")
    PENDING
}
