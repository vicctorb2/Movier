package movier.bsuir.study.movier.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.adapter.MovieRecyclerViewAdapter;
import movier.bsuir.study.movier.api.APIClient;
import movier.bsuir.study.movier.api.MovieApi;
import movier.bsuir.study.movier.model.MoviListResponse;
import movier.bsuir.study.movier.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    final String access_token = "f43cdf4c9aec6de5430e5fab778e3855";
    final String language = "ru-RU";


    protected static RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    public static List<Movie> moviesList;
    static MovieApi apiService;
    static MovieRecyclerViewAdapter recyclerViewAdapter;
    static boolean loading;
    ProgressBar progressBar;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        view = inflater.inflate(R.layout.activity_movie_list, container, false);
        init();
        loadPopularMovieList();
        return view;
    }

    private void init() {
        progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        moviesList = new ArrayList<>();
        recyclerViewAdapter = new MovieRecyclerViewAdapter(getContext(), moviesList);
        recyclerView = view.findViewById(R.id.movies_listview);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadPopularMovieList() {
        progressBar.setVisibility(View.VISIBLE);
        apiService = APIClient.getClient().create(MovieApi.class);

        Call<MoviListResponse> call = apiService.getPopularMovies(access_token,language,"1");
        call.enqueue(new Callback<MoviListResponse>() {
            @Override
            public void onResponse(Call<MoviListResponse> call, Response<MoviListResponse> response) {
                try {
                    if (response.isSuccessful() && response.code() != 403) {
                        List<Movie> responseItemsList = new ArrayList<>(response.body().getMovieList());
                        moviesList.addAll(responseItemsList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.scrollToPosition(moviesList.size() - responseItemsList.size() - 1);
                        loading = false;
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    recyclerView.setAdapter(recyclerViewAdapter);
                    loading = false;
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<MoviListResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity().getApplicationContext(),"Something wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        moviesList.clear();
        loadPopularMovieList();
        swipeRefreshLayout.setRefreshing(false);
    }
}
