package com.auth0.infinitescrolling;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;


class LoaderViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar progressBar = itemView.findViewById(R.id.progress_bar);
    private Button retryButton = itemView.findViewById(R.id.retry_button);

    LoaderViewHolder(@NonNull View itemView, Consumer retryFunction) {
        super(itemView);
        retryButton.setOnClickListener(v -> retryFunction.accept(null));
    }

    void bind(NetworkState networkState){

        if(networkState.getStatus()==Status.LOADING){
            progressBar.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.GONE);
        } else if(networkState.getStatus()==Status.SUCCESS){
            progressBar.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            retryButton.setVisibility(View.VISIBLE);
        }

    }

}
