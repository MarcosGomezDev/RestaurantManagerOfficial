package com.example.appbar.ui.booking_select;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookingSelectViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BookingSelectViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is BookingSelect fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

