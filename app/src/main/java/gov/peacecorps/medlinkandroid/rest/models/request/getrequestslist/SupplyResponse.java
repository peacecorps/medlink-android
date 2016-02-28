package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import gov.peacecorps.medlinkandroid.helpers.Constants;

public class SupplyResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("type")
    private SupplyResponseType type;

    @JsonProperty("created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern=Constants.DESERIALIZE_DATE_FORMAT)
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SupplyResponseType getType() {
        return type;
    }

    public void setType(SupplyResponseType type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
