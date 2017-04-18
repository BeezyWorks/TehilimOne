package com.mattaniahbeezy.wisechildtehilim.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.helpers.ViewUtil;

import java.util.List;

/**
 * Created by Mattaniah on 10/21/2015.
 */
public class SimpleAdapter extends ArrayAdapter {
    List<String> sectionTitles;
    Context context;
    ViewUtil viewFactory;

    public SimpleAdapter(Context context, List<String> sectionTitles) {
        super(context, R.layout.listitems);
        this.context = context;
        viewFactory = new ViewUtil(context);
        this.sectionTitles=sectionTitles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean isStyleDark = viewFactory.isStyleDark();
        View getView = View.inflate(context, R.layout.listitems, null);
        int textColor = isStyleDark ? R.color.listviewtext_dark : R.color.listviewtext_light;
        TextView title = (TextView) getView.findViewById(R.id.navTextItem);
        title.setTextColor(ContextCompat.getColorStateList(context, textColor));
        title.setText(sectionTitles.get(position));
        getView.setBackgroundResource(isStyleDark ? R.drawable.listview_selector_dark : R.drawable.listview_selector_light);
        return getView;
    }

    @Override
    public int getCount() {
        return sectionTitles.size();
    }
}
