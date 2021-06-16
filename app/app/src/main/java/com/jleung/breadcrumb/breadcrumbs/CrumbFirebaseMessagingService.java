package com.jleung.breadcrumb.breadcrumbs;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.jleung.breadcrumb.R;

public class CrumbFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = CrumbFirebaseMessagingService.class.getName();

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Received new Firebase messaging token: "  + token);

        // Send to server
    }

    public static void displayToken(Activity currentActivity) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Display token in logs
                String token = task.getResult();
                Log.d(TAG, "Firebase messaging token: " + token);
        });
    }
}
