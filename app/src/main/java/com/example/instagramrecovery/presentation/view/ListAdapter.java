package com.example.instagramrecovery.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramrecovery.R;
import com.example.instagramrecovery.presentation.model.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Data> values;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Data item);
    }

    public ListAdapter(List<Data> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHeader;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            imgHeader = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Data item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.row_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Data currentData = values.get(position);
        Picasso.get().load(currentData.getMedia_url()).into(holder.imgHeader);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}


