package com.mattaniahbeezy.wisechildtehilim.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.mattaniahbeezy.wisechildtehilim.R;
import com.mattaniahbeezy.wisechildtehilim.adapters.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mattaniah on 7/16/2015.
 */
public class ViewUtil {
    Context context;

    public ViewUtil(Context context) {
        this.context = context;
    }

    public enum EnglishOptions {
        NO_ENGLISH, DIALOG, SPLIT;
        public EnglishOptions value = this;
        public static String key = "englishKey";

        public String getTitle() {
            switch (value) {

                case NO_ENGLISH:
                    return "No English";
                case SPLIT:
                    return "Split Screen";
                case DIALOG:
                    return "Tap for Dialog";

            }
            return "I am Error";
        }

        public static EnglishOptions getEnglishOption(Context context) {
            String setting = PreferenceManager.getDefaultSharedPreferences(context).getString(key, EnglishOptions.NO_ENGLISH.getTitle());
            for (EnglishOptions options : EnglishOptions.values())
                if (options.getTitle().equals(setting))
                    return options;
            return EnglishOptions.NO_ENGLISH;
        }

        public void setAsDefault(Context context) {
            new SettingsUtil(context).getSharedPreferences().edit().putString(key, this.getTitle()).apply();
        }
    }


    public enum Style {
        SEPIA, NIGHT, DAY, AMOLED;

        public static final String key = "key:Style";
        public Style value = this;

        public String getThemeName() {
            switch (value) {
                case SEPIA:
                    return "Sepia";
                case NIGHT:
                    return "Night";
                case DAY:
                    return "Day";
                case AMOLED:
                    return "AMOLED Night";
            }
            return "I am error";
        }

        public void setAsDefault(Context context) {
            new SettingsUtil(context).getSharedPreferences().edit().putString(key, this.getThemeName()).apply();
        }

        public static Style getSavedStyle(Context context) {
            String setting = PreferenceManager.getDefaultSharedPreferences(context).getString(key, DAY.getThemeName());
            for (Style options : Style.values())
                if (options.getThemeName().equals(setting))
                    return options;
            return DAY;
        }

        public int getBackgroundColor() {
            switch (value) {
                case SEPIA:
                    return R.color.sepia;
                case NIGHT:
                    return R.color.background_material_dark;
                case DAY:
                    return R.color.background_material_light;
                case AMOLED:
                    return android.R.color.black;
            }
            return R.color.background_material_light;

        }

        public int getTextColor() {
            switch (value) {
                case SEPIA:
                    return R.color.abc_primary_text_material_light;
                case NIGHT:
                    return R.color.abc_primary_text_material_dark;
                case DAY:
                    return R.color.abc_primary_text_material_light;
                case AMOLED:
                    return R.color.abc_primary_text_material_dark;
            }
            return R.color.background_material_light;

        }

        public int getSpecialTextColor() {
            switch (value) {
                case SEPIA:
                    return R.color.accent_material_light;
                case NIGHT:
                    return R.color.accent_material_dark;
                case DAY:
                    return R.color.accent_material_light;
                case AMOLED:
                    return R.color.accent_material_dark;
            }
            return R.color.background_material_light;

        }
    }

    public int getTextColor() {
        return ContextCompat.getColor(context, Style.getSavedStyle(context).getTextColor());
    }

    public int getSpecialTextColor(Style style) {
        return ContextCompat.getColor(context, style.getSpecialTextColor());
    }

    public int getBackgroundColor() {
        return ContextCompat.getColor(context, Style.getSavedStyle(context).getBackgroundColor());
    }

    public int getDividerColor() {
        return ContextCompat.getColor(context, isStyleDark() ? R.color.dividerDark : R.color.dividerLight);
    }

    public ColorStateList getListViewTextColor(){
        return ContextCompat.getColorStateList(context, isStyleDark() ? R.color.listviewtext_dark : R.color.listviewtext_light);
    }

    public boolean isStyleDark() {
        Style style = Style.getSavedStyle(context);
        return style == Style.NIGHT || style == Style.AMOLED;
    }

    public ListView getSimpleListView(List<String> title) {
        ListView listView = new ListView(context);
        listView.setDividerHeight(0);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(new SimpleAdapter(context, title));
        listView.setBackgroundColor(getDarkOrLightBackground());
        return listView;
    }

    public View getRowView(String title, Drawable drawable, LayoutInflater inflater, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.drawerbottomitems, parent, false);
        rowView.findViewById(R.id.divider).setVisibility(View.GONE);

        TextView textView = (TextView) rowView.findViewById(R.id.bottomItemTitle);
        textView.setText(title);
        textView.setTextColor(ContextCompat.getColorStateList(context, isStyleDark() ? R.color.listviewtext_dark : R.color.listviewtext_light));


        ImageView imageView = (ImageView) rowView.findViewById(R.id.bottomItemImage);
        imageView.setImageDrawable(drawable);
        imageView.setColorFilter(ContextCompat.getColor(context, isStyleDark() ? R.color.background_material_light : R.color.secondary_text_default_material_light), PorterDuff.Mode.SRC_ATOP);

        rowView.setBackgroundResource(isStyleDark() ? R.drawable.listview_selector_dark : R.drawable.listview_selector_light);

        return rowView;
    }

    public View getRowView(String title, int drawableId, LayoutInflater inflater, ViewGroup parent) {
        return getRowView(title, ContextCompat.getDrawable(context, drawableId), inflater, parent);
    }

    public int getDarkOrLightBackground() {
        return ContextCompat.getColor(context, isStyleDark() ? R.color.background_material_dark : R.color.background_material_light);
    }

    public int getDarkOrLightFloatingBackground(){
        return ContextCompat.getColor(context, isStyleDark() ? R.color.background_floating_material_dark : R.color.background_floating_material_light);
    }


    public Typeface getTypeface() {
        String key = context.getString(R.string.typefaceKey);
        String defaultValue = "TaameyFrankCLM.ttf";
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return Typeface.createFromAsset(context.getAssets(), sharedPref.getString(key, defaultValue));
        } catch (Exception e) {
            sharedPref.edit().putString(key, defaultValue).apply();
            return getTypeface();
        }
    }

    public float getTextSize() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getString(R.string.sizeKey);
        float size;
        try {
            size = Float.parseFloat(sharedPref.getString(key, "30"));
        } catch (NumberFormatException ex) {
            sharedPref.edit().putString(key, "30").apply();
            return getTextSize();
        }
        return size;
    }

    public float getEnglishTextSize() {
        return getTextSize() + context.getResources().getInteger(R.integer.small_text_modifier);
    }

    public View getSpinnerOption(final String key, final List<String> values, String Title) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.spinner_option, null);
        Spinner spinner = (Spinner) retView.findViewById(R.id.optionSpinner);
        TextView textView = (TextView) retView.findViewById(R.id.optionTitle);
        textView.setText(Title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1,
                values);
        spinner.setAdapter(adapter);
        int selection = 0;
        while (!sharedPref.getString(key, values.get(0)).equals(values.get(selection)))
            selection++;
        spinner.setSelection(selection);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPref.edit().putString(key, values.get(position)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return retView;
    }

    public View getEditTextOption(final String key, String defaultValue, int inputType, String title) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View retView = inflater.inflate(R.layout.edittext_option, null);
        TextView titleTv = (TextView) retView.findViewById(R.id.editTextTitle);
        EditText editText = (EditText) retView.findViewById(R.id.editText);

        titleTv.setText(title);
        editText.setInputType(inputType);
        editText.setText(key==null? defaultValue: sharedPref.getString(key, defaultValue));

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0&&key!=null) {
                    sharedPref.edit().putString(key, s.toString().trim()).apply();
                }
            }
        });

        return retView;
    }

    public View getRadioView(String key, String[] values) {
        return getRadioView(key, values, values);
    }

    public View getRadioView(final String key, String[] titles, String[] values) {
        RadioGroup radioGroup = new RadioGroup(context);
        String currentValue = PreferenceManager.getDefaultSharedPreferences(context).getString(key, values[0]);
        for (int i = 0; i < values.length; i++) {
            RadioButton button = new RadioButton(context);
            button.setText(titles[i]);
            radioGroup.addView(button);
            final String value = values[i];
            button.setChecked(currentValue.equals(value));
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                        editor.putString(key, value).apply();
                    }

                }
            });
            setViewProperties(button);
        }
        radioGroup.setGravity(Gravity.CENTER_VERTICAL);
        return radioGroup;
    }

    public CheckBox getCheckboxPreferenceView(String key, String title) {
        return getCheckboxPreferenceView(key, title, null);
    }

    public CheckBox getCheckboxPreferenceView(final String key, String title, final String[] extras) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(title);
        setViewProperties(checkBox);
        checkBox.setChecked(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putBoolean(key, isChecked).apply();
                if (extras != null)
                    for (String extraKey : extras)
                        editor.putBoolean(extraKey, isChecked).apply();
            }
        });

        return checkBox;
    }

    public void setDrawerWidth(View drawer) {
        if (!(context instanceof Activity)) {
            Log.d("View Factory", "Only set DrawerWidth with Activity context");
            return;
        }

        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) drawer.getLayoutParams();
        params.width = (int) getDrawerWidth();
        drawer.setLayoutParams(params);
    }

    public float getDrawerWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float width = metrics.widthPixels;
        int actionBarHeight = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        float drawerWidth = width - actionBarHeight;
        float maxDrawerWidth = context.getResources().getDimension(R.dimen.maxDrawerWidth);
        if (drawerWidth > maxDrawerWidth)
            drawerWidth = maxDrawerWidth;
        return drawerWidth;
    }

    public void showDisplayOptions(final HostActivity callback) {
        final SettingsUtil settingsHelper = new SettingsUtil(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(getDisplayOptionsView());
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                callback.resetColors();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public View getDisplayOptionsView() {
        LinearLayout mainLL = new LinearLayout(context);
        mainLL.setOrientation(LinearLayout.VERTICAL);
        int padding = (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin);
        mainLL.setPadding(padding, padding / 2, padding, padding / 2);


        List<String> fontList = new ArrayList<>();
        Collections.addAll(fontList, context.getResources().getStringArray(R.array.fontValues));
        mainLL.addView(getSpinnerOption(context.getString(R.string.typefaceKey), fontList, "Typeface"));


        //add theme spinner
        List<String> themeList = new ArrayList<>();
        for (Style theme : Style.values())
            themeList.add(theme.getThemeName());
        mainLL.addView(getSpinnerOption(Style.key, themeList, "Style"));

        //add line spacing options
        List<String> lineSpacingOptionsList = new ArrayList<>();
        for (LineSpacingOption option : LineSpacingOption.values())
            lineSpacingOptionsList.add(option.getTitle());
        mainLL.addView(getSpinnerOption(LineSpacingOption.key, lineSpacingOptionsList, "Line Spacing"));

        //add font size
        mainLL.addView(getEditTextOption(context.getString(R.string.sizeKey), "30", InputType.TYPE_CLASS_NUMBER, "Text Size"));
        return mainLL;
    }


    public void setViewProperties(TextView view) {
        view.setTextSize(18);
        view.setGravity(Gravity.CENTER_VERTICAL);
        view.setMinHeight((int) context.getResources().getDimension(R.dimen.row_height));
    }

    public TextView getTextView() {
        TextView tv = (TextView) View.inflate(context, R.layout.text_component_simple, null);
        tv.setTypeface(getTypeface());
        tv.setLineSpacing(1f, getLineSpacing());
        tv.setTextSize(getTextSize());
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.centeredKey), false))
            tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getTextColor());
        return tv;
    }



    public void showEnglishOptions() {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        List<String> englishOptionsList = new ArrayList<>();
        for (EnglishOptions option : EnglishOptions.values())
            englishOptionsList.add(option.getTitle());
        ll.addView(getSpinnerOption(EnglishOptions.key, englishOptionsList, "English Options"));
//        ll.addView(getCheckboxPreferenceView(context.getString(R.string.instructionsKey), "Show Instructions"));
        int padding = (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin);
        ll.setPadding(padding, padding, padding, padding);
        new AlertDialog.Builder(context)
                .setPositiveButton("Done", null)
                .setView(ll)
                .create()
                .show();

    }

    public float getLineSpacing() {
        String setting = PreferenceManager.getDefaultSharedPreferences(context).getString(LineSpacingOption.key, LineSpacingOption.SINGLE.getTitle());
        for (LineSpacingOption option : LineSpacingOption.values())
            if (option.getTitle().equals(setting))
                return option.getValue();
        return LineSpacingOption.SINGLE.getValue();
    }

    public enum LineSpacingOption {
        SINGLE, ONE_TWENTY, ONE_FOURTY_FIVE;
        public LineSpacingOption value = this;
        public static final String key = "lineSpaceOption";

        public String getTitle() {
            switch (value) {

                case SINGLE:
                    return "Single";
                case ONE_TWENTY:
                    return "120%";
                case ONE_FOURTY_FIVE:
                    return "145%";
            }
            return "Single";
        }

        public float getValue() {
            switch (value) {

                case SINGLE:
                    return 1f;
                case ONE_TWENTY:
                    return 1.2f;
                case ONE_FOURTY_FIVE:
                    return 1.45f;
            }
            return 1f;
        }
    }
}
