package com.mattaniahbeezy.wisechildtehilim.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.adapters.ChapterRecycleAdapter;
import com.mattaniahbeezy.wisechildtehilim.helpers.PerekNumber;
import com.mattaniahbeezy.wisechildtehilim.helpers.RepeatListener;

/**
 * Created by Mattaniah on 2/15/2015.
 */
public class TehilimFragment extends MainFragment implements Toolbar.OnMenuItemClickListener{
    ChapterRecycleAdapter adapter;
    int chapterNumber;
    PerekNumber perekNumber = new PerekNumber();

    enum Tabs {
        English, Hebrew;

        public String getLable() {
            switch (this) {

                case English:
                    return "English";
                case Hebrew:
                    return "עברית";
            }
            return "I am error";
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.displayOptions:
                viewUtil.showDisplayOptions(hostActivity);
                return true;
            case R.id.goTo:
                goTo();
                return true;
        }
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ChapterRecycleAdapter(getActivity(), true);
        recyclerView.setAdapter(adapter);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);
        goToChapter(settingsUtil.getSavedChapter());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                newChapter();
            }
        });
        newChapter();

        TabLayout tableLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tableLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                recyclerView.setAdapter(new ChapterRecycleAdapter(getActivity(), tab.getTag() == Tabs.Hebrew));
                goToChapter(settingsUtil.getSavedChapter());
                perekNumber.setUseHebrew(tab.getTag() == Tabs.Hebrew);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (Tabs tab : Tabs.values()) {
            TabLayout.Tab tabby = tableLayout.newTab().setTag(tab).setText(tab.getLable());
            tableLayout.addTab(tabby, tab == Tabs.Hebrew);
        }

        view.findViewById(R.id.scrollButton).setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollDown();
            }
        }));
    }

    private void scrollDown() {
        if (!isAdded() || recyclerView == null)
            return;
        recyclerView.smoothScrollBy(0, (int) getResources().getDimension(R.dimen.small_padding));
    }

    @Override
    public void updateColor() {
        super.updateColor();
        int scrollTo = linearLayoutManager.findFirstVisibleItemPosition();
        adapter = new ChapterRecycleAdapter(getActivity(), adapter.isHebrew);
        recyclerView.setAdapter(adapter);
        linearLayoutManager.scrollToPosition(scrollTo);
    }



    @Override
    public void goToChapter(int chapter) {
        if (!isAdded() || recyclerView == null)
            return;
        linearLayoutManager.scrollToPositionWithOffset(chapter, 0);
    }
    private void goTo() {
        View view = viewUtil.getEditTextOption(null, "1", InputType.TYPE_CLASS_NUMBER, "Go to פרק");
        final EditText editText = (EditText) view.findViewById(R.id.editText);
        int padding = (int) getActivity().getResources().getDimension(R.dimen.activity_horizontal_margin);
        view.setPadding(padding, padding, padding, padding);
        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int enteredChapter = Integer.parseInt(editText.getText().toString());
                        enteredChapter-=1;
                        if (enteredChapter > 149)
                            enteredChapter = 149;
                        if (enteredChapter < 0)
                            enteredChapter = 0;
                        hostActivity.goToChapter(enteredChapter);
                    }
                })
                .create()
                .show();
    }


    private void newChapter() {
        if (!isAdded() || linearLayoutManager == null || linearLayoutManager.findFirstVisibleItemPosition() == chapterNumber)
            return;
        chapterNumber = linearLayoutManager.findFirstVisibleItemPosition();
        toolbar.setTitle(perekNumber.getGroupTitles(chapterNumber));
        toolbar.setSubtitle(perekNumber.getChapterNameHebrew(chapterNumber));
        settingsUtil.saveChapter(chapterNumber);
        hostActivity.informNewChapter(chapterNumber);
    }


}
