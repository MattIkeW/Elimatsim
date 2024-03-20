package com.example.elimatsim;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;

import java.util.ArrayList;


public class AmplifyLogin extends Application {
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Elimatsim", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(), "mattikewax@gmail.com@email.com"));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), "+17039696283"));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.name(), "Matthew Wakhweya"));

        // This method creates and sends the sign up fields to AppSync then DynamoDB and SNS.
        Amplify.Auth.signUp(
                "username",
                "Password123",
                AuthSignUpOptions.builder().userAttributes(attributes).build(),
                result -> Log.i("AuthQuickstart", result.toString()),
                error -> Log.e("AuthQuickstart", error.toString())
        );

        Amplify.Auth.confirmSignUp(
                "username",
                "the code you received via email",
                result -> Log.i("AuthQuickstart",
                        result.isSignUpComplete()
                                ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );

        Amplify.Auth.signIn(
                "username",
                "password",
                result -> Log.i("AuthQuickstart", result.isSignedIn() ? "Sign in succeeded" :
                        "Sign in not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }
}
