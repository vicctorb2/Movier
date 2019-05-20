package movier.bsuir.study.movier.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    boolean isImageFitToScreen;

    Movie movie;
    ImageView posterView;
    TextView overviewTV;
    TextView titleTV;
    TextView kinopoiskTv;
    TextView imdbTV;
    TextView genreTV;
    TextView yearTV;
    Button likeButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initUI();
    }

    private void initUI() {
        movie = (Movie) getIntent().getSerializableExtra("movie");
        posterView = findViewById(R.id.movie_poster_imageview);
        Picasso.with(getApplicationContext()).load(movie.getPosterImgUrl("original")).placeholder(R.drawable.poster).into(posterView);
        titleTV = findViewById(R.id.movie_title_tv);
        yearTV = findViewById(R.id.movie_year_tv);
        genreTV = findViewById(R.id.movie_genre_tv);
        kinopoiskTv = findViewById(R.id.movie_kinopoisk_tv);
        imdbTV = findViewById(R.id.movie_imdb_tv);
        overviewTV = findViewById(R.id.movie_overview_tv);
        likeButton = findViewById(R.id.movie_like_button);


        overviewTV.setText(movie.getOverview());
        genreTV.setText("Жанр: " + movie.getGenre());
        kinopoiskTv.setText("Кинопоиск: " + movie.getKinopoiskRating());
        imdbTV.setText("IMDB: " + movie.getRating());
        yearTV.setText("Год выпуска: " + movie.getYear());
        titleTV.setText(movie.getTitle());

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: LIstener for like button
                Toast.makeText(getApplicationContext(),"You just liked this movie", Toast.LENGTH_SHORT).show();
            }
        });

        final ViewGroup.LayoutParams defaultLayoutParams = posterView.getLayoutParams();
        posterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    posterView.setLayoutParams(defaultLayoutParams);
                    posterView.setAdjustViewBounds(true);
                }else{
                    isImageFitToScreen=true;
                    posterView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    posterView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    posterView.setBackgroundColor(getResources().getColor(R.color.BLACK));
                }
            }
        });

    }
}
