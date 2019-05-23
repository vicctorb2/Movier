package movier.bsuir.study.movier.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.activity.MovieDetailsActivity;
import movier.bsuir.study.movier.adapter.holder.MovieCardHolder;
import movier.bsuir.study.movier.model.Movie;

public class SimilarMoviesRecyclerViewAdapter extends RecyclerView.Adapter<MovieCardHolder>{
    private Context mContext;
    private List<Movie> moviesList;

    public SimilarMoviesRecyclerViewAdapter(Context mContext, List<Movie> listData) {
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
    public MovieCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.similar_movie_item, parent, false);
        return new MovieCardHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.similar_movie_item;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCardHolder movieHolder, final int position) {
        Picasso.with(mContext).load(moviesList.get(position).getPosterImgUrl("large")).placeholder(R.drawable.poster).into(movieHolder.poster);
        movieHolder.title.setText(moviesList.get(position).getTitle());
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
