package com.shoobyman.sireatsalot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shoobyman.sireatsalot.Fragments.DiaryFragment;
import com.shoobyman.sireatsalot.Fragments.ProgressFragment;
import com.shoobyman.sireatsalot.Fragments.SummaryFragment;
import com.shoobyman.sireatsalot.databinding.ActivityContentBinding;

public class ContentActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    public static final int FRAG_DIARY = 1;
    public static final int FRAG_SUMMARY = 2;
    public static final int FRAG_PROGRESS = 3;

    private ActivityContentBinding mBinding;
    private MainViewModel mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_content);
        mData = new ViewModelProvider(this).get(MainViewModel.class);

        init();
    }

    private void init() {
        switch (mData.getLastFragmentDisplayed()) {
            case FRAG_DIARY:
                displayDiaryFrag();
                break;
            case FRAG_SUMMARY:
                displaySummaryFrag();
                break;
            case FRAG_PROGRESS:
                displayProgressFrag();
                break;
        }

        mBinding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {
                    case R.id.nav_diary:
                        displayDiaryFrag();
                        return true;
                    case R.id.nav_summary:
                        displaySummaryFrag();
                        return true;
                    case R.id.nav_progress:
                        displayProgressFrag();
                        return true;
                    default:
                        Log.w(TAG, "The selected bottom navigation tab doesn't exist!");
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diary_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out_menu_item:
                mData.signOut(this);
                return true;
            case R.id.add_food_menu_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayDiaryFrag() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new DiaryFragment()).commit();
        mData.saveNewFragmentState(FRAG_DIARY);
    }

    public void displaySummaryFrag() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new SummaryFragment()).commit();
        mData.saveNewFragmentState(FRAG_SUMMARY);
    }

    public void displayProgressFrag() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new ProgressFragment()).commit();
        mData.saveNewFragmentState(FRAG_PROGRESS);
    }

}
