package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.SignUp.ISignUpPresenter;
import vn.phamthang.themovies.Interface.SignUp.ISignUpView;
import vn.phamthang.themovies.interactors.FireBase.SignUpInteractor;

public class SignUpPresenter implements ISignUpPresenter {

    private SignUpInteractor signUpInteractor;
    private ISignUpView iSignUpView;

    public SignUpPresenter(ISignUpView iSignUpView) {
        this.iSignUpView = iSignUpView;
        signUpInteractor = new SignUpInteractor(this);
    }

    public void signUp(String email, String password, String username) {
        signUpInteractor.signUp(email, password, username);
    }

    @Override
    public void signUpSuccess() {
        iSignUpView.signUpSuccess();
    }

    @Override
    public void signUpError(String message) {
        iSignUpView.signUpError(message);
    }
}
