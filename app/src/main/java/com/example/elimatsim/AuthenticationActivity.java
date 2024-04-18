package com.example.elimatsim;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.example.elimatsim.databinding.ContentAuthBinding;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ContentAuthBinding binding = ContentAuthBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.nav_host_fragment_content_auth);
//
//        assert navHostFragment != null;
//        NavController navController = navHostFragment.getNavController();
//        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.auth_navigation);

        setContentView(R.layout.signup_main);

        new Thread(this::amplifyInitialize).start();

        // Navigation.findNavController(view).navigate(R.id.ACTION_in_NavGraph)

//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
//                findFragmentById(R.id.nav_host_fragment_content_auth);
//        assert navHostFragment != null;
//        NavController navController = navHostFragment.getNavController();

//        NavController navController = Navigation.findNavController
//                (this, R.id.nav_host_fragment_content_auth);
//        navController.setGraph(R.navigation.auth_navigation);
    }

    private void amplifyInitialize() {
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
    }
}