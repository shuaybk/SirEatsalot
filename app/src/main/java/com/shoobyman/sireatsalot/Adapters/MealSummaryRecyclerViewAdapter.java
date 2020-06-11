package com.shoobyman.sireatsalot.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoobyman.sireatsalot.POJOs.FoodEntry;
import com.shoobyman.sireatsalot.R;

import java.util.ArrayList;

public class MealSummaryRecyclerViewAdapter extends RecyclerView.Adapter<MealSummaryRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "MealSummaryRecyclerViewAdapter";
    private ArrayList<FoodEntry> mealEntryList;
    private Context mContext;
    private MealItemClickListener mealItemClickListener;

    public interface MealItemClickListener {
        void onMealItemClick(FoodEntry foodEntry);
        void onMealItemLongClick(FoodEntry foodEntry);
    }

    public MealSummaryRecyclerViewAdapter(Context context, ArrayList<FoodEntry> mealEntryList, MealItemClickListener mealItemClickListener) {
        this.mContext = context;
        this.mealEntryList = mealEntryList;
        this.mealItemClickListener = mealItemClickListener;
    }

    public void updateMealEntries(ArrayList<FoodEntry> newEntries) {
        this.mealEntryList = newEntries;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_summary_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodEntry foodEntry = mealEntryList.get(position);
        System.out.println(""+ mealEntryList.size());
        System.out.println(foodEntry.getFoodName());

        holder.foodName.setText(foodEntry.getFoodName());
        holder.servingAmount.setText(foodEntry.getServingSummary());
        holder.calorieCount.setText(""+foodEntry.getCalories());
    }

    @Override
    public int getItemCount() {
        return mealEntryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView foodName;
        TextView servingAmount;
        TextView calorieCount;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            servingAmount = itemView.findViewById(R.id.serving_amount);
            calorieCount = itemView.findViewById(R.id.calorie_count);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            FoodEntry foodEntry = mealEntryList.get(getAdapterPosition());
            mealItemClickListener.onMealItemClick(foodEntry);
        }

        @Override
        public boolean onLongClick(View v) {
            FoodEntry foodEntry = mealEntryList.get(getAdapterPosition());

            return true;
        }
    }
}
