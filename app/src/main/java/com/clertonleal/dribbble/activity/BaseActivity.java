package com.clertonleal.dribbble.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.application.Application;
import com.clertonleal.dribbble.dagger.DribbbleComponent;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        injectMembers();
        ButterKnife.inject(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setOnClickListeners();
    }

    @Override
    protected void onDestroy() {
        unsubscribeSubscriptions();
        super.onDestroy();
    }

    protected void unsubscribeSubscriptions(){
        compositeSubscription.unsubscribe();
        compositeSubscription = new CompositeSubscription();
    }

    protected void showProgressDialog(int message) {
        cancelProgressDialog();
        if (!isFinishing()) {
            setUpProgressDialog(getResources().getString(message));
            progressDialog.show();
        }
    }

    protected void showProgressDialog() {
        cancelProgressDialog();
        if (!isFinishing()) {
            setUpProgressDialog(null);
            progressDialog.show();
        }
    }

    protected void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void setUpProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.dribble);
        progressDialog.setMessage(message);
    }

    protected DribbbleComponent dribbleComponent() {
        return Application.getDribbbleComponent();
    }

    protected void log(Throwable e){
        Log.e(getTag(), "", e);
    }

    protected void log(Exception e, String message){
        Log.e(getTag(), message, e);
    }

    private String getTag(){
        return this.getClass().getName();
    }

    protected Bundle getBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }

        return intent.getExtras();
    }

    protected void setOnClickListeners(){}

    protected abstract void setContentView();

    protected abstract void injectMembers();
}
