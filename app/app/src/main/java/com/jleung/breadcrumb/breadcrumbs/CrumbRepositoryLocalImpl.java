package com.jleung.breadcrumb.breadcrumbs;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class CrumbRepositoryLocalImpl implements CrumbRepository {

    private final LinkedList<Crumb> crumbsList;

    public CrumbRepositoryLocalImpl() {
        crumbsList = new LinkedList<>();
    }

    @Override
    public void attachListAdapter(CrumbAdapter adapter) {
        // No action needed
    }

    public void create(String description, LatLng location, Calendar time) {
        Crumb c = new Crumb(description, location, time);
        crumbsList.add(c);
        Collections.sort(crumbsList, Collections.reverseOrder());
    }

    public List<Crumb> getCrumbs() {
        return crumbsList;
    }

}
