package com.shoobyman.sireatsalot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.Utils.JSONUtils;
import com.shoobyman.sireatsalot.databinding.ActivitySearchFoodBinding;

import java.util.ArrayList;


public class SearchFoodActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    ActivitySearchFoodBinding mBinding;
    private DiaryViewModel mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_food);
        mData = new ViewModelProvider(this).get(DiaryViewModel.class);
    }

    public void returnResult() {
        Intent intent = new Intent();
        setResult(ContentActivity.RESULT_CODE_FOUND_FOOD, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_main_menu, menu); //Main menu
        initSearchBar(mBinding.searchBar.getMenu()); //Sub menu

        return true;
    }

    public void setResultsPreview(ArrayList<Food> resultList) {
        String output = "";
        for (Food food: resultList) {
            System.out.println("Id="+food.getId() + ", name="+food.getName()+", type="+food.getType()+", brand="+food.getBrand()+", description="+food.getDescription()+", url="+food.getUrl());
            output=output+food.getName()+"\n";
        }
        output+=resultList.size();
        mBinding.searchResultsPreview.setText(output);
    }

    private void initSearchBar(Menu menu) {
        getMenuInflater().inflate(R.menu.search_food_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchItem.expandActionView();

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mData.getFoodSearchResults(newText, SearchFoodActivity.this);
                return false;
            }
        });
    }

}
