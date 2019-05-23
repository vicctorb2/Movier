package movier.bsuir.study.movier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTokenResponse {
    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("expires_at")
    @Expose
    String expires_at;

    @SerializedName("request_token")
    @Expose
    String reques_token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getReques_token() {
        return reques_token;
    }

    public void setReques_token(String reques_token) {
        this.reques_token = reques_token;
    }
}
