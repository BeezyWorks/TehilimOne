package com.mattaniahbeezy.wisechildtehilim.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.adapters.ChapterAdapter;
import com.mattaniahbeezy.wisechildtehilim.helpers.HostActivity;
import com.mattaniahbeezy.wisechildtehilim.helpers.NavAnchors;
import com.mattaniahbeezy.wisechildtehilim.helpers.PerekNumber;
import com.mattaniahbeezy.wisechildtehilim.helpers.Tefilos;
import com.mattaniahbeezy.wisechildtehilim.helpers.ViewUtil;

import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mattaniah on 7/16/2015.
 */
public class NavigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView list;
    LinearLayout bottomContainer;
    HostActivity hostActivity;
    HebrewDateFormatter hebrewDateFormatter = new HebrewDateFormatter();
    ViewUtil viewUtil;
    PerekNumber perekNumber = new PerekNumber();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        hostActivity.closeDrawer();
        hostActivity.goToChapter(position - 1);
    }

    @Override
    public void onClick(View v) {
        hostActivity.closeDrawer();
        switch ((NavAnchors) v.getTag()) {
            case Yahrzeit:
                break;
            case Share:
                break;
            case Tefilos:
                showTefilos();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.hostActivity = (HostActivity) context;
        viewUtil = new ViewUtil(context);
    }

    public void setSelectedChapter(int chapter){
        if (!isAdded()||list==null)
            return;
        list.setItemChecked(chapter+1, true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.drawer_layout, container, false);
        list = (ListView) retView.findViewById(R.id.drawer_list);
        bottomContainer = (LinearLayout) retView.findViewById(R.id.bottom_container);
        hebrewDateFormatter.setUseGershGershayim(false);

        View headerView = inflater.inflate(R.layout.navigation_header, list, false);
        ImageView headerImage = (ImageView) headerView.findViewById(R.id.header_image);
        headerImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.accentBright), PorterDuff.Mode.MULTIPLY);
        list.addHeaderView(headerView);
        list.setDividerHeight(0);
        setListAdapter();
        list.setOnItemClickListener(this);
        createAnchorItems();
        return retView;
    }

    private void createAnchorItems() {
        bottomContainer.setBackgroundColor(viewUtil.getDarkOrLightFloatingBackground());
        bottomContainer.removeAllViews();
        for (NavAnchors tag : NavAnchors.values()) {
            View view = viewUtil.getRowView(tag.getDescription(), tag.getDrawableId(), getActivity().getLayoutInflater(), bottomContainer);
            view.setTag(tag);
            view.setOnClickListener(this);
            bottomContainer.addView(view);
        }
    }


    public void resetColor() {
        setListAdapter();
        createAnchorItems();
    }


    private void setListAdapter() {
        list.setAdapter(new ChapterAdapter(getActivity(), perekNumber, viewUtil));
        list.setBackgroundColor(viewUtil.getDarkOrLightBackground());
         }

    private void showTefilos() {
        List<String> tefilos = new ArrayList<>();
        for (Tefilos tefilos1 : Tefilos.values())
            tefilos.add(tefilos1.getDescription());
        new AlertDialog.Builder(getActivity())
                .setView(viewUtil.getSimpleListView(tefilos))
                .create()
                .show();
    }

}
