package holybible.religious.christianity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import holybible.religious.christianity.Bible.TitleModel;


public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String db_name) {
        super(context, db_name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    // For Get jokes in list
    public ArrayList<HashMap<String, String>> gethistorydata() {

        ArrayList<HashMap<String, String>> jokes_content = new ArrayList<HashMap<String, String>>();

        // Select All Query
        String selectQuery = "SELECT * FROM history";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("heading", cursor.getString(0));
                hm.put("description", cursor.getString(1));
                jokes_content.add(hm);
            } while (cursor.moveToNext());
        }
        return jokes_content;

    }

    public ArrayList<HashMap<String, String>> getversesdata() {

        ArrayList<HashMap<String, String>> jokes_content = new ArrayList<HashMap<String, String>>();

        // Select All Query
        String selectQuery = "SELECT distinct topic FROM verses";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("heading", cursor.getString(0));
                jokes_content.add(hm);
            } while (cursor.moveToNext());
        }
        return jokes_content;

    }

    public ArrayList<HashMap<String, String>> getversesdetails(String s) {

        ArrayList<HashMap<String, String>> jokes_content = new ArrayList<HashMap<String, String>>();

        // Select All Query
        String selectQuery = "SELECT title,description FROM verses where topic='" + s + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("title", cursor.getString(0));
                hm.put("description", cursor.getString(1));
                jokes_content.add(hm);
            } while (cursor.moveToNext());
        }
        return jokes_content;

    }

    public ArrayList<HashMap<String, String>> getprayerdata() {

        ArrayList<HashMap<String, String>> jokes_content = new ArrayList<HashMap<String, String>>();

        // Select All Query
        String selectQuery = "SELECT distinct prayertitle FROM prayer";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("heading", cursor.getString(0));
                jokes_content.add(hm);
            } while (cursor.moveToNext());
        }
        return jokes_content;

    }

    public ArrayList<HashMap<String, String>> getprayerdetails(String s) {

        ArrayList<HashMap<String, String>> jokes_content = new ArrayList<HashMap<String, String>>();

        // Select All Query
        String selectQuery = "SELECT prayertopic,description FROM prayer where prayertitle='" + s + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("title", cursor.getString(0));
                hm.put("description", cursor.getString(1));
                jokes_content.add(hm);
            } while (cursor.moveToNext());
        }
        return jokes_content;
    }

    public List<TitleModel> getBibleChaptersTitles() {

        List<TitleModel> titlelList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM bible_chapter_titles";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TitleModel titleModel = new TitleModel();
                titleModel.setId(cursor.getInt(0));
                titleModel.setName(cursor.getString(1));
                titleModel.setChapters(cursor.getInt(2));
                titlelList.add(titleModel);
            } while (cursor.moveToNext());
        }
        return titlelList;
    }


    public List<String> getBibleChapterDescription(String title) {

        List<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM bible_chapters where chaptertitle = '" + title + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //  DescriptionModel descriptionModel = new DescriptionModel();
                //   descriptionModel.setId(cursor.getInt(0));
                //   descriptionModel.setChaptertitle(cursor.getString(1));
                //   descriptionModel.setChapterdesc(cursor.getString(2));
                //   descriptionModel.setBookmark(cursor.getInt(3));
                list.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return list;
    }
}