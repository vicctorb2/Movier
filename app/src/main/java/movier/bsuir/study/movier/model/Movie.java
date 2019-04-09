package movier.bsuir.study.movier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

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

    private String genre = "Жанр";
    private String kinopoiskRating = "5.5";
    private String imdbRating = "5.5";

    @SerializedName("poster_path")
    private String posterImgUrl;


    private int year = 2019;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie() {
    }

    public Movie(int id, String title, String rating, String overview, String genre, String kinopoiskRating, String imdbRating, String posterImgUrl, int year) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.overview = overview;
        this.genre = genre;
        this.kinopoiskRating = kinopoiskRating;
        this.imdbRating = imdbRating;
        this.posterImgUrl = "https://image.tmdb.org/t/p/original" + posterImgUrl;
        this.year = year;
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
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String getPosterImgUrl() {
        return posterImgUrl;
    }

    public void setPosterImgUrl(String posterImgUrl) {
        this.posterImgUrl = posterImgUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
