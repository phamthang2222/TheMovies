package vn.phamthang.themovies.ultis;

import vn.phamthang.themovies.objects.Result;

public class MessageEvent {
    public  Result result;
    public String textMessage;

    public MessageEvent(Result result) {

        this.result = result;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public MessageEvent(String textMessage) {
        this.textMessage = textMessage;

    }
}
