package com.example.elimatsim.ui.transactions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.elimatsim.MoMoAPI;
import com.example.elimatsim.databinding.FragmentSlideshowBinding;

/**
 * This class handles the Transaction page UI.
 * It has a button that changes the text view from boiler plate text to
 *      the Account Balance of the account holder.
 */
public class TransactionsFragment extends Fragment {
    private MoMoAPI apiUser;
    private FragmentSlideshowBinding binding;
    private String content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransactionsViewModel transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);

        binding.AccessToken.setOnClickListener(v -> new Thread(() -> {
            Log.i("Transactions Thread: ", "Thread Starting");
            apiUser = MoMoAPI.getInstance();
            content = apiUser.getAccountBalance();
        }
        ).start());
        if(content == null)
            content = "Failed to change content before thread ends";
        transactionsViewModel.getText().setValue(content);
        transactionsViewModel.getText().observe(getViewLifecycleOwner(), binding.textSlideshow::setText);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}