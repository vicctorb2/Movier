package movier.bsuir.study.movier.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import movier.bsuir.study.movier.R;

public class MovieListActivity extends AppCompatActivity {

    ListView moviesListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
    }
}
