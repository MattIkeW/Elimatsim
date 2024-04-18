package com.example.elimatsim.ui.transactions;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TransactionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Currency is USD : $10,000");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }

}