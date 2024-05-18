package vn.phamthang.themovies.ultis;

import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;

public class MessageEvent {
    public  Result result;
    public Movie movie;
    public String textMessage;

    public MessageEvent(Result result) {
        this.result = result;
    }

    public MessageEvent(Movie movie) {
        this.movie = movie;
    }
    public MessageEvent(String textMessage) {
        this.textMessage = textMessage;

    }
    public Result getResult() {
        return result;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getTextMessage() {
        return textMessage;
    }


}
