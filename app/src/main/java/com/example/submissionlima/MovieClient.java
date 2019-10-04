package com.example.submissionlima;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {
    private static Retrofit retrofit = null;
    public static Retrofit Retrofit(){

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(new OkHttpClient.Builder()
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20,TimeUnit.SECONDS)
                        .readTimeout(20,TimeUnit.SECONDS)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request ori = chain.request();
                                HttpUrl oriUrl = ori.url();
                                HttpUrl newUrl = oriUrl.newBuilder()
                                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                                        .build();
                                Request.Builder requestBuilder = ori.newBuilder().url(newUrl);

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                        .build())
                .addConverterFactory(GsonConverterFactory.create(myGson()))
                .build();
    }

    private static Gson myGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @SuppressLint("SimpleDataFormat")
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            @Override
            public Date deserialize (final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try{
                    return df.parse(json.getAsString());
                } catch (ParseException e){
                    return null;
                }
            }
        }).serializeNulls();
        return gsonBuilder.create();
    }

    public static void LoadImage(Context context, ImageView imageView, String url){
        String posterUrl = url != null ? String.format(BuildConfig.IMGURL, "w154", url) : null;
        Glide.with(context).load(posterUrl).into(imageView);
    }

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
