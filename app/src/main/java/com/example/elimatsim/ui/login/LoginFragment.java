package com.example.elimatsim.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.elimatsim.R;
import com.example.elimatsim.databinding.LoginMainBinding;

/**
 * The flow of this Fragment class takes data from the EditText fields and Passes that data into
 * the SharedViewModel instance to call the sign in method keeping modularity low.
 */
public class LoginFragment extends Fragment {

    private LoginMainBinding binding;
    private LoginViewModel loginViewModel;
    private Boolean session;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            binding = LoginMainBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            // Obtain NavController associated with NavHostFragment
            NavController navController = NavHostFragment.findNavController(this);

            EditText logUn = root.findViewById(R.id.loguser);
            EditText signPass = root.findViewById(R.id.logpass);

            loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
            Button login = root.findViewById(R.id.loginButton);
            login.setOnClickListener(v -> {
                session = loginViewModel.login(logUn.toString(), signPass.toString());
                if(session)
                    navController.setGraph(R.navigation.mobile_navigation);
            });
            return root;
        } catch (Exception e) {
            Log.e("LoginFragment", "onCreateView", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

    }
}
