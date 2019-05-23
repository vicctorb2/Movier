package movier.bsuir.study.movier.activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.adapter.MovieRecyclerViewAdapter;
import movier.bsuir.study.movier.adapter.SimilarMoviesRecyclerViewAdapter;
import movier.bsuir.study.movier.api.APIClient;
import movier.bsuir.study.movier.api.MovieApi;
import movier.bsuir.study.movier.model.ClassicResponse;
import movier.bsuir.study.movier.model.MarkAsFavoriteBody;
import movier.bsuir.study.movier.model.MoviListResponse;
import movier.bsuir.study.movier.model.Movie;
import movier.bsuir.study.movier.model.Video;
import movier.bsuir.study.movier.model.VideoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailsActivity extends AppCompatActivity {

    boolean isImageFitToScreen;
    int userId;
    Movie movie;
    ImageView posterView;
    TextView overviewTV;
    TextView titleTV;
    TextView imdbTV;
    TextView genreTV;
    TextView yearTV;
    Button likeButton;
    MovieApi apiService;

    YouTubePlayerView youTubePlayerView;
    List<Movie> similarMoviesList;
    SimilarMoviesRecyclerViewAdapter similarAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        apiService = APIClient.getClient().create(MovieApi.class);
        initUI();
    }

    private void initUI() {
        movie = (Movie) getIntent().getSerializableExtra("movie");
        userId = getIntent().getIntExtra("user_id", 0);
        posterView = findViewById(R.id.movie_poster_imageview);
        recyclerView = findViewById(R.id.similarMoviesRecyclerId);
        similarMoviesList = new ArrayList<>();
        similarAdapter = new SimilarMoviesRecyclerViewAdapter(getApplicationContext(), similarMoviesList);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(similarAdapter);
        Picasso.with(getApplicationContext()).load(movie.getPosterImgUrl("large")).placeholder(R.drawable.poster).into(posterView);
        titleTV = findViewById(R.id.movie_title_tv);
        yearTV = findViewById(R.id.movie_year_tv);
        genreTV = findViewById(R.id.movie_genre_tv);
        imdbTV = findViewById(R.id.movie_imdb_tv);
        overviewTV = findViewById(R.id.movie_overview_tv);
        likeButton = findViewById(R.id.movie_like_button);
        overviewTV.setText(movie.getOverview());
        genreTV.setText("Жанр: " + movie.getGenre());
        imdbTV.setText("Рейтинг: " + movie.getRating());
        yearTV.setText("Год выпуска: " + movie.getYear());
        titleTV.setText(movie.getTitle());
        loadTrailers();
        loadSimilarMovies();
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markAsFavorite();
            }
        });

        final ViewGroup.LayoutParams defaultLayoutParams = posterView.getLayoutParams();
        posterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    posterView.setScaleType(ImageView.ScaleType.FIT_XY);
                    posterView.setLayoutParams(defaultLayoutParams);
                } else {
                    isImageFitToScreen = true;
                    posterView.setAdjustViewBounds(true);
                    posterView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    posterView.setBackgroundColor(getResources().getColor(R.color.BLACK));
                }
            }
        });

    }

    private void loadSimilarMovies() {
        apiService = APIClient.getClient().create(MovieApi.class);

        Call<MoviListResponse> call = apiService.getSimilarMovies(movie.getId(),MainActivity.getAccess_token(),"ru-RU");
        call.enqueue(new Callback<MoviListResponse>() {
            @Override
            public void onResponse(Call<MoviListResponse> call, Response<MoviListResponse> response) {
                try {
                    if (response.isSuccessful() && response.code() != 403) {
                        List<Movie> responseItemsList = new ArrayList<>(response.body().getMovieList());
                        similarMoviesList.addAll(responseItemsList);
                        recyclerView.setAdapter(similarAdapter);
                        recyclerView.scrollToPosition(similarMoviesList.size() - responseItemsList.size() - 1);
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    recyclerView.setAdapter(similarAdapter);
                }

            }

            @Override
            public void onFailure(Call<MoviListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void markAsFavorite() {
        MarkAsFavoriteBody body = new MarkAsFavoriteBody("movie",movie.getId(),true);
        Call<ClassicResponse> call = apiService.markAsFavorite("application/json;charset=utf8",MainActivity.getAccount_id() ,MainActivity.getAccess_token(),MainActivity.getSession_id(),body);
        call.enqueue(new Callback<ClassicResponse>() {
            @Override
            public void onResponse(Call<ClassicResponse> call, Response<ClassicResponse> response) {
                if (response.code()==201){
                    Toast.makeText(getApplicationContext(), "You just liked this movie", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Status: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassicResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Respone failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTrailers() {
        final String[] videoId = new String[1];
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.setEnableAutomaticInitialization(true);
        Call<VideoResponse> call = apiService.getVideos(movie.getId(), MainActivity.getAccess_token());
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.code() == 200) {
                    VideoResponse videoResponse = response.body();
                    List<Video> videoList = null;
                    if (videoResponse != null) {
                        videoList = videoResponse.getResults();
                        for (final Video video : videoList) {
                            if (video.getSite().toLowerCase().equals("youtube")) {
                                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                        youTubePlayer.loadVideo(video.getKey(), 0f);
                                        youTubePlayer.pause();
                                    }
                                });
                                break;
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed while loading trailers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });

    }
}
