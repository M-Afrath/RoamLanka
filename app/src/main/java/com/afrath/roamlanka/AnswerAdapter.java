package com.afrath.roamlanka;

import android.content.Context;

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

// Adapter class used to display answers inside a RecyclerView
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private Context context;
    private List<AnswerModel> answerList;  // List containing all answers
    private String questionId; // ID of the question to which these answers belong

    // Constructor
    // Receives context, answer list, and question ID
    public AnswerAdapter(Context context, List<AnswerModel> answerList, String questionId) {

        this.context = context;
        this.answerList = answerList;
        this.questionId = questionId;
    }


    // Called when RecyclerView needs a new item view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Load item_answer.xml layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer, parent, false);
        return new ViewHolder(view);
    }

    // Called for each row to display answer data
    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        AnswerModel answer = answerList.get(position);  // Get current answer object

        holder.user.setText(answer.getUsername());  // Display username

        holder.answer.setText(answer.getAnswer());  // Display answer text

        // Long press on an answer to delete it
        holder.itemView.setOnLongClickListener(v -> {

            // Get currently logged-in user
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // Stop if user is not logged in
            if(user == null) {
                return true;
            }

            // Allow deletion only if the answer belongs to the currently logged-in user
            if(user.getUid().equals(answer.getUserId())) {

                // Show confirmation dialog
                new AlertDialog.Builder(context)
                        .setTitle("Delete Answer")
                        .setMessage("Are you sure you want to delete this answer?")
                        .setPositiveButton("Delete",
                                (dialog, which) -> {

                                    // Delete answer from Firestore
                                    FirebaseFirestore
                                            .getInstance()
                                            .collection("community")
                                            .document(questionId)
                                            .collection("answers")
                                            .document(answer.getId())
                                            .delete();

                                })
                        .setNegativeButton(
                                "Cancel",
                                null)
                        .show();
            }

            return true;
        });
    }

    // Returns total number of answers
    @Override
    public int getItemCount() {
        return answerList.size();
    }

    // Holds references to views inside item_answer.xml
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView answer;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            user = itemView.findViewById(R.id.txtUser); // Connect username TextView

            answer = itemView.findViewById(R.id.txtAnswer);  // Connect answer TextView
        }
    }
}