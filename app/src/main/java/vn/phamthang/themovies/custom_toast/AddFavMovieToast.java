package vn.phamthang.themovies.custom_toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import vn.phamthang.themovies.databinding.LayoutAddFavToastBinding;
import vn.phamthang.themovies.databinding.LayoutSuccessfullToastBinding;

public class AddFavMovieToast {
    private static LayoutAddFavToastBinding binding;
    private static Toast toast;
    public AddFavMovieToast(Context mContext, String content) {
        binding = LayoutAddFavToastBinding.inflate(LayoutInflater.from(mContext));

        toast = new Toast(mContext);
        toast.setView(binding.getRoot());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
//
//        binding.layoutSuccessfulToast.setTranslationX(-2000);
//        binding.backgroundToast.setTranslationX(-2000);
        binding.layoutSuccessfulToast.setTranslationZ(-2000);
        binding.backgroundToast.setTranslationZ(-2000);
        binding.txtContentMessage.setText(content);
    }
    public static void showToast() {
        toast.show();
        binding.layoutSuccessfulToast.animate().translationX(0).setDuration(1000).setStartDelay(0);
        binding.backgroundToast.animate().translationX(0).setDuration(800).setStartDelay(2500);
    }
}
