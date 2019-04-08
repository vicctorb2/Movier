package movier.bsuir.study.movier.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.adapter.MovieRecyclerViewAdapter;
import movier.bsuir.study.movier.api.NetworkService;
import movier.bsuir.study.movier.model.Movie;

public class MovieListFragment extends Fragment {

    View view;
    ListView moviesListView;
    protected static RecyclerView recyclerView;
    private static List<Movie> moviesList;
    protected static List<Movie> filteredMovieList;
    static NetworkService apiService = NetworkService.getInstance();
    private static int currentJsonResponsePage = 1;
    private static int usersPerPage = 50;
    static MovieRecyclerViewAdapter recyclerViewAdapter;
    static MovieRecyclerViewAdapter filteredViewAdapter;

    static boolean loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        view = inflater.inflate(R.layout.activity_movie_list, container, false);
        initUI();
        loadMoviesList();
        return view;
    }

    private void loadMoviesList() {

    }

    private void initUI() {
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(this);
        moviesList = new ArrayList<>();
        filteredMovieList = new ArrayList<>();
        recyclerViewAdapter = new MovieRecyclerViewAdapter(getContext(), moviesList);
        filteredViewAdapter = new MovieRecyclerViewAdapter(getContext(), filteredMovieList);
        recyclerView = view.findViewById(R.id.movies_listview);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
    }
}
