package vn.phamthang.themovies.api.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Cast.CastingRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Review.ReviewResponse;
import vn.phamthang.themovies.objects.Video.ResultVideoMovie;
import vn.phamthang.themovies.objects.request.FavoriteMovieRequest;
import vn.phamthang.themovies.ultis.Constant;

public interface IDummyServices {
    //GET
    @GET(Constant.GET_TOP_RATE_MOVIE + Constant.API_KEY)
    Call<BestMovieRespone> getTopRateMovie();

    @GET(Constant.GET_NOW_PLAYING_MOVIE + Constant.API_KEY)
    Call<BestMovieRespone> getNowPlayingMovie();

    @GET(Constant.GET_UP_COMING_MOVIE + Constant.API_KEY)
    Call<BestMovieRespone> getUpComingMovie();

    @GET(Constant.GET_POPULAR_MOVIE + Constant.API_KEY)
    Call<BestMovieRespone> getPopularMovie();

    @GET(Constant.GET_DISCOVER_MOVIE + Constant.API_KEY)
    Call<BestMovieRespone> getDiscoverMovie();

    @GET(Constant.GET_DETAIL_MOVIE + "{id}" + Constant.API_KEY)
    Call<Movie> getDetailMovie(@Path("id") int idMovie);

    @GET(Constant.GET_SEARCH_MOVIE + Constant.API_KEY)
    Call<BestMovieRespone> getSearchMovie(@Query("query") String query);

    @GET(Constant.GET_FAV_MOVIE + Constant.API_KEY)
    Call<BestMovieRespone> getFavMovie();

    @GET(Constant.GET_VIDEO_MOVIE + "{idMovie}" + "/videos" + Constant.API_KEY)
    Call<ResultVideoMovie> getVideoMovie(@Path("idMovie") int idMovie);

    @GET(Constant.GET_REVIEW_MOVIE + "{idMovie}" + "/reviews?language=en-US&page=1" + Constant.API_KEY)
    Call<ReviewResponse> getReviewMovie(@Path("idMovie") int idMovie);

    @GET(Constant.GET_CAST_MOVIE+ "{idMovie}" + "/casts?language=en-US&page=1" + Constant.API_KEY)
    Call<CastingRespone> getCastMovie(@Path("idMovie") int idMovie);

    //----------------------------------------------------------------------
    //POST
    @POST(Constant.POST_FAV_MOVIE + Constant.API_KEY)
    Call<ResponseBody> addToFavorite(@Body FavoriteMovieRequest favoriteMovieRequest);
}
