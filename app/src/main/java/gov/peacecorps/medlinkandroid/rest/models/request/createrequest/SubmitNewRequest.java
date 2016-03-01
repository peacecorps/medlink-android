package gov.peacecorps.medlinkandroid.rest.models.request.createrequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubmitNewRequest {
    @JsonProperty("supply_ids")
    private Set<Integer> supplyIds;

    @JsonProperty("message")
    private String specialInstructions;

    public Set<Integer> getSupplyIds() {
        return supplyIds;
    }

    public void setSupplyIds(Set<Integer> supplyIds) {
        this.supplyIds = supplyIds;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
