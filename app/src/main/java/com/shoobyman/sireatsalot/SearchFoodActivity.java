package com.shoobyman.sireatsalot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shoobyman.sireatsalot.Adapters.SearchResultsRecyclerViewAdapter;
import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.Utils.JSONUtils;
import com.shoobyman.sireatsalot.databinding.ActivitySearchFoodBinding;

import java.util.ArrayList;


public class SearchFoodActivity extends AppCompatActivity implements SearchResultsRecyclerViewAdapter.SearchItemClickListener {

    private final String TAG = this.getClass().getSimpleName();
    ActivitySearchFoodBinding mBinding;
    private DiaryViewModel mData;
    SearchResultsRecyclerViewAdapter rvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_food);
        mData = new ViewModelProvider(this).get(DiaryViewModel.class);
        init();
    }

    public void init() {
        rvAdapter = new SearchResultsRecyclerViewAdapter(this, mData.searchResultList, this);
        mBinding.searchResultRecyclerview.setAdapter(rvAdapter);
        mBinding.searchResultRecyclerview.setLayoutManager(new LinearLayoutManager(this));
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
        rvAdapter.updateSearchResults(resultList);
        rvAdapter.notifyDataSetChanged();
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
                if (newText.length() > 0) {
                    mBinding.searchResultRecyclerview.setVisibility(View.VISIBLE);
                    mData.getFoodSearchResults(newText, SearchFoodActivity.this);
                } else {
                    mBinding.searchResultRecyclerview.setVisibility(View.GONE);
                    mData.searchResultList.clear();
                }
                return false;
            }
        });
    }

    @Override
    public void onSearchItemClick(Food food) {
        Toast.makeText(this, "Clicked " + food.getName(), Toast.LENGTH_SHORT).show();
    }
}
