package gov.peacecorps.medlinkandroid.rest.models.response.getsupplies;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetSuppliesResponse {
    @JsonProperty("supplies")
    private List<Supply> supplies;

    public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }
}
