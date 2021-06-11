package com.jleung.breadcrumb;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jleung.breadcrumb.databinding.ActivityUpdateLocationBinding;

import java.util.ArrayList;
import java.util.List;

public class UpdateLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUpdateLocationBinding binding = ActivityUpdateLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up action bar
        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_update_location);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up floating action button
        binding.fab.setOnClickListener(view -> Snackbar
                .make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        );

        // Set up list of crumbs
        List<String> crumbsList = new ArrayList<>();
        crumbsList.add("Crumb 1");
        crumbsList.add("Crumb 2");
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, crumbsList
        );

        ListView listView = findViewById(R.id.crumbs_list);
        listView.setAdapter(listAdapter);
    }

}