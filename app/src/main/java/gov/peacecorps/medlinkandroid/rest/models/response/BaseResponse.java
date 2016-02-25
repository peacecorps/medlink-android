package gov.peacecorps.medlinkandroid.rest.models.response;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {

    @JsonProperty("error")
    private String error;

    public boolean hasError(){
        return !TextUtils.isEmpty(getError());
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
