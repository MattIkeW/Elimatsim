package com.example.elimatsim.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.elimatsim.databinding.FragmentSlideshowBinding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TransactionsFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransactionsViewModel transactionsViewModel =
                new ViewModelProvider(this).get(TransactionsViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        transactionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void createAccessToken(){
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/remittance/token/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Authorization", "••••••••••••••••••••");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "••••••••••••••••••••••••••••••••");

            connection.setRequestMethod("POST");

            int status = connection.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);

            connection.disconnect();
        } catch (Exception ex) {
            System.out.print("exception:" + ex.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}