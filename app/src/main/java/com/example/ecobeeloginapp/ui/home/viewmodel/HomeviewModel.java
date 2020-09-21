package com.example.ecobeeloginapp.ui.home.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecobeeloginapp.ApplicationController;
import com.example.ecobeeloginapp.data.LoginRepository;
import com.example.ecobeeloginapp.data.model.CatApiResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class HomeviewModel extends ViewModel {
    MutableLiveData<CatApiResponse> catApiResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> progress = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable;

    public HomeviewModel() {
        this.compositeDisposable = new CompositeDisposable();
    }

    public void buttonClicked(View view)
    {
        progress.setValue(true);
        fetchCatImage();
    }

    public void fetchCatImage() {
        compositeDisposable.add(LoginRepository.getLoginRepository().getCatImageDetails()
                .subscribeOn(ApplicationController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess,this::onError));
    }

    private void onSuccess(List<CatApiResponse> catApiResponseList) {
        progress.setValue(false);
        catApiResponseMutableLiveData.postValue(catApiResponseList.get(0));
    }

    private void onError(Throwable error) {
        catApiResponseMutableLiveData.postValue(null);
    }

    public MutableLiveData<CatApiResponse> getCatApiResponseMutableLiveData() {
        return this.catApiResponseMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgress() {
        return this.progress;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
