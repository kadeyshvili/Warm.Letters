package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Tab;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.TabViewHolder> {

    private List<Tab> tabs;
    private OnTabCLickListener onTabCLickListener;

    TabsAdapter() {
    }

    public TabsAdapter(List<Tab> tabs) {
        this.tabs = tabs;
    }

    void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
        notifyDataSetChanged();
    }

    public void setOnTabCLickListener(OnTabCLickListener onTabCLickListener) {
        this.onTabCLickListener = onTabCLickListener;
    }

    @NonNull
    @Override
    public TabsAdapter.TabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_tab, viewGroup, false);
        return new TabsAdapter.TabViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull TabViewHolder viewHolder, int i) {
        final Tab tab = tabs.get(i);

        viewHolder.itemView.setBackgroundResource(tab.getColorRes());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTabCLickListener!=null) {
                    onTabCLickListener.onClick(tab);
                }
            }
        });

        if (tab.isPlusNeeded()) {
            viewHolder.textContainer.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textContainer.setVisibility(View.VISIBLE);
            viewHolder.image.setVisibility(View.GONE);
            viewHolder.name.setText(tab.getName());
            viewHolder.number.setText(String.valueOf(tab.getNumber()));
        }
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }

    static class TabViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout textContainer;
        TextView name;
        TextView number;
        ImageView image;

        TabViewHolder(View itemView) {
            super(itemView);
            textContainer = itemView.findViewById(R.id.tab_text_container);
            name = itemView.findViewById(R.id.tab_name);
            number = itemView.findViewById(R.id.tab_number);
            image = itemView.findViewById(R.id.tab_image);
        }
    }

    interface OnTabCLickListener {
        public void onClick(Tab tab);
    }
}
