package com.example.elimatsim.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amplifyframework.core.Amplify;
import com.example.elimatsim.R;
import com.example.elimatsim.databinding.LoginMainBinding;
import com.example.elimatsim.databinding.SignupMainBinding;

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
        binding = LoginMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtain NavController associated with NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_content_auth);

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
