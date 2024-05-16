package vn.phamthang.themovies.api;

import vn.phamthang.themovies.api.services.IDummyServices;

public class ApiUtils {
    public static IDummyServices getDummyServices() {
        return RetrofitClient.getInstance().create(IDummyServices.class);
    }
}
