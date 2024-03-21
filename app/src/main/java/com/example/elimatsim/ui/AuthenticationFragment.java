package com.example.elimatsim.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.elimatsim.R;

import java.util.ArrayList;

public class AuthenticationFragment extends Fragment {
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment. This makes it LOGIN_MAIN XML
        return inflater.inflate(R.layout.login_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize NavController
        navController = Navigation.findNavController(view);

        // Find a way to fetch user input.
        // Validate user input.

        // Set up navigation between login and sign-up screens
        view.findViewById(R.id.loginButton).setOnClickListener(v -> {
            // Navigate to main screen after login
            navController.navigate(R.id.mobile_navigation);

        });

        view.findViewById(R.id.signupButton).setOnClickListener(v -> {
            // Navigate to main screen after sign-up
            navController.navigate(R.id.mobile_navigation);

        });
    }

    public class LoginFragment extends Fragment {

        // Method to handle login authentication
        private void loginUser(String uName, String passWrd) {
            // Implement authentication logic here
            // For example, you can use Firebase Authentication

            Amplify.Auth.signIn(
                    "username",
                    "password",
                    result -> Log.i("AuthQuickstart", result.isSignedIn() ? "Sign in succeeded" :
                            "Sign in not complete"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );

            // If authentication is successful, navigate to the main screen.
            // This SHOULD change the graph to the main nav graph.
            navController.navigate(R.id.mobile_navigation);
        }
    }

    public class SignupFragment extends Fragment {
        // Method to handle sign-up authentication
        private void signUpUser(String uName, String password, String email, String phone,
                                String name) {
            // Implement sign-up logic here
            // For example, you can use Firebase Authentication

            ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(), email));
            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), phone));
            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.name(), name));

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
            // If sign-up is successful, navigate to the main screen
            navController.navigate(R.id.mobile_navigation);
        }
    }


}