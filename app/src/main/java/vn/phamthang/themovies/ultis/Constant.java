package vn.phamthang.themovies.ultis;

public class Constant {

    public static final String  BASE_URL = "https://api.themoviedb.org";
    private static final String  BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    private static final String  BASE_URL_IMAGE2 = "https://image.tmdb.org/t/p/original";
    public static final String API_KEY = "?api_key=d63cb64edcf292fc628aa4c72c2cf6ab";

    //-------------------------------------------------------------------------------------
    //GET
    public static final String  GET_DISCOVER_MOVIE = "/3/discover/movie";
    public static final String GET_TOP_RATE_MOVIE = "/3/movie/top_rated";
    public static final String  GET_NOW_PLAYING_MOVIE = "/3/movie/now_playing";
    public static final String  GET_UP_COMING_MOVIE = "/3/movie/upcoming";
    public static final String  GET_POPULAR_MOVIE = "/3/movie/popular";
    public static final String  GET_DETAIL_MOVIE = "/3/movie/";
    public static final String  GET_SEARCH_MOVIE = "/3/search/movie";


    public static  String convertLinkImage(String linkImage ){
        return BASE_URL_IMAGE+linkImage;
    }
    public static  String convertLinkImage2(String linkImage ){
        return BASE_URL_IMAGE2+linkImage;
    }

}
