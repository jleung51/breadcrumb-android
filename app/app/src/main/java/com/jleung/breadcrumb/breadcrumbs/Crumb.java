package com.jleung.breadcrumb.breadcrumbs;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Locale;

// Data structure to contain a single dropped breadcrumb
public class Crumb {

    private String description;

    private final LatLng location;

    private final Calendar time;

    public Crumb(String description, LatLng location, Calendar time) {
        this.description = description;
        this.location = location;
        this.time = time;
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
        return time.get(Calendar.HOUR_OF_DAY) +
                ":" +
                time.get(Calendar.MINUTE) +
                " | " +
                time.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +
                " " +
                time.get(Calendar.DAY_OF_MONTH) +
                ", " +
                time.get(Calendar.YEAR);
    }

}
