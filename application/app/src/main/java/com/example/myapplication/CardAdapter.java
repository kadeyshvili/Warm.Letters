package com.example.myapplication;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.DefaultCard;
import com.example.myapplication.model.ICard;
import com.example.myapplication.model.UserCard;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<ICard> cards = new ArrayList<>();
    private OnCardClickListener onCardClickListener;
    private OnCardLongClickListener onCardLongClickListener;

    public CardAdapter() {
    }

    public CardAdapter(List<ICard> cards) {
        this.cards = cards;
    }

    public void setCards(List<ICard> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public void setOnCardLongClickListener(OnCardLongClickListener onCardLongClickListener) {
        this.onCardLongClickListener = onCardLongClickListener;
    }

    void addCard(ICard card) {
        cards.add(card);
        notifyDataSetChanged();
    }

    void erase() {
        cards.clear();
        notifyDataSetChanged();
    }

    String getSentence(){
        StringBuilder text = new StringBuilder();
        for (ICard card : cards) {
            text.append(card.getText()).append(" ");
        }
        return text.toString().trim();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_card, viewGroup, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, int i) {
        final ICard card = cards.get(i);
        viewHolder.pecsImage.setImageDrawable(null);
        viewHolder.pecsImage.setBackgroundResource(0);

        if (card instanceof DefaultCard){
            viewHolder.pecsImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            viewHolder.pecsImage.setBackgroundResource(((DefaultCard) card ).getImageResId());
        }else if (card instanceof UserCard){
            viewHolder.pecsImage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.pecsImage.setImageURI(Uri.parse(((UserCard) card).getCardFilePath()));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCardClickListener !=null){
                    onCardClickListener.onClick(card);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onCardLongClickListener != null) {
                    onCardLongClickListener.onClick(card);
                    return true;
                }
                return false;
            }
        });

        if (card.getText().isEmpty()){
            viewHolder.pecsText.setVisibility(View.GONE);
        }else{
            viewHolder.pecsText.setVisibility(View.VISIBLE);
            viewHolder.pecsText.setText(card.getText());
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        private ImageView pecsImage;
        private TextView pecsText;

        public CardViewHolder(View itemView) {
            super(itemView);
            pecsImage = itemView.findViewById(R.id.pecs_image);
            pecsText = itemView.findViewById(R.id.tv_text);
        }
    }

    interface OnCardClickListener {
        public void onClick(ICard card);
    }

    interface OnCardLongClickListener {
        public void onClick(ICard card);
    }
}

