package movier.bsuir.study.movier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviListResponse {

    @SerializedName("results")
    @Expose
    private List<Movie> movieList;

    @SerializedName("total_results")
    @Expose
    private int totalCount;

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
