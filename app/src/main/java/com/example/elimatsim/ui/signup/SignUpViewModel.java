package com.example.elimatsim.ui.signup;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;

public class SignUpViewModel extends ViewModel {
    private final MutableLiveData<Boolean> signInResultLiveData = new MutableLiveData<>();

    /**
     * This function is a holder for the sign in process to Amplify.
     * Consider running this in a Thread. Shouldn't take too long but just in case.
     * It uses the code below in the shared Fragment class.
     * EditText uName = root.findViewById(R.id.username);
     * EditText email = root.findViewById(R.id.email);
     * EditText pWord = root.findViewById(R.id.password);
     * EditText pNum = root.findViewById(R.id.phoneNum);
     * EditText name = root.findViewById(R.id.name);
     *
     * @param uName This is the username.
     * @param pWord This is the password
     * @param email This is the email address
     * @param pN    This is a phone number with format +7038889876
     * @param name  This will be a first and last name field.
     * @return The return type is a boolean on successful sign in.
     */
    public boolean signIn(String uName, String pWord,
                          String email, String pN,
                          String name) {

        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(), email));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), pN));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.name(), name));

        Amplify.Auth.signUp(uName, pWord, AuthSignUpOptions
                        .builder()
                        .userAttributes(attributes)
                        .build(),
                result -> {
                    Log.i("AuthQuickstart", result.toString());
                    if (result.isSignUpComplete()) {
                        signInResultLiveData.postValue(true);
                        Log.i("Amplify", "Sign Up Successful");
                    } else {
                        signInResultLiveData.postValue(false);
                        Log.i("Amplify", "Sign Up Failed");
                    }
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    signInResultLiveData.postValue(false);
                    Log.i("Amplify", "Sign Up Failed. Check Amplify connection");
                }
        );

        Amplify.Auth.confirmSignUp(uName,
                "the code you received via email",
                result -> Log.i("AuthQuickstart",
                        result.isSignUpComplete()
                                ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );

        return Boolean.TRUE.equals(signInResultLiveData.getValue());
    }
}
