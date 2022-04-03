package com.bcit.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    private ArrayList<PostModel> localDataSet;

    /**
     * References a TextView for PostModel name and post
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView inputName;
        private final TextView inputPost;

        public ViewHolder(View view) {
            super(view);

            inputName = view.findViewById(R.id.textView_input_name);
            inputPost = view.findViewById(R.id.textView_read_post);
        }

        public TextView getInputName() {
            return inputName;
        }
        public TextView getInputPost() {
            return inputPost;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet ArrayList containing the post data to populate views to be used
     *                by RecyclerView.
     */
    public PostRecyclerAdapter(ArrayList<PostModel> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_recycler_posts, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getInputName().setText(localDataSet.get(position).getPosterName());
        viewHolder.getInputPost().setText(localDataSet.get(position).getMessage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}