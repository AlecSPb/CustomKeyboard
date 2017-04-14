package com.basti.platenumberkeyboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.basti.platenumberkeyboard.PlateContent;
import com.basti.platenumberkeyboard.R;

import java.util.List;

/**
 * Created by Boateng17 on 2017/4/13.
 */

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder> {

    List<PlateContent> plateContentList;
    AdapterView.OnItemClickListener onItemClickListener;

    public HeaderAdapter(List<PlateContent> plateContentList) {
        this.plateContentList = plateContentList;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public HeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_view_rv, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HeaderViewHolder holder, int position) {
        holder.headViewTv.setText(plateContentList.get(position).getLabel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return plateContentList.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headViewTv;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headViewTv = (TextView) itemView.findViewById(R.id.header_view_tv);
        }
    }

}
