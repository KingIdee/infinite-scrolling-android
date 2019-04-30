package com.auth0.infinitescrolling;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

class GitHubDataSourceFactory extends DataSource.Factory<String,JobModel> {

    MutableLiveData<GitHubDataSource> gitHubDataSource = new MutableLiveData<>();
    private GitHubDataSource source = new GitHubDataSource();

    @NonNull
    @Override
    public DataSource<String, JobModel> create() {
        gitHubDataSource.postValue(source);
        return source;
    }
}