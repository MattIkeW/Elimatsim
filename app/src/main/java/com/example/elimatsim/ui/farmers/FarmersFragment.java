package com.example.elimatsim.ui.farmers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.elimatsim.MoMoAPI;
import com.example.elimatsim.databinding.FragmentReflowBinding;

public class FarmersFragment extends Fragment {

    private FragmentReflowBinding binding;
    private MoMoAPI apiUser;
    private FarmersViewModel farmersViewModel;
    private String content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        farmersViewModel =
                new ViewModelProvider(this).get(FarmersViewModel.class);

        binding = FragmentReflowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button invest = binding.investNow;

        invest.setOnClickListener(v -> new Thread(() -> {
            Log.i("Farmers Thread: ", "Thread Starting");
            apiUser = MoMoAPI.getInstance();
            content = apiUser.getAccountBalanceCurr();
        }));

        farmersViewModel.getText().setValue(content);
        final TextView textView = binding.textReflow;
        farmersViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}