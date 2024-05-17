package vn.phamthang.themovies.ultis;

import vn.phamthang.themovies.objects.Result;

public class EventBus {
    public  Result result;
    public String textMessage;

    public EventBus(Result result) {

        this.result = result;
    }

    public EventBus(String textMessage) {
        this.textMessage = textMessage;
    }
}
