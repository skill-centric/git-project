package com.skill_centric.gitproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jokeTextView = findViewById(R.id.joke_text_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokeApi jokeApi = retrofit.create(JokeApi.class);

        final Call<Joke> jokeCall = jokeApi.getJoke();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayJoke(jokeCall);
            }
        });
    }

    private void displayJoke(Call<Joke> jokeCall) {

        Call<Joke> cloneCall = jokeCall.clone();

        cloneCall.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {

                String joke = response.body().getValue();

                jokeTextView.setText(joke);
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {

                Toast.makeText(MainActivity.this, R.string.falied_to_fetch_data,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_reset) {

            jokeTextView.setText(R.string.press_to_load_new_joke);
        }

        return super.onOptionsItemSelected(item);
    }
}
