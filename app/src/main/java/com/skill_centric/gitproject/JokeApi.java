package com.skill_centric.gitproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeApi {

    @GET("jokes/random")
    Call<Joke> getJoke();
}
