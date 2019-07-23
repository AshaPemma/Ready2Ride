package com.warrous.ready2ride.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Ready2RideApp;
import com.warrous.ready2ride.framework.AndroidUtil;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements BaseContract.View {

    private ProgressDialog progressDialog;
    protected View inflateFragment(int resId, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(resId, container, false);
        injectView(view);
        return view;
    }

    protected void injectView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public Activity getAppActivity() {
        return getActivity();
    }

    @Override
    public void showLoader() {
        AndroidUtil.hideKeyBoard(getActivity());
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext(), R.style.ProgressBarSmall);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);

        }
        progressDialog.show();
    }

    @Override
    public void dismissLoader() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Ready2RideApp getReady2RideApp() {
        return (Ready2RideApp) getAppActivity().getApplication();
    }

    protected void injectButterKnife(View view) {
        ButterKnife.bind(this, view);
    }
}
