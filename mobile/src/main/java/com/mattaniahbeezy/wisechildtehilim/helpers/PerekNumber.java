package com.mattaniahbeezy.wisechildtehilim.helpers;

import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;

/**
 * Created by Mattaniah on 10/22/2015.
 */
public class PerekNumber {

    final static int[] books = {0, 41, 72, 89, 106};
    final static int[] perWeek = {0, 29, 50, 72, 89, 106, 119};
    final static int[] perMonth = {0, 9, 17, 22, 28, 34, 38, 43, 48, 54, 59, 65, 68, 71, 76, 78, 82, 87, 89, 96, 103, 105, 107, 112, 118, 119, 134, 139, 144};

    final static String[] daysOfWeek = {"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"};
    final static String[] daysOfWeekEnglish={"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Shabbos"};

    HebrewDateFormatter hFat = new HebrewDateFormatter();
    boolean useHebrew=true;

    public PerekNumber() {
        hFat.setUseGershGershayim(false);
    }

    public PerekNumber(boolean useHebrew) {
        hFat.setUseGershGershayim(false);
        this.useHebrew = useHebrew;
    }

    public void setUseHebrew(boolean useHebrew) {
        this.useHebrew = useHebrew;
    }

    private int getNumberInGroup(int[] group, int chapter) {
        for (int i = group.length - 1; i > 0; i--)
            if (chapter >= group[i])
                return i;
        return 0;
    }

    private boolean isFirstInSeries(int[] group, int chapterNumber) {
        for (int i = group.length - 1; i >= 0; i--)
            if (group[i] == chapterNumber)
                return true;
        return false;
    }

    public boolean isFirstInBook(int chapter) {
        return isFirstInSeries(books, chapter);
    }

    public boolean isFirstInWeek(int chapter) {
        return isFirstInSeries(perWeek, chapter);
    }

    public boolean isFirstInMonth(int chapter) {
        return isFirstInSeries(perMonth, chapter);
    }

    public boolean isFirstInAny(int chapter) {
        return isFirstInBook(chapter) || isFirstInWeek(chapter) || isFirstInMonth(chapter);
    }

    /**
     * Returns formatted name in Hebrew for chapter number
     *
     * @param chapterNumber based 0 (perek א =0)
     */
    public String getChapterNameHebrew(int chapterNumber) {
        if (chapterNumber<0)
            chapterNumber=0;
        chapterNumber++;
        return useHebrew? "פרק " + hFat.formatHebrewNumber(chapterNumber):"Chapter "+chapterNumber;
    }

    public String getBookForChapter(int chapter) {
        int number = getNumberInGroup(books, chapter) + 1;
        return useHebrew? "ספר " + hFat.formatHebrewNumber(number):"Book "+number;
    }

    public String getDayOfWeekForChapter(int chapter) {
        int number = getNumberInGroup(perWeek, chapter);
        return useHebrew? "יום " + daysOfWeek[number]:daysOfWeekEnglish[number] ;
    }

    public String getDayOfMonthForChapter(int chapter) {
        int number = getNumberInGroup(perMonth, chapter) + 1;
        return useHebrew? "יום " + hFat.formatHebrewNumber(number):"Day "+number;
    }

    public String getFirstTitles(int chapterNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isFirstInBook(chapterNumber)) {
            stringBuilder.append(getBookForChapter(chapterNumber));
        }
        if (isFirstInWeek(chapterNumber)) {
            if (stringBuilder.length() > 0)
                stringBuilder.append(" | ");
            stringBuilder.append(getDayOfWeekForChapter(chapterNumber));
        }
        if (isFirstInMonth(chapterNumber)) {
            if (stringBuilder.length() > 0)
                stringBuilder.append(" | ");
            stringBuilder.append(getDayOfMonthForChapter(chapterNumber));
        }
        return stringBuilder.toString();
    }

    public String getGroupTitles(int chapterNumber) {
        return getBookForChapter(chapterNumber) + " | " + getDayOfWeekForChapter(chapterNumber) + " | " + getDayOfMonthForChapter(chapterNumber);
    }
}
