package com.shoobyman.sireatsalot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;

import com.shoobyman.sireatsalot.databinding.ActivitySearchFoodBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    ActivitySearchFoodBinding mBinding;
    private DiaryViewModel mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_food);
        mData = new ViewModelProvider(this).get(DiaryViewModel.class);
        //returnResult();
    }

    public void returnResult() {
        Intent intent = new Intent();
        setResult(ContentActivity.RESULT_CODE_FOUND_FOOD, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_food_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        //ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        //searchView.setLayoutParams(params);
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
                mData.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}
