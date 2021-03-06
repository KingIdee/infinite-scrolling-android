package com.auth0.infinitescrolling;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    GitHubDataSourceFactory factory = new GitHubDataSourceFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListAdapter listAdapter = new ListAdapter(new DiffUtilCallback(),x -> factory.gitHubDataSource.getValue().retryFailedRequest());

        RecyclerView recyclerView = findViewById(R.id.recycler_view_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(15)
                .setEnablePlaceholders(false)
                .build();

        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder<>(factory, config);
        LiveData<PagedList> listLiveData = livePagedListBuilder.build();

        listLiveData.observe(this, listAdapter::submitList);

        LiveData<NetworkState> networkStateLiveData =
                Transformations.switchMap(factory.gitHubDataSource, input -> input.status);

        networkStateLiveData.observe(this, listAdapter::updateNetworkState);

    }

}
