package com.pawansinghchouhan05.callcustomizer.core.database;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * to get CBLite database instance
     *
     * @param dbName
     * @return
     * @throws CouchbaseLiteException
     */
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

    /**
     * to get CBLite database manager instance
     *
     * @param context
     * @return
     * @throws IOException
     */
    public static Manager getManagerInstance(Context context) throws IOException {
        if (manager == null) {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

    /**
     * to create custom number document
     *
     * @param customNumber
     */
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

    /**
     * to get map from CBLite database
     *
     * @return
     */
    public static Map<String,Object> getDocument() {
        Document retrievedDocument = null;
        try {
            retrievedDocument = getDatabaseInstance(DB_CUSTOM_NUMBER).getDocument(DB_CUSTOM_NUMBER_DOC_KEY);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map =  retrievedDocument.getProperties();
        Log.e("Document",map.toString());
        return map;
    }

    /**
     * to update CBLite document
     *
     * @param customNumber
     */
    public static void updateDocument(CustomNumber customNumber) {
        try {
            Document document = getDatabaseInstance(DB_CUSTOM_NUMBER).getDocument(DB_CUSTOM_NUMBER_DOC_KEY);
            Map<String, Object> map = new HashMap<String, Object>();
            map.putAll(document.getProperties());
            map.put(String.valueOf(new Date().getTime()),new Gson().toJson(customNumber));
            document.putProperties(map);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    /**
     * to check number is already exist or not in CBLite database
     *
     * @param customNumber
     * @return
     */
    public static boolean isCustomNumberExist(CustomNumber customNumber) {
        boolean flag = false;
        Gson gson = new Gson();
        Map<String, Object> map = getDocument();
        for (String key: map.keySet()) {
            try {
                CustomNumber number = gson.fromJson(map.get(key).toString(), CustomNumber.class);
                if (number != null && number.equals(customNumber)) {
                    return flag = true;
                }
            } catch (Exception e) {
            }

        }
        return false;
    }

    /**
     * to delete CBLite database
     *
     */
    public static void deleteDatabase() {
        try {
            if(!Utils.readPreferences(CallCustomizerApplication.getContext(), Constant.CUSTOM_NUMBER_DOC_EXIST, "").equals("")) {
                getDatabaseInstance(DB_CUSTOM_NUMBER).getDocument(DB_CUSTOM_NUMBER_DOC_KEY).delete();
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}