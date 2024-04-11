package com.example.elimatsim.ui.transactions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.elimatsim.MoMoAPI;

public class TransactionsViewModel extends ViewModel {
    private  MoMoAPI apiUser;

    private final MutableLiveData<String> mText;

    public TransactionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is transactions fragment. List connecting to AWS database. " +
                "No data available.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}