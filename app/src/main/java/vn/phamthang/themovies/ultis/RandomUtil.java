package vn.phamthang.themovies.ultis;

import java.util.Random;

public class RandomUtil {
    public int ranDomIdUser() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt((max - min) + 1) + min;
    }
}
