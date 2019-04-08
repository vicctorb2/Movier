package movier.bsuir.study.movier.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import movier.bsuir.study.movier.R;

public class MovieHolder extends RecyclerView.ViewHolder {

    public ImageView itemMoviePosterImageView;
    public TextView itemMovieTitleTv;
    public TextView itemMovieGenreTv;
    public TextView itemMovieYearTv;
    public TextView itemMovieRatingTv;


    public MovieHolder(View itemView) {
        super(itemView);
        itemMovieTitleTv = itemView.findViewById(R.id.item_movie_title);
        itemMovieGenreTv = itemView.findViewById(R.id.item_movie_genre);
        itemMovieRatingTv = itemView.findViewById(R.id.item_movie_rating);
        itemMovieYearTv = itemView.findViewById(R.id.item_movie_year);
        itemMoviePosterImageView = itemView.findViewById(R.id.item_movie_poster);
    }
}