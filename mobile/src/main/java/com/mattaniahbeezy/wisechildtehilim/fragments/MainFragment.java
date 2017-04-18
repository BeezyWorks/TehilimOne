package com.mattaniahbeezy.wisechildtehilim.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.helpers.HostActivity;
import com.mattaniahbeezy.wisechildtehilim.helpers.JSONHelper;
import com.mattaniahbeezy.wisechildtehilim.helpers.SettingsUtil;
import com.mattaniahbeezy.wisechildtehilim.helpers.ViewUtil;

import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;

import org.json.JSONObject;

/**
 * Created by Mattaniah on 2/22/2015.
 */
public abstract class MainFragment extends Fragment   {
    ViewUtil viewUtil;
    SettingsUtil settingsUtil;
    JSONObject allTehilim;
    HostActivity hostActivity;

    RecyclerView recyclerView;
    Toolbar toolbar;

    LinearLayoutManager linearLayoutManager;

    HebrewDateFormatter hFat = new HebrewDateFormatter();

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        allTehilim = new JSONHelper(activity).getFullJson();
        hostActivity = (HostActivity) activity;
        hFat.setUseGershGershayim(false);
        viewUtil=new ViewUtil(activity);
        settingsUtil=new SettingsUtil(activity);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tehilim_container, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setBackgroundColor(viewUtil.getBackgroundColor());

        toolbar.bringToFront();
        hostActivity.setDrawerToggle(toolbar);

        return view;
    }



    public void updateColor(){
         if (!isAdded()||recyclerView==null)
             return;
         recyclerView.setBackgroundColor(viewUtil.getBackgroundColor());
     };

    public void goToChapter(int chapter){}




}

