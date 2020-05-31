package com.shoobyman.sireatsalot;

import android.content.Context;
import android.content.Intent;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DiaryViewModel extends ViewModel implements Filterable {

    private final String TAG = this.getClass().getSimpleName();
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    private int currentFragment = ContentActivity.FRAG_DIARY;
    List<String> testListFull = new ArrayList<String>() {{
        add("hello");
        add("Hello!");
        add("Bye!");
        add("Bounjour");
        add("Lol");
    }};
    List<String> testListCurr = new ArrayList<>(testListFull);

    public FirebaseUser getUser() {
        return fbAuth.getCurrentUser();
    }

    public void signOut(final Context context) {
        //Display loading UI here!
        AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    //Make sure to call this when changing the fragment so it can be restored on activity recreation
    public void saveNewFragmentState(int fragmentId) {
        currentFragment = fragmentId;
    }

    public int getLastFragmentDisplayed() {
        return currentFragment;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(testListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : testListFull) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            testListCurr.clear();
            testListCurr.addAll((List) results.values);
            System.out.println("SEARCH HAS BEEN COMPLETEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            for (String str: testListCurr) {
                System.out.println(str);
            }
        }
    };

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
