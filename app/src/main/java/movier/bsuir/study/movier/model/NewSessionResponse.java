package movier.bsuir.study.movier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewSessionResponse {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("session_id")
    @Expose
    String session_id;

    @SerializedName("status_message")
    @Expose
    String status_message;

    @SerializedName("status_code")
    @Expose
    int status_code;

    public NewSessionResponse() {
    }

    public NewSessionResponse(boolean success, String session_id, String status_message, int status_code) {
        this.success = success;
        this.session_id = session_id;
        this.status_message = status_message;
        this.status_code = status_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
