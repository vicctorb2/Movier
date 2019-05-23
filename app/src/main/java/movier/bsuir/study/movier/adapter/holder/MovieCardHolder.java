package movier.bsuir.study.movier.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import movier.bsuir.study.movier.R;

public class MovieCardHolder extends RecyclerView.ViewHolder{

    public ImageView poster;
    public TextView title;

    public MovieCardHolder(@NonNull View itemView) {
        super(itemView);
        poster = itemView.findViewById(R.id.similar_movie_poster);
        title = itemView.findViewById(R.id.similar_movie_title);
    }
}
