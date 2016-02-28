package gov.peacecorps.medlinkandroid.rest.models.response.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.peacecorps.medlinkandroid.rest.models.response.BaseResponse;

public class LoginResponse extends BaseResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("secret_key")
    private String secretKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
