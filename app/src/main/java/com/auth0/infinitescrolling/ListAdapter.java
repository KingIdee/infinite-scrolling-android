package com.auth0.infinitescrolling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends PagedListAdapter<JobModel,RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private Consumer retryFunction;

    ListAdapter(@NonNull DiffUtil.ItemCallback<JobModel> diffCallback, Consumer function) {
        super(diffCallback);
        this.retryFunction = function;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){

            case R.layout.job_item:
                View jobItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item,parent,false);
                return new JobViewHolder(jobItemView);

            case R.layout.loading_item:
                View loadingItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item,parent,false);
                return new LoaderViewHolder(loadingItemView,retryFunction);

        }

        throw new IllegalArgumentException("Wrong view type");

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case R.layout.loading_item:
                ((LoaderViewHolder) holder).bind(networkState);
                break;

            case R.layout.job_item:
                ((JobViewHolder) holder).bind(getItem(position));
                break;


        }

    }

    private Boolean hasExtraRow(){
        return networkState!=null && networkState.getStatus() != Status.SUCCESS;
    }

    @Override
    public int getItemViewType(int position) {
        if(hasExtraRow() && position == getItemCount() -1){
            return R.layout.loading_item;
        } else {
            return R.layout.job_item;
        }
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if(hasExtraRow()){
            count++;
        }
        return count;
    }


    void updateNetworkState(NetworkState networkState) {
        NetworkState previousState = this.networkState;
        Boolean hadExtraRow = hasExtraRow();

        this.networkState = networkState;
        Boolean hasExtraRow = hasExtraRow();

        if (hadExtraRow!=hasExtraRow) {
            if (hadExtraRow){
                notifyDataSetChanged();
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasExtraRow && previousState!=networkState){
            notifyItemChanged(getItemCount() - 1);
        }

    }

}
