package vn.phamthang.themovies.CustomToast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import vn.phamthang.themovies.databinding.LayoutFailToastBinding;

public class FailToast {
    private static LayoutFailToastBinding binding;
    private static Toast toast;

    public FailToast(Context mContext, String content) {
        binding = LayoutFailToastBinding.inflate(LayoutInflater.from(mContext));

        toast = new Toast(mContext);
        toast.setView(binding.getRoot());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);

//        binding.layoutFailToast.setTranslationX(-2000);
//        binding.backgroundToast.setTranslationX(-2000);

        binding.layoutFailToast.setTranslationZ(-2000);
        binding.backgroundToast.setTranslationZ(-2000);

        binding.txtContentMessage.setText(content);
    }

    public static void showToast() {
        toast.show();
        binding.layoutFailToast.animate().translationX(0).setDuration(1000).setStartDelay(0);
        binding.backgroundToast.animate().translationX(0).setDuration(800).setStartDelay(2500);
    }
}
