package movier.bsuir.study.movier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("results")
    @Expose
    List<Video> results;


    public VideoResponse(int id, List<Video> results) {
        this.id = id;
        this.results = results;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
