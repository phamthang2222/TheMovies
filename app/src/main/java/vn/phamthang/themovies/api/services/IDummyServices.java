package vn.phamthang.themovies.api.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.ultis.Constant;

public interface IDummyServices {
    @GET(Constant.GET_TOP_RATE_MOVIE +Constant.API_KEY)
    Call<BestMovieRespone> getTopRateMovie();
    @GET(Constant.GET_NOW_PLAYING_MOVIE+Constant.API_KEY)
    Call<BestMovieRespone> getNowPlayingMovie();
    @GET(Constant.GET_UP_COMING_MOVIE+Constant.API_KEY)
    Call<BestMovieRespone> getUpComingMovie();
    @GET(Constant.GET_POPULAR_MOVIE+Constant.API_KEY)
    Call<BestMovieRespone> getPopularMovie();
    @GET(Constant.GET_DISCOVER_MOVIE+Constant.API_KEY)
    Call<BestMovieRespone> getDiscoverMovie();
    @GET(Constant.GET_DETAIL_MOVIE+"{id}"+Constant.API_KEY)
    Call<Movie> getDetailMovie(@Path("id") int idMovie);
}
