package vn.phamthang.themovies.objects.request;

public class FavoriteMovieRequest {
    private int media_id;
    private String media_type;
    private boolean favorite;

    public FavoriteMovieRequest() {
    }

    public FavoriteMovieRequest(int media_id, String media_type, boolean favorite) {
        this.media_id = media_id;
        this.media_type = media_type;
        this.favorite = favorite;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String  toString() {
        return "FavoriteMovieRequest{" +
                "media_id=" + media_id +
                ", media_type='" + media_type + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
