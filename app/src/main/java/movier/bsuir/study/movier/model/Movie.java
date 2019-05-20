package movier.bsuir.study.movier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {


    private String originalImageURL = "https://image.tmdb.org/t/p/original";
    private String tinyImageURL = "https://image.tmdb.org/t/p/w92";
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("vote_average")
    @Expose
    private String rating;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genre_id_list;

    private String kinopoiskRating = "5.5";
    private String imdbRating = "5.5";

    @SerializedName("poster_path")
    @Expose
    private String posterImgUrl;

    @SerializedName("release_date")
    @Expose
    private String release_date;

    @SerializedName("budget")
    @Expose
    private int budget;



    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie() {
    }

    public Movie(int id, String title, String rating, String overview, String kinopoiskRating, String imdbRating, String posterImgUrl) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.overview = overview;
        this.kinopoiskRating = kinopoiskRating;
        this.imdbRating = imdbRating;
        this.posterImgUrl = posterImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getGenre() {
        return Genres.getGenre(genre_id_list);
    }

    public String getKinopoiskRating() {
        return kinopoiskRating;
    }

    public void setKinopoiskRating(String kinopoiskRating) {
        this.kinopoiskRating = kinopoiskRating;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPosterImgUrl(String type) {
        switch (type) {
            case "tiny":
                return tinyImageURL + posterImgUrl;
            case "original":
                return originalImageURL + posterImgUrl;
            default:
                return originalImageURL + posterImgUrl;
        }
    }

    public void setPosterImgUrl(String posterImgUrl) {
        this.posterImgUrl = posterImgUrl;
    }

    public String getYear() {
        return release_date.split("-")[0];
    }
}
