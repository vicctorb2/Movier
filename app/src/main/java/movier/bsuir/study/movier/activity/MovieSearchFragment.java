package movier.bsuir.study.movier.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class MovieSearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private String access_token = "f43cdf4c9aec6de5430e5fab778e3855";
    private View view;

    static RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static List<Movie> moviesList;
    public static MovieRecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        new LinearLayoutManager(getActivity());
        view = inflater.inflate(R.layout.activity_movie_list, container, false);
        init();
        loadMoviesList();
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

    private void loadMoviesList() {
        progressBar.setVisibility(View.VISIBLE);
        MovieApi apiService = APIClient.getClient().create(MovieApi.class);

        Call<MoviListResponse> call = apiService.getMoviesFromSearch(access_token,"");
        call.enqueue(new Callback<MoviListResponse>() {
            @Override
            public void onResponse(Call<MoviListResponse> call, Response<MoviListResponse> response) {
                try {
                    if (response.isSuccessful() && response.code() != 403) {
                        List<Movie> responseItemsList = new ArrayList<>(response.body().getMovieList());
                        moviesList.addAll(responseItemsList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.scrollToPosition(moviesList.size() - responseItemsList.size() - 1);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    recyclerView.setAdapter(recyclerViewAdapter);
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
        loadMoviesList();
        swipeRefreshLayout.setRefreshing(false);
    }
}
