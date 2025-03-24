package com.aidenadzich.adventureblogs;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView title, content;
    public RecyclerView imageRecyclerView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.post_title);
        content = itemView.findViewById(R.id.post_content);
        imageRecyclerView = itemView.findViewById(R.id.imageViewPager);
    }
}

