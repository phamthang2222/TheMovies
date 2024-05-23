package vn.phamthang.themovies.ultis;


import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardUtils {
    private Context context;

    public KeyBoardUtils(Context context) {
        this.context = context;
    }

    // Hàm ẩn bàn phím
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
