package movier.bsuir.study.movier.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.adapter.MovieRecyclerViewAdapter;
import movier.bsuir.study.movier.adapter.ViewPagerAdapter;
import movier.bsuir.study.movier.api.APIClient;
import movier.bsuir.study.movier.api.MovieApi;
import movier.bsuir.study.movier.model.MoviListResponse;
import movier.bsuir.study.movier.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static movier.bsuir.study.movier.activity.MovieSearchFragment.apiService;
import static movier.bsuir.study.movier.activity.MovieSearchFragment.recyclerView;
import static movier.bsuir.study.movier.activity.MovieSearchFragment.recyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String access_token = "f43cdf4c9aec6de5430e5fab778e3855";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    public SearchView searchView;
    ViewPagerAdapter adapter;
    MovieSearchFragment movieSearchFragment;

    List<Movie> fromSearchMovieList;
    static MovieRecyclerViewAdapter fromSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        movieSearchFragment = new MovieSearchFragment();
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(movieSearchFragment, "Search");
        adapter.addFragment(new Fragment(),"Popular");
        adapter.addFragment(new Fragment(),"For you");
        fromSearchMovieList = new ArrayList<>();
        fromSearchAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), fromSearchMovieList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("") || newText.equals(" ")) {
            recyclerView.setAdapter(recyclerViewAdapter);
            return false;
        } else {
            apiService = APIClient.getClient().create(MovieApi.class);
            Call<MoviListResponse> call = apiService.getMoviesFromSearch(access_token, newText);
            call.enqueue(new Callback<MoviListResponse>() {
                @Override
                public void onResponse(Call<MoviListResponse> call, Response<MoviListResponse> response) {
                    try {
                        if (response.code() != 403) {
                            fromSearchMovieList.clear();
                            fromSearchMovieList.addAll(response.body().getMovieList());
                            recyclerView.swapAdapter(fromSearchAdapter, true);
                        } else {
                            Toast.makeText(getApplicationContext(), "TMDB API search rate limit (30 per minute). Please try again later.", Toast.LENGTH_LONG).show();
                        }
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                        recyclerView.swapAdapter(recyclerViewAdapter, true);
                    }
                }

                @Override
                public void onFailure(Call<MoviListResponse> call, Throwable t) {
                    recyclerView.swapAdapter(recyclerViewAdapter, true);
                }
            });
        }
        return false;
    }
}
