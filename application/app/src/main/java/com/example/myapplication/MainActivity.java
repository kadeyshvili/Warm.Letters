package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.DefaultCard;
import com.example.myapplication.model.ICard;
import com.example.myapplication.model.Tab;
import com.example.myapplication.model.UserCard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import DataBase.CategoryAndCards;
import DataBase.CategoryEntity;
import DataBase.UserCardEntity;

public class MainActivity extends AppCompatActivity {

    public static long ALL_CATEGORY_ID = 1;
    private RecyclerView cardsRecyclerView;
    private CardAdapter cardsAdapter;
    private RecyclerView tabsRecyclerView;
    private TabsAdapter tabsAdapter;
    private long currentCategoryId = ALL_CATEGORY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInitialData();
        initCardsRecyclerView();
        initTabsRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAllCategoryCards();
    }

    private void initInitialData() {
        if (App.getInstance().getDatabase().categoryDao().getById(ALL_CATEGORY_ID) == null) {
            CategoryEntity allCategory = new CategoryEntity(ALL_CATEGORY_ID, "Все");
            App.getInstance().getDatabase().categoryDao().insert(allCategory);
        }
    }

    private void initCardsRecyclerView() {
        cardsRecyclerView = findViewById(R.id.cards_recycler_view);
        cardsAdapter = new CardAdapter();
        cardsAdapter.setOnCardClickListener(new CardAdapter.OnCardClickListener() {
            @Override
            public void onClick(ICard card) {
                if (card.isPlusNeeded()) {
                    startActivity(GalleryActivity.newIntent(MainActivity.this, currentCategoryId));
                } else {
                    File file = new File(((UserCard) card).getCardFilePath());
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(photoURI, "text/html");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(intent, "Chose browser"));
                }
            }
        });

        cardsAdapter.setOnCardLongClickListener(new CardAdapter.OnCardLongClickListener() {
            @Override
            public void onClick(ICard card) {
                if (!card.isPlusNeeded()) {
                    if (card instanceof UserCard) {
                        App.getInstance().getDatabase().categoryDao().deleteCategoryUserCardCrossRefById(currentCategoryId, card.getId());
                        setAllCategoryCards();
                    }
                }
            }
        });
        cardsRecyclerView.setAdapter(cardsAdapter);
    }

    private void initTabsRecyclerView() {
        tabsRecyclerView = findViewById(R.id.tabs_recycler_view);
        tabsAdapter = new TabsAdapter();
        tabsAdapter.setOnTabCLickListener(new TabsAdapter.OnTabCLickListener() {
            @Override
            public void onClick(Tab tab) {
                if (tab.isPlusNeeded()) {
                    showAddCategoryDialog();
                } else {
                    currentCategoryId = tab.getId();
                    setAllCategoryCards();
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tabsRecyclerView.setLayoutManager(layoutManager);
        tabsRecyclerView.setAdapter(tabsAdapter);
    }

    private void showAddCategoryDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        ConstraintLayout dialogView = (ConstraintLayout) inflater.inflate(R.layout.dialog_category, null);

        final EditText etCategoryName = dialogView.findViewById(R.id.et_category_name);
        Button btnSaveCategory = dialogView.findViewById(R.id.btn_save_category);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etCategoryName.getText().toString().isEmpty()) {
                    String categoryName = etCategoryName.getText().toString();
                    CategoryEntity categoryEntity = new CategoryEntity(categoryName);
                    App.getInstance().getDatabase().categoryDao().insert(categoryEntity);
                    setAllCategoryCards();
                } else {
                    Toast.makeText(MainActivity.this, "Категория не введена", Toast.LENGTH_SHORT).show();
                }
                dialogBuilder.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void setAllCategoryCards() {
        List<Tab> tabs = new ArrayList<>();

        List<ICard> cards = new ArrayList<>();
        cards.add(new DefaultCard(-1, R.drawable.plus2, "", true));

        List<CategoryAndCards> categoryWithCardsList = App.getInstance().getDatabase().categoryDao().getCategoriesAndCards();
        for (int i = 0; i < categoryWithCardsList.size(); i++) {
            tabs.add(
                    new Tab(
                            categoryWithCardsList.get(i).category.categoryId,
                            categoryWithCardsList.get(i).category.name,
                            categoryWithCardsList.get(i).userCards.size(),
                            false,
                            getTabColor(i)
                    )
            );
            if (categoryWithCardsList.get(i).category.categoryId == currentCategoryId) {
                for (UserCardEntity userCardEntity : categoryWithCardsList.get(i).userCards) {
                    cards.add(new UserCard(userCardEntity.userCardId, userCardEntity.imagePath, userCardEntity.text, false));
                }
            }
        }
        tabs.add(new Tab(-1, "", -1, true, R.color.white));
        tabsAdapter.setTabs(tabs);
        cardsAdapter.setCards(cards);
    }
    private int getTabColor(int current) {
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.gray);
        colors.add(R.color.silver);
        colors.add(R.color.aqua);
        int index = current % colors.size();
        return colors.get(index);
    }
}
