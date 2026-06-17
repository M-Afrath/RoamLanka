package com.afrath.roamlanka;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

// Adapter class used to display community questions in RecyclerView
public class TravelCommunityAdapter
        extends RecyclerView.Adapter<TravelCommunityAdapter.CommunityViewHolder> {

    private Context context;
    private List<CommunityPost> postList;

    // Constructor
    // Receives context and post list
    public TravelCommunityAdapter(Context context, List<CommunityPost> postList) {

        this.context = context;
        this.postList = postList;
    }

    // Creates a new item view when RecyclerView needs one
    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                  int viewType) {

        // Load item_community_post.xml layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_community_post, parent, false);

        return new CommunityViewHolder(view);
    }

    // Binds post data to each RecyclerView item
    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {

        CommunityPost post = postList.get(position); // Get current community post

        holder.username.setText(post.getUsername());  // Display username
        holder.question.setText(post.getQuestion());  // Display question text

        // Display relative time (e.g., 5 minutes ago)
        if (post.getTime() != null) {

            CharSequence timeAgo =
                    DateUtils.getRelativeTimeSpanString(
                            post.getTime(),  // Post time
                            System.currentTimeMillis(),  // Current time
                            DateUtils.MINUTE_IN_MILLIS   // Minimum unit
                    );

            holder.time.setText(timeAgo);
        }
        else {
            holder.time.setText("");  // Show empty text if time is unavailable
        }

        // Open AnswersActivity when user clicks a question
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, AnswersActivity.class);
            intent.putExtra("questionId", post.getId());   // Pass selected question ID
            context.startActivity(intent);

        });

        // Long press to delete own question
        holder.itemView.setOnLongClickListener(v -> {

            // Get current logged-in user
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // Stop if user is not logged in
            if (user == null) {
                return true;
            }

            // Allow deletion only if the current user is the owner of the question
            if (user.getUid().equals(post.getUserId())) {

                // Show delete confirmation dialog
                new AlertDialog.Builder(context)
                        .setTitle("Delete Question")
                        .setMessage("Are you sure you want to delete this question?")
                        .setPositiveButton("Delete",
                                (dialog, which) -> {

                                    FirebaseFirestore.getInstance()
                                            .collection("community")
                                            .document(post.getId())
                                            .delete();

                                })
                        .setNegativeButton("Cancel", null)
                        .show();
            }

            return true;
        });
    }

    // Returns total number of posts
    @Override
    public int getItemCount() {
        return postList.size();
    }

    // ViewHolder class
    // Holds references to views inside each item
    public static class CommunityViewHolder
            extends RecyclerView.ViewHolder {

        TextView username;
        TextView question;
        TextView time;

        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.postUser);
            question = itemView.findViewById(R.id.postQuestion);
            time = itemView.findViewById(R.id.postTime);
        }
    }
}