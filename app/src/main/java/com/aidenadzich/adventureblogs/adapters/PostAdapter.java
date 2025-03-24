package com.aidenadzich.adventureblogs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.aidenadzich.adventureblogs.PostViewHolder;
import com.aidenadzich.adventureblogs.R;
import com.aidenadzich.adventureblogs.models.FileData;
import com.aidenadzich.adventureblogs.models.Post;
import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context context;

    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.username.setText(post.getUsername());
        holder.created_at.setText(post.getCreatedAt());
        holder.content.setText(post.getContent());

        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(holder.itemView.getContext(), post.getFiles());
        holder.imageViewPager.setAdapter(imagePagerAdapter);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, username, created_at;
        ViewPager2 imageViewPager;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_title);
            content = itemView.findViewById(R.id.post_content);
            username = itemView.findViewById(R.id.post_username);
            created_at = itemView.findViewById(R.id.post_created_at);
            imageViewPager = itemView.findViewById(R.id.imageViewPager);
        }
    }
}
