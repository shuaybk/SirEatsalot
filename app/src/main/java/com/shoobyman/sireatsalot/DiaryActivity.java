package com.shoobyman.sireatsalot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.shoobyman.sireatsalot.databinding.ActivityDiaryBinding;

public class DiaryActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private final String ADD_CONTACT_TAG = "add contact";

    private ActivityDiaryBinding mBinding;
    private DiaryViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_diary);
        model = new ViewModelProvider(this).get(DiaryViewModel.class);
        mBinding.usernameText.setText("User is: uid=" + model.getUser().getUid() + ", display name = " + model.getUser().getDisplayName());
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
                model.signOut(this);
                return true;
            case R.id.add_food_menu_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
