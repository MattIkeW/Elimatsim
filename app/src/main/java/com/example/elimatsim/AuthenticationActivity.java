package com.example.elimatsim;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.example.elimatsim.ui.login.LoginViewModel;
import com.example.elimatsim.ui.signup.SignUpViewModel;

public class AuthenticationActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private SignUpViewModel signupViewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Amplify asynchronously using a new thread
        new Thread(() -> {
            try {
                Amplify.addPlugin(new AWSApiPlugin());
                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.configure(getApplicationContext());
                Log.i("Elimatsim", "Initialized Amplify");
            } catch (AmplifyException error) {
                Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
            }
        }).start();

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );
        setContentView(R.layout.content_auth);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        signupViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_auth);
        navController.setGraph(R.navigation.auth_navigation);

    }
}
