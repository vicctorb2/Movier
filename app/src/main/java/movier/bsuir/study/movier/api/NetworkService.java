package movier.bsuir.study.movier.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService ourInstance = new NetworkService();
    private static final String BASE_API_URL = "https://api.themoviedb.org/3/movie/";
    private Retrofit mRetrofit;
    private static Gson gson;
    public static NetworkService getInstance() {
        if (ourInstance == null) {
            gson = new GsonBuilder().serializeNulls().create();
            ourInstance = new NetworkService();
        }
        return ourInstance;
    }

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public NetworkService getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}
