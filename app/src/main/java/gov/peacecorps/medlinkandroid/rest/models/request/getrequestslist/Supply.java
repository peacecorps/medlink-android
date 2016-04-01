package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.Date;

import gov.peacecorps.medlinkandroid.helpers.Constants;

public class Supply implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("response")
    private SupplyResponse response;

    @JsonProperty("status")
    private SupplyUserResponseType userResponseStatus;

    private Date userResponseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SupplyResponse getResponse() {
        return response;
    }

    public boolean isPending(){
        return getResponse() == null;
    }

    public void setResponse(SupplyResponse response) {
        this.response = response;
    }

    public SupplyUserResponseType getUserResponseStatus() {
        return userResponseStatus;
    }

    public void setUserResponseStatus(SupplyUserResponseType userResponseStatus) {
        this.userResponseStatus = userResponseStatus;
    }

    public Date getUserResponseDate() {
        return userResponseDate;
    }

    @JsonSetter("received_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= Constants.DESERIALIZE_DATE_FORMAT)
    public void setUserResponseReceivedAtDate(Date userResponseDate) {
        this.userResponseDate = userResponseDate;
    }

    @JsonSetter("denied_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= Constants.DESERIALIZE_DATE_FORMAT)
    public void setUserResponseDeniedAtDate(Date userResponseReceivedAt) {
        this.userResponseDate = userResponseReceivedAt;
    }

    @JsonSetter("responded_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= Constants.DESERIALIZE_DATE_FORMAT)
    public void setUserResponseRespondedAtDate(Date userResponseReceivedAt) {
        this.userResponseDate = userResponseReceivedAt;
    }
}
