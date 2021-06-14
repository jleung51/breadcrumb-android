package com.jleung.breadcrumb.breadcrumbs;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;

public interface CrumbRepository {

    void attachListAdapter(CrumbAdapter adapter);

    void create(String description, LatLng location, Calendar time);

    List<Crumb> getCrumbs();

}
