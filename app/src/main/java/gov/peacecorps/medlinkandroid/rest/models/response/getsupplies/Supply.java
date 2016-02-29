package gov.peacecorps.medlinkandroid.rest.models.response.getsupplies;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.peacecorps.medlinkandroid.rest.models.response.BaseResponse;

public class Supply extends BaseResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortcode")
    private String shortCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
