package vn.phamthang.themovies.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.ActivityLoginBinding;
import vn.phamthang.themovies.databinding.ActivitySignUpBinding;
import vn.phamthang.themovies.ultis.Authentication;
import vn.phamthang.themovies.ultis.KeyBoardUtils;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        initView();
    }

    private void initView() {
        binding.tvLogin.setOnClickListener(v -> {
            finish();
            Animatoo.INSTANCE.animateSlideRight(this);
        });
        binding.imgSignup.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            String repeatPassword = binding.edtReapectPassword.getText().toString().trim();
            KeyBoardUtils keyBoardUtils = new KeyBoardUtils(getBaseContext());

            if(email.isEmpty()){
                binding.edtEmail.setError("Không bỏ trống");
            }else{
                if(password.isEmpty()){
                    binding.edtPassword.setError("Không bỏ trống");
                }else{
                    if(password.length() < 6){
                        binding.edtPassword.setError("Mật khẩu dài hơn 6 kí tự ");
                    }else{
                        if(repeatPassword.equals(password)){
                            Authentication.onSignUp(email,password,this);
                        }else{
                            binding.edtReapectPassword.setError("Không trùng mật khẩu ");

                        }
                    }
                }
            }
            keyBoardUtils.hideKeyboard(v);


        });
    }
}