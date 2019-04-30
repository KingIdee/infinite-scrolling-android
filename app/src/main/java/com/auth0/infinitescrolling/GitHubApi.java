package com.auth0.infinitescrolling;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface GitHubApi {

    @GET("/positions.json?description=web")
    Call<List<JobModel>> getJobs(@Query("page") int page);

}
