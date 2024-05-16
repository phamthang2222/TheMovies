package vn.phamthang.themovies.ultis;

import vn.phamthang.themovies.objects.Result;

public class EventBus {
    public final Result result;

    public EventBus(Result result) {
        this.result = result;
    }
}
