package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Supply {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("response")
    private SupplyResponse response;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SupplyResponse getResponse() {
        return response;
    }

    public void setResponse(SupplyResponse response) {
        this.response = response;
    }
}
