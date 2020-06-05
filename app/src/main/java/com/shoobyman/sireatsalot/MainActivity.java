package com.shoobyman.sireatsalot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.shoobyman.sireatsalot.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private static final int RC_SIGN_IN = 1;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        if (fbAuth.getCurrentUser() != null) { //proceed to diary activity
            launchChatActivity();
        } else { //display sign in prompt
            mBinding.mainContainer.setVisibility(View.VISIBLE);
            mBinding.loadingContainer.setVisibility(View.GONE);

            mBinding.signInText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Launch FB authentication activity flow
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build());

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                //signed in
                //start chat activity
                launchChatActivity();
            } else {
                //sign in failed
                Toast.makeText(this, getString(R.string.sign_in_failed_message), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void launchChatActivity() {
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }
}