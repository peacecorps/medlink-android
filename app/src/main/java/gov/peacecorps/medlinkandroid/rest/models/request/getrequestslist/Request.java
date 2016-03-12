package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import gov.peacecorps.medlinkandroid.helpers.Constants;

public class Request implements Serializable {

    public enum Status {
        ALL_APPROVED,
        ALL_DENIED,
        AT_LEAST_ONE_PENDING,
        PROCESSED
    }

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DESERIALIZE_DATE_FORMAT)
    private Date createdAt;

    @JsonProperty("supplies")
    private List<Supply> supplies;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }

    public Status getStatus() {
        int numApproved = 0, numDenied = 0;

        for (Supply supply : getSupplies()) {
            if (supply.isPending()) {
                return Status.AT_LEAST_ONE_PENDING;
            } else {
                switch (supply.getResponse().getType()) {
                    case DELIVERY:
                        numApproved++;
                        break;
                    case DENIAL:
                        numDenied++;
                        break;
                    case PICKUP:
                        numApproved++;
                        break;
                    case REIMBURSE:
                        //TODO: not sure what this status is for
                        break;
                }
            }
        }

        if(hasOnlyApprovedSupplies(numApproved, numDenied)){
            return Status.ALL_APPROVED;
        } else if(hasOnlyDeniedSupplies(numApproved, numDenied)) {
            return Status.ALL_DENIED;
        }

        return Status.PROCESSED;
    }

    private boolean hasOnlyDeniedSupplies(int numApproved, int numDenied) {
        return numApproved == 0 && numDenied > 0;
    }

    private boolean hasOnlyApprovedSupplies(int numApproved, int numDenied) {
        return numApproved > 0 && numDenied == 0;
    }
}
