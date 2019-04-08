package movier.bsuir.study.movier.api;

import movier.bsuir.study.movier.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApi {
    String access_token = "f43cdf4c9aec6de5430e5fab778e3855";

    @GET("/search/movie")
    Call<Movie> getMoviesFromSearch(@Query("api_key") String access_token, @Query("query") String query);

}
