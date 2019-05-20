package movier.bsuir.study.movier.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.activity.MovieDetailsActivity;
import movier.bsuir.study.movier.adapter.holder.MovieHolder;
import movier.bsuir.study.movier.model.Movie;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieHolder>{
    private Context mContext;
    private List<Movie> moviesList;
    Movie movieForDetailView;

    public MovieRecyclerViewAdapter(Context mContext, List<Movie> listData) {
        if (listData == null) {
            listData.add(new Movie());
            this.mContext = mContext;
        } else {
            this.moviesList = listData;
            this.mContext = mContext;
        }
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_movie;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, final int position) {
        Picasso.with(mContext).load(moviesList.get(position).getPosterImgUrl("tiny")).placeholder(R.drawable.poster).into(movieHolder.itemMoviePosterImageView);
        movieHolder.itemMovieTitleTv.setText(moviesList.get(position).getTitle());
        movieHolder.itemMovieGenreTv.setText(moviesList.get(position).getGenre());
        movieHolder.itemMovieRatingTv.setText(moviesList.get(position).getRating());
        movieHolder.itemMovieYearTv.setText(moviesList.get(position).getYear());
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie detailedUser = moviesList.get(position);
                Intent startDetailedActivityIntent = new Intent(mContext, MovieDetailsActivity.class);
                startDetailedActivityIntent.putExtra("movie", detailedUser);
                mContext.getApplicationContext().startActivity(startDetailedActivityIntent);
            }
        };
        movieHolder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
