package com.pawansinghchouhan05.callcustomizer.core.application;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumberList;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fitterfox-Pawan on 6/21/2016.
 */
public class CallCustomizerApplication extends Application {

    public static DatabaseReference databaseReference;
    public static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
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
                .baseUrl("http://192.168.1.7/php/CrudExample/index.php/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Utils.retriveCustomNumberListToFCMDatabase();
    }
}
