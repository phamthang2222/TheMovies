package vn.phamthang.themovies.ultis;

public class Constant {

    public static final String BASE_URL = "https://api.themoviedb.org";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    private static final String BASE_URL_IMAGE2 = "https://image.tmdb.org/t/p/original";
    private static final String BASE_URL_YOUTUBE = "https://www.youtube.com/watch?v=";
    public static final String API_KEY = "?api_key=d63cb64edcf292fc628aa4c72c2cf6ab";
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNjNjYjY0ZWRjZjI5MmZjNjI4YWE0YzcyYzJjZjZhYiIsInN1YiI6IjY2MmU3MTAzN2Q1ZGI1MDEyYzNlMjg2NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uoHx4I-9vYJx20_9OCRvz5lXDdW1Vh30PGagGHcI6LQ";
    public static final String AUTHORIZATION = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNjNjYjY0ZWRjZjI5MmZjNjI4YWE0YzcyYzJjZjZhYiIsInN1YiI6IjY2MmU3MTAzN2Q1ZGI1MDEyYzNlMjg2NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uoHx4I-9vYJx20_9OCRvz5lXDdW1Vh30PGagGHcI6LQ";

    public static final int ACCOUNT_ID = 21241207;

    //-------------------------------------------------------------------------------------
    //GET
    public static final String GET_DISCOVER_MOVIE = "/3/discover/movie";
    public static final String GET_TOP_RATE_MOVIE = "/3/movie/top_rated";
    public static final String GET_NOW_PLAYING_MOVIE = "/3/movie/now_playing";
    public static final String GET_UP_COMING_MOVIE = "/3/movie/upcoming";
    public static final String GET_POPULAR_MOVIE = "/3/movie/popular";
    public static final String GET_DETAIL_MOVIE = "/3/movie/";
    public static final String GET_SEARCH_MOVIE = "/3/search/movie";
    public static final String GET_VIDEO_MOVIE = "/3/movie/";
    public static final String GET_REVIEW_MOVIE = "/3/movie/";
    public static final String GET_FAV_MOVIE = "/3/account/"+ACCOUNT_ID+"/favorite/movies?language=en-US&page=1&sort_by=created_at.asc";

    //POST
    public static final String POST_FAV_MOVIE = "/3/account/"+ACCOUNT_ID+"/favorite";
    public static String convertLinkImage(String linkImage) {
        return BASE_URL_IMAGE + linkImage;
    }
    public static String convertLinkImage2(String linkImage) {
        return BASE_URL_IMAGE2 + linkImage;
    }
    public static String convertLinkVideo(String key) {
        return BASE_URL_YOUTUBE + key;
    }

}
