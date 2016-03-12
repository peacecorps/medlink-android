package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import gov.peacecorps.medlinkandroid.ui.fragments.requestslist.submittedrequests.RequestListItem;

public class GetRequestsListResponse {
    @JsonProperty("requests")
    private List<RequestListItem> requests;

    public List<RequestListItem> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestListItem> requests) {
        this.requests = requests;
    }
}
