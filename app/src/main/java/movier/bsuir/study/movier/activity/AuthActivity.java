package movier.bsuir.study.movier.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import movier.bsuir.study.movier.R;
import movier.bsuir.study.movier.api.APIClient;
import movier.bsuir.study.movier.api.MovieApi;
import movier.bsuir.study.movier.model.LoginCredentials;
import movier.bsuir.study.movier.model.NewSessionBody;
import movier.bsuir.study.movier.model.NewSessionResponse;
import movier.bsuir.study.movier.model.RequestTokenResponse;
import movier.bsuir.study.movier.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthActivity extends AppCompatActivity {

    private final String TAG = "MOVIER_AUTH";
    private final String access_token = "f43cdf4c9aec6de5430e5fab778e3855";
    private static String session_id;
    private static String request_token;

    private static String username;
    private static String password;
    static MovieApi apiService;

    EditText usernameInputET;
    EditText passwordInputET;
    TextView registerLinkTV;
    Button loginButton;

    User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
    }

    private void init() {
        apiService = APIClient.getClient().create(MovieApi.class);
        usernameInputET = findViewById(R.id.username_input);
        passwordInputET = findViewById(R.id.password_input);
        registerLinkTV = findViewById(R.id.register_link);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (validate()) {
//                    username = usernameInputET.getText().toString();
//                    password = passwordInputET.getText().toString();
                username = "vicctorb2";
                password = "ptanb7vu";
                getNewRequestToken(false);
//                }
            }
        });
        registerLinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewRequestToken(true);
            }
        });
    }

    private void getNewRequestToken(final boolean webValidation) {
        Call<RequestTokenResponse> call = apiService.getNewRequestToken(access_token);
        call.enqueue(new Callback<RequestTokenResponse>() {
            @Override
            public void onResponse(Call<RequestTokenResponse> call, Response<RequestTokenResponse> response) {
                try {
                    if (response.code() == 200) {
                        request_token = response.body().getReques_token();
                        Log.d("MOVIER_AUTH", "Got new request token: " + request_token);
                        if (webValidation) {
                            Intent webLoginIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/authenticate/" + request_token + "?redirect_to=bsuir://movier"));
                            startActivity(webLoginIntent);
                        } else {
                            validateToken();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "TMDB API search rate limit (30 per minute). Please try again later.", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RequestTokenResponse> call, Throwable t) {
                Log.d(TAG, "Something went wrong");
            }
        });
    }

    private void validateToken() {
        Call<RequestTokenResponse> call = apiService.validateToken("application/json", access_token, new LoginCredentials(username, password, request_token));
        call.enqueue(new Callback<RequestTokenResponse>() {
            @Override
            public void onResponse(Call<RequestTokenResponse> call, Response<RequestTokenResponse> response) {
                if (response.code() == 200 && response.body().isSuccess()) {
                    Log.d(TAG, "Succesfully validated " + response.body().getReques_token() + " " + response.body().getExpires_at());
                    request_token = response.body().getReques_token();
                    createNewSession();
                } else {
                    Log.d(TAG, "Something went wrong while create a new session token: " + response.errorBody());
                    Toast.makeText(getApplicationContext(), "Incorrect data/ Try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestTokenResponse> call, Throwable t) {
                Log.d(TAG, "Something went wrong while validating token");
                Toast.makeText(getApplicationContext(), "Incorrect data/ Try again!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createNewSession() {
        Call<NewSessionResponse> call = apiService.createNewSession("application/json", access_token, new NewSessionBody(request_token));
        call.enqueue(new Callback<NewSessionResponse>() {
            @Override
            public void onResponse(Call<NewSessionResponse> call, Response<NewSessionResponse> response) {
                if (response.code() == 200 && response.body().isSuccess()) {
                    Log.d(TAG, "Successfully created new session_id " + response.body().getSession_id());
                    session_id = response.body().getSession_id();
                    initUser();
                } else {
                    Log.d(TAG, "Something went wrong while create a new session token: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NewSessionResponse> call, Throwable t) {

            }
        });
    }

    private void initUser() {
        Call<User> call = apiService.getCurrentUser(access_token, session_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    currentUser = response.body();
                    Intent goToMainIntent = new Intent(getBaseContext(), MainActivity.class);
                    goToMainIntent.putExtra("session_id", session_id);
                    goToMainIntent.putExtra("user_id",currentUser.getId());
                    startActivity(goToMainIntent);
//                    initPreferences();
                } else {
                    Toast.makeText(getApplicationContext(), "Couldn't get user account. You need to sign in again!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Couldn't get user account. You need to sign in again!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (request_token != null) {
            createNewSession();
        }
    }

    private boolean validate() {
        boolean result = false;
        if (usernameInputET.getText().toString().equals("")) {
            usernameInputET.setError("Required");
        } else {
            passwordInputET.setError(null);
            result = true;
        }
        if (passwordInputET.getText().toString().equals("")) {
            passwordInputET.setError("Required");
        } else {
            passwordInputET.setError(null);
            result = true;
        }
        return result;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
