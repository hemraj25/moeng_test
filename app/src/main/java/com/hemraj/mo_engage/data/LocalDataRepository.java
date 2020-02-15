package com.hemraj.mo_engage.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hemraj.mo_engage.domain.NewsFeed;

import java.util.ArrayList;
import java.util.List;

public class LocalDataRepository {

    private final String KEY = "local_news_list";
    private final String PREF_NAME = "localPref";

    private Gson gson;
    private SharedPreferences sharedPrefernces;

    public LocalDataRepository(Context context, Gson gson) {
        this.gson = gson;
        this.sharedPrefernces = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    List<NewsFeed> getNewsFeedFromLocal() {
        String value = sharedPrefernces.getString(KEY, "");
        try {
            if (!TextUtils.isEmpty(value)) {
                return gson.fromJson(value, TypeToken.getParameterized(ArrayList.class, NewsFeed.class).getType());
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            Log.d("Local Data Repository", e.getStackTrace().toString());
            return new ArrayList<>();
        }
    }

    void addNewsFeedToLocal(List<NewsFeed> newsFeedList) {
        sharedPrefernces.edit().putString(KEY ,gson.toJson(newsFeedList)).apply();
    }
}
