package com.example.elimatsim.ui.signup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.elimatsim.R;
import com.example.elimatsim.databinding.SignupMainBinding;

public class SignUpFragment extends Fragment {

    private SignupMainBinding binding;
    private SignUpViewModel signUpViewModel;
    private Boolean session;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
        binding = SignupMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        // Obtain NavController associated with NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_content_auth);

        Button signUp = root.findViewById(R.id.signInButton);
        signUp.setOnClickListener(v -> {
            session=signUpViewModel.signIn(root.findViewById(R.id.username).toString(),
                    root.findViewById(R.id.password).toString(),
                    root.findViewById(R.id.email).toString(),
                    root.findViewById(R.id.phoneNum).toString(),
                    root.findViewById(R.id.name).toString());
            if(session)
                navController.navigate(R.id.action_signup_to_login);
        });
        return root;} catch (Exception e) {
            Log.e("SignUpFragment", "onCreateView", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
