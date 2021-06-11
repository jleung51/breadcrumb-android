package com.jleung.breadcrumb.breadcrumbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jleung.breadcrumb.R;

import java.util.List;

public class CrumbAdapter extends ArrayAdapter<Crumb> {

    private final int layoutResource;

    public CrumbAdapter(@NonNull Context context, int resource, @NonNull List<Crumb> crumbs) {
        super(context, resource, crumbs);
        layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable @org.jetbrains.annotations.Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        // Set crumb contents
        Crumb c = getItem(position);
        if (c != null) {

            TextView desc = view.findViewById(R.id.crumb_desc);
            if (desc != null) {
                desc.setText(c.getDescription());
            }

            TextView time = view.findViewById(R.id.crumb_time);
            if (time != null) {
                time.setText(c.getTimeReadable());
            }

            TextView location = view.findViewById(R.id.crumb_location);
            if (location != null) {
                location.setText(c.getLocationReadable());
            }

        }

        return view;
    }



}
