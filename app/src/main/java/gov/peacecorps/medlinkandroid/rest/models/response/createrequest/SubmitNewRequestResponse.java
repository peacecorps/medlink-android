package gov.peacecorps.medlinkandroid.rest.models.response.createrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist.Request;
import gov.peacecorps.medlinkandroid.rest.models.response.BaseResponse;

public class SubmitNewRequestResponse extends BaseResponse {
    @JsonProperty("request")
    private Request requestResponse;

    public Request getRequestResponse() {
        return requestResponse;
    }

    public void setRequestResponse(Request requestResponse) {
        this.requestResponse = requestResponse;
    }
}
