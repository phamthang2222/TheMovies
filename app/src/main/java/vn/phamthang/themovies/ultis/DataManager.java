package vn.phamthang.themovies.ultis;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import vn.phamthang.themovies.objects.request.MovieRequest;

public class DataManager {
    private static final String PREFS_NAME = "ListFavouriteMoviePreferences";
    private static final String KEY_DATA = "FAV_MOVIE";
    public static void saveFavoriteMovie(Context context, ArrayList<MovieRequest> listFav){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(listFav);

        editor.putString(KEY_DATA,json);
        editor.apply();
    }
    public static ArrayList<MovieRequest> loadFavoriteMovie(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_DATA, null);

        if (json == null) {
            return new ArrayList<>(); // Trả về một ArrayList trống nếu không có dữ liệu
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MovieRequest>>() {}.getType();

        return gson.fromJson(json, type); // Chuyển đổi JSON thành ArrayList
    }
    public static void removeFavoriteMovie(Context context,  ArrayList<MovieRequest> listFav) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
        editor.apply(); // Áp dụng thay đổi

        saveFavoriteMovie(context,listFav);
    }
}
