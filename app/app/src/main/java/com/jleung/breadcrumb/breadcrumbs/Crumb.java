package com.jleung.breadcrumb.breadcrumbs;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

// Data structure to contain a single dropped breadcrumb
public class Crumb implements Comparable {

    private static final String TAG = Crumb.class.getName();

    private final UUID id;

    private final String description;

    private final LatLng location;

    private final Calendar time;

    public Crumb(String description, LatLng location, Calendar time) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.location = location;
        this.time = time;
    }

    // Only for use during conversion from JSON to Crumb
    private Crumb(UUID id, String description, LatLng location, Calendar time) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getLocationReadable() {
        return "Coordinates: " + location.latitude + ", " + location.longitude;
    }

    public Calendar getTime() {
        return time;
    }

    public String getTimeReadable() {

        String minute = String.valueOf(time.get(Calendar.MINUTE));
        // Add leading 0 to minutes
        if (minute.length() < 2) {
            minute = "0" + minute;
        }

        return time.get(Calendar.HOUR_OF_DAY) +
                ":" +
                minute +
                " | " +
                time.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +
                " " +
                time.get(Calendar.DAY_OF_MONTH) +
                ", " +
                time.get(Calendar.YEAR);
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", time=" + time +
                '}';
    }

    // Equality is determined by the ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crumb crumb = (Crumb) o;
        return id.equals(crumb.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Comparison uses timestamp
    @Override
    public int compareTo(Object o) {
        if (this == o) return -1;
        if (o == null || getClass() != o.getClass()) return -1;
        Crumb other = (Crumb) o;
        return time.compareTo(other.getTime());
    }

    // POJO representation and methods used for data transfer

    public static class CrumbData {

        public String id;
        public String description;
        public Double latitude;
        public Double longitude;
        public Long time;

        public CrumbData() {
        }

    }

    public CrumbData toData() {
        CrumbData data = new CrumbData();

        data.id = id.toString();
        data.description = description;
        data.latitude = location.latitude;
        data.longitude = location.longitude;
        data.time = time.getTimeInMillis();

        return data;
    }

    // Returns null if the crumb data is incomplete
    public static Crumb fromData(CrumbData data) {

        if (data.id == null || data.id.isEmpty()) {
            Log.e(TAG, "Received crumb data with no ID: " + data.toString());
            return null;
        }
        UUID id = UUID.fromString(data.id);

        if (data.description == null || data.description.isEmpty()) {
            Log.e(TAG, "Received crumb data with no description: " + data.toString());
            return null;
        }
        String description = data.description;

        if (data.latitude == null || data.longitude == null) {
            Log.e(TAG, "Received crumb data with no latitude or longitude: " + data.toString());
            return null;
        }
        LatLng location = new LatLng(data.latitude, data.longitude);

        if (data.time == null) {
            Log.e(TAG, "Received crumb data with no description: " + data.toString());
            return null;
        }
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(data.time);

        return new Crumb(id, description, location, time);
    }

}
