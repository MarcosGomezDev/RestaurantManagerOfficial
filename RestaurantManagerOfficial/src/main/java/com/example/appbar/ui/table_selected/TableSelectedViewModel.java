package com.example.appbar.ui.table_selected;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TableSelectedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TableSelectedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tables fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}