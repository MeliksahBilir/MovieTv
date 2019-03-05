package com.meliksahbilir.movietv.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meliksahbilir.movietv.ApiService.Client;
import com.meliksahbilir.movietv.R;
import com.squareup.picasso.Picasso;
import com.meliksahbilir.movietv.DataModel.MovieResponse;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private List<MovieResponse> customList;
    private ItemClickListener clickListener;

    public ItemAdapter(List<MovieResponse> customList) {
        this.customList = customList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        MovieResponse movieResponse = customList.get(position);
        holder.movieName.setText(movieResponse.getOriginal_title());
        holder.movieInfo.setText(movieResponse.getOverview());
        Picasso.get().load(Client.Base_PIC+movieResponse.getPoster_path()).into(holder.movie_pic);
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void setItems(List<MovieResponse> items) {
        customList.clear();
        customList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return customList == null ? 0 : customList.size();
    }

    // ITEM_HOLDER
    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView movieName, movieInfo;
        public ImageView movie_pic;

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }

        public ItemHolder(final View view) {
            super(view);
            movieName = (TextView) view.findViewById(R.id.movie_name);
            movieInfo = (TextView) view.findViewById(R.id.movie_info);
            movie_pic = (ImageView) view.findViewById(R.id.movie_pic);
            view.setOnClickListener(this);
        }
    }
}