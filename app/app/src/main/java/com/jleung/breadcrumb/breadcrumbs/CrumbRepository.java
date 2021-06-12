package com.jleung.breadcrumb.breadcrumbs;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class CrumbRepository {

    private LinkedList<Crumb> crumbsList;

    public CrumbRepository() {
        crumbsList = new LinkedList<>();
    }

    public void create(String description, LatLng location, Calendar time) {
        Crumb c = new Crumb(description, location, time);
        crumbsList.addFirst(c);
    }

    public List<Crumb> getList() {
        return crumbsList;
    }

}
