package vn.phamthang.themovies.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import vn.phamthang.themovies.Interface.SignUp.ISignUpView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.custom_toast.FailToast;
import vn.phamthang.themovies.custom_toast.SuccessfulToast;
import vn.phamthang.themovies.databinding.ActivitySignUpBinding;
import vn.phamthang.themovies.presenter.SignUpPresenter;
import vn.phamthang.themovies.ultis.KeyBoardUtils;

public class SignUpActivity extends AppCompatActivity implements ISignUpView {
    ActivitySignUpBinding binding;
    private SignUpPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));


        mPresenter = new SignUpPresenter(this);
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
            String username = binding.edtUsername.getText().toString().trim();
            String repeatPassword = binding.edtReapectPassword.getText().toString().trim();


            KeyBoardUtils keyBoardUtils = new KeyBoardUtils(getBaseContext());


            if (email.isEmpty()) {
                binding.edtEmail.setError(getString(R.string.error_empty_field));
            } else if (username.isEmpty()) {
                binding.edtUsername.setError(getString(R.string.error_empty_field));
            } else if (password.isEmpty()) {
                binding.edtPassword.setError(getString(R.string.error_empty_field));
            } else if (password.length() < 6) {
                binding.edtPassword.setError(getString(R.string.error_password_length));
            } else if (!repeatPassword.equals(password)) {
                binding.edtReapectPassword.setError(getString(R.string.error_password_mismatch));
            } else {
//                FirebaseUser firebaseUser = Authentication.firebaseAuth.getCurrentUser();
//                String userId = firebaseUser.getUid();
//                User user = new User(userId, email, username, password, new ArrayList<>());
//                Authentication.onSignUp(email, password, this,username);
                mPresenter.signUp(email, password, username);

            }
            keyBoardUtils.hideKeyboard(v);
        });
    }

    @Override
    public void signUpSuccess() {
        new SuccessfulToast(SignUpActivity.this, "Đăng kí thành công").showToast();
    }

    @Override
    public void signUpError(String message) {
        new FailToast(SignUpActivity.this, "Email đã đăng ký!").showToast();
    }
}