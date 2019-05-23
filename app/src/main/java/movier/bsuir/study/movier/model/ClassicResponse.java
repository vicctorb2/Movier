package movier.bsuir.study.movier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassicResponse {

    @SerializedName("status_code")
    @Expose
    int status_code;


    @SerializedName("status_message")
    @Expose
    String status_messsage;

    public ClassicResponse(int status_code, String status_messsage) {
        this.status_code = status_code;
        this.status_messsage = status_messsage;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_messsage() {
        return status_messsage;
    }

    public void setStatus_messsage(String status_messsage) {
        this.status_messsage = status_messsage;
    }
}
