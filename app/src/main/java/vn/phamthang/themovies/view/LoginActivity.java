package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import vn.phamthang.themovies.Interface.SignIn.ISignInView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.custom_toast.FailToast;
import vn.phamthang.themovies.custom_toast.SuccessfulToast;
import vn.phamthang.themovies.databinding.ActivityLoginBinding;
import vn.phamthang.themovies.presenter.SignInPresenter;
import vn.phamthang.themovies.ultis.KeyBoardUtils;

public class LoginActivity extends AppCompatActivity implements ISignInView {
    ActivityLoginBinding binding;
    private SignInPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        mPresenter = new SignInPresenter( this);
        initAccount();
        initView();
    }

    private void initView() {
        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,
                    SignUpActivity.class));
            Animatoo.INSTANCE.animateSlideLeft(LoginActivity.this);
        });
        binding.imgLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            KeyBoardUtils keyBoardUtils = new KeyBoardUtils(getBaseContext());

            if (email.isEmpty()) {
                binding.edtEmail.setError("Không bỏ trống");
                return;
            }
            if (password.isEmpty()) {
                binding.edtPassword.setError("Không bỏ trống");
                return;
            }
            if (password.length() < 6) {
                binding.edtPassword.setError("Mật khẩu dài hơn 6 kí tự");
                return;
            }
            mPresenter.signIn(email,password);
            keyBoardUtils.hideKeyboard(v);
        });
    }
    private void initAccount(){
      binding.edtEmail.setText("t1@gmail.com");
       binding.edtPassword.setText("123456");
    }

    @Override
    public void signInSuccess() {
        new SuccessfulToast(this,"Đăng nhập thành công").showToast();
        Intent intent = new Intent(this, FlashLoginSuccessActivity.class);
        startActivity(intent);
        finish();
        Animatoo.INSTANCE.animateSlideUp(this);
    }

    @Override
    public void signInError(String message) {
        new FailToast(this,"Sai tên đăng nhập/mật khẩu").showToast();

    }
}