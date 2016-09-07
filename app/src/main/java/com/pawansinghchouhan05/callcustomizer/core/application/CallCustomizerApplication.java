package com.pawansinghchouhan05.callcustomizer.core.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.core.database.CouchBaseDB;
import com.pawansinghchouhan05.callcustomizer.core.receiver.AlarmReceiver;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumberList;
import com.pawansinghchouhan05.callcustomizer.home.models.Email;
import com.pawansinghchouhan05.callcustomizer.home.services.UserLoggedInService;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;

import org.androidannotations.annotations.EApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fitterfox-Pawan on 6/21/2016.
 */
@EApplication
public class CallCustomizerApplication extends Application {

    public static DatabaseReference databaseReference;
    public static Retrofit retrofit;
    private UserLoggedInService userLoggedInService;
    public static List<CustomNumber> numbers = new ArrayList<>();
    private static Context context;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
        try {
            CouchBaseDB.getManagerInstance(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userLoggedInService = CallCustomizerApplication.retrofit.create(UserLoggedInService.class);
        if(!Utils.readPreferences(getApplicationContext(), Constant.LOGIN_STATUS, "").equals("") && !Utils.readPreferences(getApplicationContext(), Constant.CUSTOM_NUMBER_DOC_EXIST, "").equals(""))
            numbers = getCustomNumberFromDB();


    }

    /**
     * to initialize retrofit and other objects
     *
     */
    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Utils.retriveCustomNumberListToFCMDatabase();

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        // TODO set alarm in optimise way
        //alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //int interval = 10000; // 10 seconds

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        //Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    /**
     * to get custom number list from server
     *
     * @return
     */
    public List<CustomNumber> getCustomNumber() {
        UserLoggedIn userLoggedIn = new Gson().fromJson(Utils.readPreferences(getApplicationContext(), Constant.LOGGED_IN_USER, ""), UserLoggedIn.class);

        Observable<List<CustomNumber>> stringObservable = userLoggedInService.getNumber(new Email(userLoggedIn.getEmail()));
        try {
            stringObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<CustomNumber>>() {
                @Override
                public void onCompleted() {
                    Log.e("Complete","C");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Error",e.getMessage());
                }

                @Override
                public void onNext(List<CustomNumber> customNumbers) {
                    Log.e("list", customNumbers.toString());
                    numbers = customNumbers;
                }

            });
        } catch (Exception e) { e.printStackTrace();}

        return numbers;
    }

    /**
     * to get custom number list from Couchbase database
     *
     * @return
     */
    private List<CustomNumber> getCustomNumberFromDB() {
        Map<String, Object> map = CouchBaseDB.getDocument();
        Gson gson = new Gson();
        List<CustomNumber> numberList= new ArrayList<>();
        for (String key: map.keySet()) {
            try {
                numberList.add(gson.fromJson(map.get(key).toString(), CustomNumber.class));
            } catch (Exception e) { Log.e("Exp", e.getMessage()); }

        }
        return numberList;
    }

    /**
     * to get context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
