package com.mattaniahbeezy.wisechildtehilim.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.helpers.PerekNumber;
import com.mattaniahbeezy.wisechildtehilim.helpers.ViewUtil;

/**
 * Created by Mattaniah on 10/25/2015.
 */
public class ChapterAdapter extends ArrayAdapter {
    ViewUtil viewUtil;
    PerekNumber perekNumber;
    Activity context;

    public ChapterAdapter(Activity context, PerekNumber perekNumber, ViewUtil viewUtil) {
        super(context, R.layout.navigation_item);
        this.context=context;
        this.perekNumber=perekNumber;
        this.viewUtil=viewUtil;
    }

    public void setPerekNumber(PerekNumber perekNumber) {
        this.perekNumber = perekNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(R.layout.navigation_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(perekNumber.getChapterNameHebrew(position));
        textView.setTextColor(viewUtil.getListViewTextColor());
        return view;
    }

    @Override
    public int getCount() {
        return 150;
    }
}
