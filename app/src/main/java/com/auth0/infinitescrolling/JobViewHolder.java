package com.auth0.infinitescrolling;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class JobViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTextView;
    private TextView companyTextView;

    JobViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.job_title);
        companyTextView = itemView.findViewById(R.id.company);
    }

    void bind(JobModel item){
        titleTextView.setText(item.getTitle());
        companyTextView.setText(item.getCompany());
    }

}