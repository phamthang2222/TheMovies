package vn.phamthang.themovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.phamthang.themovies.ultis.Constant;

public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance() {
        if(instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }


}
