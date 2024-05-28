package vn.phamthang.themovies.presenter;

import vn.phamthang.themovies.Interface.SignIn.ISignInPresenter;
import vn.phamthang.themovies.Interface.SignIn.ISignInView;
import vn.phamthang.themovies.interactors.FireBase.SignInInteractor;

public class SignInPresenter implements ISignInPresenter {
    private SignInInteractor signInInteractor;
    private ISignInView iSignInView;

    public SignInPresenter(ISignInView iSignInView) {
        this.iSignInView = iSignInView;
        signInInteractor = new SignInInteractor(this);
    }

    public void signIn(String email, String password){
        signInInteractor.signIn(email,password);
    }

    @Override
    public void signInSuccess() {
        iSignInView.signInSuccess();
    }

    @Override
    public void signInError(String message) {
        iSignInView.signInError(message);
    }
}
