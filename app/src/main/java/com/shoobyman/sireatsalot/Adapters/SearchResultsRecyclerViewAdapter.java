package com.shoobyman.sireatsalot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.R;

import java.util.ArrayList;

public class SearchResultsRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "SearchResultsRecyclerViewAdapter";
    private ArrayList<Food> searchResultList;
    private Context mContext;
    private SearchItemClickListener searchItemClickListener;

    public interface SearchItemClickListener {
        void onSearchItemClick(Food food);
    }

    public SearchResultsRecyclerViewAdapter(Context context, ArrayList<Food> searchResultList, SearchItemClickListener searchItemClickListener) {
        this.mContext = context;
        this.searchResultList = searchResultList;
        this.searchItemClickListener = searchItemClickListener;
    }

    public void updateSearchResults(ArrayList<Food> newResults) {
        this.searchResultList = newResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = searchResultList.get(position);
        System.out.println(""+searchResultList.size());
        System.out.println(food.getName());

        holder.foodName.setText(food.getName());

        if (food.getBrand().equals("")) {  //Display the food type if there is no brand
            holder.foodBrand.setText(food.getType());
        } else {
            holder.foodBrand.setText(food.getBrand());
        }
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView foodName;
        TextView foodBrand;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodBrand = itemView.findViewById(R.id.food_brand);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Food food = searchResultList.get(getAdapterPosition());
            searchItemClickListener.onSearchItemClick(food);
        }
    }
}
