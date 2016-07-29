package com.pawansinghchouhan05.callcustomizer.core.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.core.database.CouchBaseDB;
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


    @Override
    public void onCreate() {
        super.onCreate();
        init();
        try {
            CouchBaseDB.getManagerInstance(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userLoggedInService = CallCustomizerApplication.retrofit.create(UserLoggedInService.class);
        if(!Utils.readPreferences(getApplicationContext(), Constant.LOGIN_STATUS, "").equals(""))
            numbers = getCustomNumber();


    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Response response = chain.proceed(chain.request());
                        Log.e("Retrofit@Response", response.body().string());

                        String token = Utils.readPreferences(getApplicationContext(), "token", "");
                        if (!token.isEmpty())
                            return chain.proceed(original.newBuilder()
                                    .header("X-Auth-Token", token)
                                    .method(original.method(), original.body())
                                    .build());
                        return chain.proceed(original);
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Utils.retriveCustomNumberListToFCMDatabase();
    }

    private List<CustomNumber> getCustomNumber() {
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
}
