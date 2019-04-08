package movier.bsuir.study.movier.model;

public class Movie {

    private String title;
    private String rating;
    private String overview;
    private String genre;
    private String kinopoiskRating;
    private String imdbRating;
    private int year;


    public Movie() {
    }

    public Movie(String title, String rating, String overview, String genre, String kinopoiskRating, String imdbRating, int year) {
        this.title = title;
        this.rating = rating;
        this.overview = overview;
        this.genre = genre;
        this.kinopoiskRating = kinopoiskRating;
        this.imdbRating = imdbRating;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
