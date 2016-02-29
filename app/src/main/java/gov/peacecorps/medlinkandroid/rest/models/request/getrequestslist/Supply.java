package gov.peacecorps.medlinkandroid.rest.models.request.getrequestslist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Supply implements Serializable {
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

    public boolean isPending(){
        return getResponse() == null;
    }

    public void setResponse(SupplyResponse response) {
        this.response = response;
    }
}
