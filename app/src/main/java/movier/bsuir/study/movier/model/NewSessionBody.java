package movier.bsuir.study.movier.model;

import com.google.gson.annotations.SerializedName;

public class NewSessionBody {

    @SerializedName("request_token")
    String requestToken;

    public NewSessionBody(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
