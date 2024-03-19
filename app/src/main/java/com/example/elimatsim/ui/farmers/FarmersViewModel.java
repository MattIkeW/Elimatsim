package com.example.elimatsim.ui.farmers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FarmersViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FarmersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This will be an overview page of account balances / investments." +
                " Note that each farmer will explicitly describe the terms of investment. " +
                "Categories will be provided on the list view and the are " +
                "short term (1 - 2 years ROI and dividend breakdowns), long term(5 - 10 ROI) " +
                "and industrial (Company live dividends but at least 10 yrs ROI.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}