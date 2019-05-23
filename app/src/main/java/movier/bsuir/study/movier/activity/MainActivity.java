package movier.bsuir.study.movier.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import movier.bsuir.study.movier.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static movier.bsuir.study.movier.activity.MovieSearchFragment.recyclerView;
import static movier.bsuir.study.movier.activity.MovieSearchFragment.recyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static String access_token = "f43cdf4c9aec6de5430e5fab778e3855";
    private static String session_id;
    static int account_id;

    private static MovieApi apiService;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Toolbar toolbar;
    public SearchView searchView;
    ViewPagerAdapter viewPagerAdapter;
    MovieSearchFragment movieSearchFragment;
    PopularMoviesFragment popularMoviesFragment;
    FavouriteMoviesFragment favouriteMoviesFragment;
    List<Movie> fromSearchMovieList;
    SharedPreferences preferences;
    static MovieRecyclerViewAdapter fromSearchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = APIClient.getClient().create(MovieApi.class);
        session_id = getIntent().getStringExtra("session_id");
        account_id = getIntent().getIntExtra("account_id",0);
        initUI();

    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        movieSearchFragment = new MovieSearchFragment();
        popularMoviesFragment = new PopularMoviesFragment();
        favouriteMoviesFragment = new FavouriteMoviesFragment();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(movieSearchFragment, "Search");
        viewPagerAdapter.addFragment(popularMoviesFragment,"Popular");
        viewPagerAdapter.addFragment(favouriteMoviesFragment,"Favorite");
        viewPagerAdapter.addFragment(new Fragment(),"For you");
        viewPagerAdapter.addFragment(new Fragment(),"Watchlist");
        fromSearchMovieList = new ArrayList<>();
        fromSearchAdapter = new MovieRecyclerViewAdapter(getApplicationContext(), fromSearchMovieList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "Welcome!",Toast.LENGTH_SHORT).show();
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

    public static String getAccess_token() {
        return access_token;
    }

    public static String getSession_id() {
        return session_id;
    }


    public static int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
