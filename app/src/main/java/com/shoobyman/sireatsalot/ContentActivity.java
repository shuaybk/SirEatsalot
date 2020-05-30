package com.shoobyman.sireatsalot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
    private final String ADD_CONTACT_TAG = "add contact";

    private ActivityContentBinding mBinding;
    private DiaryViewModel mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_content);
        mData = new ViewModelProvider(this).get(DiaryViewModel.class);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new DiaryFragment()).commit();
        init();
    }

    private void init() {
        mBinding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFrag;

                switch(menuItem.getItemId()) {
                    case R.id.nav_diary:
                        selectedFrag = new DiaryFragment();
                        break;
                    case R.id.nav_summary:
                        selectedFrag = new SummaryFragment();
                        break;
                    case R.id.nav_progress:
                        selectedFrag = new ProgressFragment();
                        break;
                    default:
                        Log.w(TAG, "The selected bottom naviation tab doesn't exist!");
                        return false;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, selectedFrag).commit();
                return true;
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

}
