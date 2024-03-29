package com.example.elimatsim.ui.login;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.amplifyframework.core.Amplify;
import com.example.elimatsim.R;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> signInResultLiveData = new MutableLiveData<>();

    public boolean login(String uName, String pWord){
        Amplify.Auth.signIn(uName, pWord,
                result -> {
                    Log.i("AuthQuickstart", result.isSignedIn() ? "Sign in succeeded" : "Sign in not complete");
                    if (result.isSignedIn()) {
                        signInResultLiveData.postValue(true); // Sign-in successful
                    } else {
                        signInResultLiveData.postValue(false); // Sign-in failed
                    }
            },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    signInResultLiveData.postValue(false); // Sign-in failed
                }
        );
        return Boolean.TRUE.equals(signInResultLiveData.getValue());
    }
}
