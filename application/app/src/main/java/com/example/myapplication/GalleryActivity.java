package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.DefaultCard;
import com.example.myapplication.model.ICard;
import com.example.myapplication.model.UserCard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import DataBase.UserCardEntity;
import DataBase.CategoryUserCardCrossRef;

public class GalleryActivity extends AppCompatActivity {

    public static final String CATEGORY_ID_ARG = "categoryId";
    private RecyclerView cardsRecyclerView;
    private CardAdapter cardsAdapter;


    public static Intent newIntent(Context context, Long categoryId) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(CATEGORY_ID_ARG, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initCardsRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCards();
    }

    private void initCardsRecyclerView() {
        cardsRecyclerView = findViewById(R.id.cards_recycler_view);
        cardsAdapter = new CardAdapter();

        cardsAdapter.setOnCardClickListener(new CardAdapter.OnCardClickListener() {
            @Override
            public void onClick(ICard card) {
                if (card.isPlusNeeded()) {
                    startActivity(new Intent(GalleryActivity.this, AddCardActivity.class));
                } else {
                    if (card instanceof UserCard) {
                        App.getInstance().getDatabase().categoryDao().insertCategoryUserCardCrossRef(
                                new CategoryUserCardCrossRef(getCategoryId(), card.getId())
                        );
                        onBackPressed();
                    }
                }
            }
        });
        cardsAdapter.setOnCardLongClickListener(new CardAdapter.OnCardLongClickListener() {
            @Override
            public void onClick(ICard card) {
                if (!card.isPlusNeeded()) {
                    if (card instanceof UserCard) {
                        App.getInstance().getDatabase().categoryDao().deleteUserCardFromAllCategoriesById(card.getId());
                        App.getInstance().getDatabase().userCardDao().deleteById(card.getId());
                        File imageFile = new File(((UserCard) card).getCardFilePath());
                        imageFile.delete();
                        setCards();
                    } else if (card instanceof DefaultCard) {
                        Toast.makeText(GalleryActivity.this, "Встроенную карточку нельзя удалить", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        cardsRecyclerView.setAdapter(cardsAdapter);
    }

    private void setCards() {
        List<ICard> cards = new ArrayList<>();
        cards.add(new DefaultCard(-1, R.drawable.plus2, "", true));

        List<UserCardEntity> userCardEntities = App.getInstance().getDatabase().userCardDao().getAll();
        for (UserCardEntity userCardEntity : userCardEntities) {
            cards.add(new UserCard(userCardEntity.userCardId, userCardEntity.imagePath, userCardEntity.text, false));
        }

        cardsAdapter.setCards(cards);
    }

    public long getCategoryId() {
        return getIntent().getLongExtra(CATEGORY_ID_ARG, 1L);
    }
}


