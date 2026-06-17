package com.afrath.roamlanka;

import android.content.Context;
import android.view.*;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

// Adapter class used to display comments in RecyclerView
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.VH> {

    Context context;
    ArrayList<CommentModel> list;  // List containing all comments
    String storyId;

    // Constructor
    public CommentAdapter(Context context, ArrayList<CommentModel> list, String storyId) {

        this.context = context;
        this.list = list;
        this.storyId = storyId;
    }

    // Creates a new comment item view
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        // Load item_comment.xml layout
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_comment, parent, false);
        return new VH(v);
    }

    // Binds comment data to each RecyclerView item
    @Override
    public void onBindViewHolder(VH h, int position) {

        // Get current comment
        CommentModel c = list.get(position);

        h.text.setText(c.getText());   // Display comment text
        h.user.setText(c.getUser());  // Display username

        // Long click event for deleting a comment
        h.itemView.setOnLongClickListener(v -> {

            // Check whether a user is logged in
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                return true;
            }

            // Get current user's ID
            String currentUid = FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .getUid();

            // Allow deletion only if the comment belongs to the current user
            if (!currentUid.equals(c.getUserId())) {

                Toast.makeText(context, "You can delete only your comments", Toast.LENGTH_SHORT).show();

                return true;
            }

            // Show confirmation dialog before deleting
            new AlertDialog.Builder(context)
                    .setTitle("Delete Comment")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Delete", (dialog, which) -> {

                        // Delete comment from Firestore
                        FirebaseFirestore.getInstance()
                                .collection("stories")
                                .document(storyId)
                                .collection("comments")
                                .document(c.getId())
                                .delete();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }

    // Returns total number of comments
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Holds references to views inside each comment item
    class VH extends RecyclerView.ViewHolder {

        TextView text, user;

        public VH(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.commentText);
            user = itemView.findViewById(R.id.commentUser);
        }
    }
}