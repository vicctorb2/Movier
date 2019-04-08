package movier.bsuir.study.movier.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService ourInstance = new NetworkService();
    private static final String BASE_API_URL = "https://api.themoviedb.org/3/movie/";
    private Retrofit mRetrofit;
    public static NetworkService getInstance() {
        if (ourInstance == null) {
            ourInstance = new NetworkService();
        }
        return ourInstance;
    }

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
