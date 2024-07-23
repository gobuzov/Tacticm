package ru.tacticm;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public TextView header, desc;
    public ImageView pic;
    public View allView;

    public CardViewHolder(View v) {
        super(v);
        header = v.findViewById(R.id.header);
        desc = (TextView) v.findViewById(R.id.desc);
        pic = (ImageView) v.findViewById(R.id.pic);
        allView = v.findViewById(R.id.view);
        allView.setOnClickListener(this::showCard);

    }
    private void showCard(View view) {
        Card card = (Card)view.getTag();
        if (false=="0".equals(card.i)) {
            Intent intent = new Intent(view.getContext(), ShowCardActivity.class);
            intent.putExtra(c.ID, card.i);
            view.getContext().startActivity(intent);
        }
    }
}

