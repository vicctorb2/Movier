package movier.bsuir.study.movier.api;

import movier.bsuir.study.movier.adapter.MovieRecyclerViewAdapter;
import movier.bsuir.study.movier.model.MoviListResponse;
import movier.bsuir.study.movier.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("3/search/movie")
    Call<MoviListResponse> getMoviesFromSearch(@Query("api_key") String access_token, @Query("query") String query);

}
