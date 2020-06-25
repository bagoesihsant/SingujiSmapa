package com.example.singujismapa.ui.jadwal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JadwalViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public JadwalViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Jadwal Fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }
}
