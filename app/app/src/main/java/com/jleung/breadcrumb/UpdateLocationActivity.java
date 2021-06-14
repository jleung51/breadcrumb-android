package com.jleung.breadcrumb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.model.LatLng;
import com.jleung.breadcrumb.breadcrumbs.Crumb;
import com.jleung.breadcrumb.breadcrumbs.CrumbAdapter;
import com.jleung.breadcrumb.breadcrumbs.CrumbRepository;
import com.jleung.breadcrumb.breadcrumbs.CrumbRepositoryFirebaseImpl;
import com.jleung.breadcrumb.breadcrumbs.NewCrumbDialog;
import com.jleung.breadcrumb.databinding.ActivityUpdateLocationBinding;

import java.util.Calendar;
import java.util.List;

public class UpdateLocationActivity extends AppCompatActivity
        implements NewCrumbDialog.NewCrumbDialogListener {

    private static final String TAG = UpdateLocationActivity.class.getName();

    private CrumbRepository crumbRepository;

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
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCrumbDialog dialog = new NewCrumbDialog();
                dialog.show(getSupportFragmentManager(), "Drop a Breadcrumb");
                // For responses, see onDialogPositiveClick / onDialogNegativeClick
            }
        });

        // Set up crumb repository and list adapter to read from the repository
        crumbRepository = new CrumbRepositoryFirebaseImpl();
        CrumbAdapter listAdapter = new CrumbAdapter(
                this, R.layout.layout_single_crumb, crumbRepository.getCrumbs()
        );

        // Synchronize updates to the crumb lit on screen
        crumbRepository.attachListAdapter(listAdapter);
        ListView listView = findViewById(R.id.crumbs_list);
        listView.setAdapter(listAdapter);

        // Send user to Google Maps when an item is pressed
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Crumb c = (Crumb) listView.getItemAtPosition(position);
                openGoogleMaps(c.getDescription(), c.getLocation());
            }
        });

    }

    private List<Crumb> crumbList;  // Reference to the list managed by crumbRepository

    private void openGoogleMaps(String description, LatLng location) {
        String descriptionEscapedChars = description.replace(" ", "%20");
        Uri gmapsUri = Uri.parse(
                "geo:0,0?q=" + location.latitude + "," + location.longitude +
                        "(" + descriptionEscapedChars + ")"
        );
        Log.d(TAG, "Opening URI in Google Maps: " + gmapsUri.toString());

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmapsUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String description) {
        // New crumb created from NewCrumbDialog

        if (description == null || description.isEmpty()) {
            description = "Location";
        }

        crumbRepository.create(
                description,
                new LatLng(50.998729, -118.19554),
                Calendar.getInstance()
        );
    }

    private void createSampleCrumbs() {

        Calendar timeRevelstoke = Calendar.getInstance();
        timeRevelstoke.setTimeInMillis(1623146700000L);
        crumbRepository.create(
                "Mount Revelstoke",
                new LatLng(50.998729, -118.19554),
                timeRevelstoke
        );

        Calendar timeRadium = Calendar.getInstance();
        timeRadium.setTimeInMillis(1623364660000L);
        crumbRepository.create(
                "Radium Hot Springs",
                new LatLng(50.6218902, -116.0808746),
                timeRadium
        );

    }

}

