package vn.phamthang.themovies.api;

import com.chuckerteam.chucker.api.ChuckerInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.phamthang.themovies.MyApplication;
import vn.phamthang.themovies.ultis.AuthInterceptor;
import vn.phamthang.themovies.ultis.Constant;

public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance() {
        if(instance == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new ChuckerInterceptor(MyApplication.getInstance().getApplicationContext()))
                    .addInterceptor(new AuthInterceptor(Constant.TOKEN))
                    .build();

            instance = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return instance;
    }


}
