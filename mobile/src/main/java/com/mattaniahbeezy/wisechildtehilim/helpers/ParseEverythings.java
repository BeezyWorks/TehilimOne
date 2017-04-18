package com.mattaniahbeezy.wisechildtehilim.helpers;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Mattaniah on 7/19/2015.
 */
public class ParseEverythings {
    Context context;
    JSONArray arochHaShulchan;

    final String aruchHaShulchanKey="ArochHaShulchan";
    final String mishnaBruraKey="MishnaBrurua";
    final String shulchanAruchKey="ShulchanAruch";

    static final String[] sectionTitles = {
            "הנהגת האדם בבוקר",
            "ציצית",
            "תפילין",
            "ברכת השחר ושאר ברכות",
            "קריאת שמע",
            "תפלה",
            "נשיאת כפים",
            "נפילת אפים",
            "קריאת ספר תורה",
            "בית הכנסת",
            "משא ומתן",
            "נטילת ידים",
            "ברכות",
            "שבת",
            "תחומין",
            "עירובי תחומין",
            "ראש חודש",
            "פסח",
            "יום טוב",
            "חול המועד",
            "תשעה באב ושאר תעניות",
            "תענית",
            "ראש השנה",
            "יום הכיפורים",
            "סוכה",
            "לולב",
            "חנוכה",
            "מגילה ופורים"
    };

    final static int[] firstSimanim = {
            1,
            8,
            25,
            46,
            58,
            89,
            128,
            131,
            135,
            150,
            156,
            157,
            231,
            242,
            396,
            408,
            417,
            429,
            495,
            530,
            549,
            562,
            581,
            604,
            625,
            645,
            670,
            686,
            697
    };

    public ParseEverythings(Context context) throws FileNotFoundException, JSONException {
        this.context = context;
        arochHaShulchan = loadJSONFromAsset();

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath());
        dir.mkdirs();


        for (int i = 0; i < arochHaShulchan.length(); i++) {
            JSONArray siman = arochHaShulchan.getJSONArray(i);


        }
    }

    public class HalachaSection {
        int firstSiman;
        List<JSONArray> simanim;

        public HalachaSection(int firstSiman, List<JSONArray> simanim) {
            this.firstSiman = firstSiman;
            this.simanim = simanim;
        }
    }


    private JSONArray loadJSONFromAsset() {
        try {
            InputStream is = context.getAssets().open("aruchhashulchan.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String rawJson = new String(buffer, "UTF-8");
            JSONObject object = new JSONObject(rawJson);
            return object.getJSONArray("text");
        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
