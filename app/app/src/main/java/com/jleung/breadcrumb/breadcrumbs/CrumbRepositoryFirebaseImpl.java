package com.jleung.breadcrumb.breadcrumbs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CrumbRepositoryFirebaseImpl implements CrumbRepository {

    private static final String TAG = CrumbRepositoryFirebaseImpl.class.getName();

    private final FirebaseDatabase database;

    private final LinkedList<Crumb> crumbList;

    private CrumbAdapter listAdapter;

    public CrumbRepositoryFirebaseImpl() {

        // Must be called before all other calls
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        database = FirebaseDatabase.getInstance();
        crumbList = new LinkedList<>();

        // Subscribe to crumb changes
        DatabaseReference crumbsRef = database.getReference("crumbs");

        crumbsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: "  + snapshot.getKey());

                // Get crumb data
                Crumb.CrumbData data = snapshot.getValue(Crumb.CrumbData.class);
                if (data == null) {
                    Log.e(TAG, "Received crumb data is null.");
                    return;
                }

                // Add crumb to repository
                Crumb c = Crumb.fromData(data);
                if (crumbList.contains(c)) {
                    Log.w(TAG, "Skipping already existing crumb: " + c);
                    return;
                }
                crumbList.add(c);

                // Sort in descending order of timestamp
                Collections.sort(crumbList, Collections.reverseOrder());

                logCurrentCrumbs();
                updateListOnScreen();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged: "  + snapshot.getKey());

                // Search for crumb with matching ID
                // Overwrite if found

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved: "  + snapshot.getKey());

                Crumb.CrumbData data = snapshot.getValue(Crumb.CrumbData.class);
                if (data == null) {
                    Log.e(TAG, "Received crumb data is null.");
                    return;
                }

                Log.i(TAG, "Crumb to delete: " + Crumb.fromData(data));

                // Delete matching crumb
                // No effect if the matching crumb is not found
                crumbList.remove(Crumb.fromData(data));

                logCurrentCrumbs();
                updateListOnScreen();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildMoved: "  + snapshot.getKey());
                // No action taken
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: "  + error.toException());
                // No action taken
            }
        });
    }

    public void attachListAdapter(CrumbAdapter adapter) {
        listAdapter = adapter;
    }

    public void create(String description, LatLng location, Calendar time) {

        // Create an empty new crumb object in the database
        DatabaseReference crumbsRef = database.getReference().child("crumbs");
        String newCrumbKey = crumbsRef.push().getKey();
        if (newCrumbKey == null || newCrumbKey.isEmpty()) {
            throw new RuntimeException("Failed to create a new crumb object in the database.");
        }

        // Update the empty database object with the crumb data
        Crumb c = new Crumb(description, location, time);
        crumbsRef.child(newCrumbKey).setValue(c.toData());

    }

    public List<Crumb> getCrumbs() {
        return crumbList;
    }

    private void logCurrentCrumbs() {
        Log.d(TAG, "Current crumbs:");
        for (Crumb cr : crumbList) {
            Log.d(TAG, "    " + cr.toString());
        }
    }

    private void updateListOnScreen() {
        if(listAdapter == null) {
            Log.w(TAG, "Unable to update list; no list adapter is attached to " +
                    "the CrumbRepository. This is acceptable on startup.");
            return;
        }

        listAdapter.notifyDataSetChanged();
    }

}
