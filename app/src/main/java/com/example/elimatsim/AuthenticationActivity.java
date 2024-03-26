package com.example.elimatsim;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.elimatsim.databinding.LoginMainBinding;

import java.util.ArrayList;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
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
        // LoginMainBinding is AUTOMATICALLY generated from the XML rss file.
        LoginMainBinding binding = LoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment_content_auth);
        assert navHostFragment != null;

        NavController navController = navHostFragment.getNavController();

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(v -> login(navController));

        Button signIn = findViewById(R.id.signInButton);
        signIn.setOnClickListener(v -> signIn(navController));

       // NavigationView navigationView = binding;
    }

    private void login(NavController nc){
        EditText logUn = findViewById(R.id.loguser);
        EditText signPass = findViewById(R.id.logpass);

        Amplify.Auth.signIn(
                logUn.toString(),
                signPass.toString(),
                result -> Log.i("AuthQuickstart", result.isSignedIn() ? "Sign in succeeded" :
                        "Sign in not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );

        nc.navigate(R.id.action_login_to_mobile);
    }

    private void signIn(NavController nc){
        EditText logUn = findViewById(R.id.username);
        EditText signPass = findViewById(R.id.password);
        EditText email = findViewById(R.id.email);
        EditText pN = findViewById(R.id.phoneNum);
        EditText name = findViewById(R.id.name);

        Log.i("Email", email.toString());

        ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(), email.toString()));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), pN.toString()));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.name(), name.toString()));

        Amplify.Auth.signUp(
                logUn.toString(),
                signPass.toString(),
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

        nc.navigate(R.id.action_signup_to_login);
    }
}
