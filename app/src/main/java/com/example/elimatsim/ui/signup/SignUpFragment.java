package com.example.elimatsim.ui.signup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.elimatsim.R;
import com.example.elimatsim.databinding.SignupMainBinding;
import com.example.elimatsim.ui.login.LoginViewModel;

import java.util.ArrayList;

public class SignUpFragment extends Fragment {

    private SignupMainBinding binding;
    private SignUpViewModel signUpViewModel;
    private Boolean session;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SignupMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtain NavController associated with NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_content_auth);

        Button login = root.findViewById(R.id.signInButton);
        login.setOnClickListener(v -> {
            session=signUpViewModel.signIn(root.findViewById(R.id.username).toString(),
                    root.findViewById(R.id.password).toString(),
                    root.findViewById(R.id.email).toString(),
                    root.findViewById(R.id.phoneNum).toString(),
                    root.findViewById(R.id.name).toString());

            if(session)
                navController.navigate(R.id.action_signup_to_login);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
