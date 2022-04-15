package com.example.appbar.ui.tables;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TablesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TablesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tables fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}