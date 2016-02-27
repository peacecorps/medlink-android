package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import gov.peacecorps.medlinkandroid.helpers.Constants;

public class Request {

    @JsonProperty("created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= Constants.DATE_FORMAT)
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
}
