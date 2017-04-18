package com.mattaniahbeezy.wisechildtehilim.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.helpers.JSONHelper;
import com.mattaniahbeezy.wisechildtehilim.helpers.PerekNumber;
import com.mattaniahbeezy.wisechildtehilim.helpers.ViewUtil;

import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Mattaniah on 7/16/2015.
 */
public class ChapterRecycleAdapter extends RecyclerView.Adapter {
    Context context;
    JSONArray tehilimHebrew;
    ViewUtil viewUtil;
    PerekNumber perekNumber;
    public boolean isHebrew;

    /*
    * @param hebrew true if should display text in Hebrew, false for English
    * */
    public ChapterRecycleAdapter(Context context, boolean hebrew) {
        this.context = context;
        this.isHebrew=hebrew;
        viewUtil = new ViewUtil(context);
        perekNumber= new PerekNumber(hebrew);
        try {
            tehilimHebrew = new JSONHelper(context).getFullJson().getJSONArray(isHebrew? JSONHelper.hebrewKey: JSONHelper.englishKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle;
        TextView chapterTitle;
        TextView chapterText;
        ImageView divider;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            chapterText = (TextView) itemView.findViewById(R.id.hebrewTextView);
            chapterTitle = (TextView) itemView.findViewById(R.id.title);
            sectionTitle = (TextView) itemView.findViewById(R.id.sectionTitle);
            divider= (ImageView) itemView.findViewById(R.id.divider);

            divider.setColorFilter(viewUtil.getDividerColor(), PorterDuff.Mode.SRC_ATOP);
            chapterTitle.setTextColor(ContextCompat.getColor(context, viewUtil.isStyleDark() ? R.color.primaryBright : R.color.primaryDark));
            sectionTitle.setTextColor(ContextCompat.getColor(context, viewUtil.isStyleDark() ? R.color.accentBright : R.color.accent));
        }

        public void setChapterNumber(int chapterNumber) throws JSONException {
            divider.setVisibility(chapterNumber==0?View.GONE:View.VISIBLE);
            HebrewDateFormatter hebrewDateFormatter = new HebrewDateFormatter();
            hebrewDateFormatter.setUseGershGershayim(false);
            StringBuilder builder = new StringBuilder();
            JSONArray text = tehilimHebrew.getJSONArray(chapterNumber);

            for (int i = 0; i < text.length(); i++) {
                builder.append("<b>")
                        .append(isHebrew? hebrewDateFormatter.formatHebrewNumber(i + 1):(i+1))
                        .append("</b> ")
                        .append(text.getString(i))
                        .append(" ");

            }
            chapterText.setText(Html.fromHtml(builder.toString()));
            chapterTitle.setText(perekNumber.getChapterNameHebrew(chapterNumber));
            boolean isFisrt=perekNumber.isFirstInAny(chapterNumber);
            sectionTitle.setVisibility(isFisrt ? View.VISIBLE : View.GONE);
            if (isFisrt)
                sectionTitle.setText(perekNumber.getFirstTitles(chapterNumber));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(View.inflate(context, R.layout.listview_subheader, null));
        linearLayout.addView(viewUtil.getTextView());
        return new ChapterViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ((ChapterViewHolder) holder).setChapterNumber(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return tehilimHebrew.length();
    }
}
