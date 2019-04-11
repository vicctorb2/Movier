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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.adapter.MovieRecyclerViewAdapter;
import movier.bsuir.study.movier.api.Client;
import movier.bsuir.study.movier.api.MovieApi;

import movier.bsuir.study.movier.model.MoviListResponse;
import movier.bsuir.study.movier.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment {


    String access_token = "f43cdf4c9aec6de5430e5fab778e3855";

    View view;
    ListView moviesListView;
    protected static RecyclerView recyclerView;
    private static List<Movie> moviesList;
    protected static List<Movie> filteredMovieList;
    static MovieApi apiService;
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
        apiService = Client.getClient().create(MovieApi.class);

        Call<MoviListResponse> call = apiService.getMoviesFromSearch(access_token,"Титаник");
        call.enqueue(new Callback<MoviListResponse>() {
            @Override
            public void onResponse(Call<MoviListResponse> call, Response<MoviListResponse> response) {
                try {
                    if (response.isSuccessful() && response.code() != 403) {
                        List<Movie> responseItemsList = new ArrayList<>(response.body().getMovieList());
                        moviesList.addAll(responseItemsList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.scrollToPosition(moviesList.size() - responseItemsList.size() - 1);
                        //incrementing current json response page
                        currentJsonResponsePage++;
                        loading = false;
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    recyclerView.setAdapter(recyclerViewAdapter);
                    loading = false;
                }

            }

            @Override
            public void onFailure(Call<MoviListResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),"Something wrong", Toast.LENGTH_LONG).show();
            }
        });
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
