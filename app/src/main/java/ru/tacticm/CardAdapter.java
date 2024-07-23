package ru.tacticm;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter<CardViewHolder>{
    ArrayList<Card> list;
    int openId = -1;

    public CardAdapter(ArrayList<Card> Data) {
        list = Data;
    }
    public void setOpen(int pos) {
        openId = pos;
    }
    public void setList(ArrayList<Card> Data) {
        list = Data;
        notifyDataSetChanged();
    }
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        CardViewHolder holder = new CardViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        Card cd = list.get(position);
        holder.header.setText(cd.h);
        String tags = cd.getTags();
        if (null!=tags) {
            holder.desc.setText(tags);
        }
        holder.desc.setVisibility(null==tags? View.GONE : View.VISIBLE);
        //holder.header.setBackgroundColor(c.NEXT_BG);
        //holder.pic.setVisibility(View.VISIBLE);
        holder.allView.setTag(cd);
    }
    /**
     * количество элементов
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

}
