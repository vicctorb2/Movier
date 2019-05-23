package movier.bsuir.study.movier.api;

import movier.bsuir.study.movier.model.ClassicResponse;
import movier.bsuir.study.movier.model.LoginCredentials;
import movier.bsuir.study.movier.model.MarkAsFavoriteBody;
import movier.bsuir.study.movier.model.MoviListResponse;
import movier.bsuir.study.movier.model.NewSessionBody;
import movier.bsuir.study.movier.model.NewSessionResponse;
import movier.bsuir.study.movier.model.RequestTokenResponse;
import movier.bsuir.study.movier.model.User;
import movier.bsuir.study.movier.model.VideoResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {


    @GET("3/account")
    Call<User> getCurrentUser(@Query("api_key") String access_token, @Query("session_id") String session_id);

    @GET("3/search/movie")
    Call<MoviListResponse> getMoviesFromSearch(@Query("api_key") String access_token, @Query("query") String query);

    @GET("3/movie/popular")
    Call<MoviListResponse> getPopularMovies(@Query("api_key") String access_token, @Query("language") String language, @Query("page") String page);

    @GET("3/authentication/token/new")
    Call<RequestTokenResponse> getNewRequestToken(@Query("api_key") String access_token);

    @POST("3/authentication/token/validate_with_login")
    Call<RequestTokenResponse> validateToken(@Header("Content-Type") String contentType, @Query("api_key") String access_token, @Body LoginCredentials body);

    @POST("3/authentication/session/new")
    Call<NewSessionResponse> createNewSession(@Header("Content-Type") String contentType, @Query("api_key") String access_token, @Body NewSessionBody body);

    @GET("3/movie/{movie_id}/videos")
    Call<VideoResponse> getVideos(@Path("movie_id") int movie_id, @Query("api_key") String access_token);

    @POST("3/account/{account_id}/favorite")
    Call<ClassicResponse> markAsFavorite(@Header("Content-Type") String content_type, @Path("account_id") int account_id, @Query("api_key") String access_token, @Query("session_id") String session_id, @Body MarkAsFavoriteBody body);

    @GET("3/account/{account_id}/favorite/movies")
    Call<MoviListResponse> getFavoriteMovies(@Path("account_id") int account_id, @Query("api_key") String access_token, @Query("session_id") String session_id, @Query("language") String language);


}
