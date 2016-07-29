package com.pawansinghchouhan05.callcustomizer.core.database;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pawan on 29/7/16.
 */
public class CouchBaseDB {
    private static Manager manager = null;
    private static Database database = null;
    public static final String DB_CUSTOM_NUMBER = "customnumber";
    public static final String DB_CUSTOM_NUMBER_DOC_KEY = "customnumber";


    public static Database getDatabaseInstance(String dbName) throws CouchbaseLiteException {
        if (dbName.equals(DB_CUSTOM_NUMBER)) {
            if ((database == null) & (manager != null)) {
                return database = manager.getDatabase(dbName);
            } else {
                return database;
            }
        }
        return database;
    }

    public static Manager getManagerInstance(Context context) throws IOException {
        if (manager == null) {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

    public static void createCustomNumberDocument(CustomNumber customNumber) {
        try {
            Document document = getDatabaseInstance(DB_CUSTOM_NUMBER).getDocument(DB_CUSTOM_NUMBER_DOC_KEY);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(String.valueOf(new Date().getTime()),new Gson().toJson(customNumber));
            document.putProperties(map);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,Object> getDocument(Database database, String documentID) {
        Document retrievedDocument = database.getDocument(documentID);
        Map<String, Object> map = new HashMap<String, Object>();
        map =  retrievedDocument.getProperties();
        Log.e("Document",map.toString());
        return map;
    }
}
