package vn.phamthang.themovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.ActivityLoginBinding;
import vn.phamthang.themovies.ultis.Authentication;
import vn.phamthang.themovies.ultis.KeyBoardUtils;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        initAccount();
        initView();
    }

    private void initView() {
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        SignUpActivity.class));
                Animatoo.INSTANCE.animateSlideLeft(LoginActivity.this);
            }
        });
        binding.imgLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            KeyBoardUtils keyBoardUtils = new KeyBoardUtils(getBaseContext());

            if (email.isEmpty()) {
                binding.edtEmail.setError("Không bỏ trống");
            } else {
                if (password.isEmpty()) {
                    binding.edtPassword.setError("Không bỏ trống");
                } else {
                    if (password.length() < 6) {
                        binding.edtPassword.setError("Mật khẩu dài hơn 6 kí tự ");
                    } else {
                        Authentication.onSignIn(email, password, this);
                    }
                }
            }
            keyBoardUtils.hideKeyboard(v);
        });
    }
    private void initAccount(){
      binding.edtEmail.setText("t1@gmail.com");
       binding.edtPassword.setText("123456");
    }

}