package com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter;

import com.wurq.dex.mobilerecovery.recoveryapp.application.ISubscriber;

//import com.wurq.dex.mobilerecovery.hearttouch.hthttp2.operation.AbstractRequest;

/**
 * Created by ht-template
 **/
public abstract class BasePresenter<T> implements ISubscriber {
    protected T target;
//    private List<AbstractRequest> requests = new ArrayList<>();

    public BasePresenter(T target) {
        this.target = target;
    }

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
//        cancelRequests();
    }

    public void onDestroy() {
    }

//    protected void putRequest(AbstractRequest request) {
//        requests.add(request);
//    }
//
//    protected void cancelRequests() {
//        if (target instanceof BaseActivity) {
//            DialogUtil.hideProgressDialog((BaseActivity) target);
//        } else if (target instanceof BaseFragment) {
//            DialogUtil.hideProgressDialog(((BaseFragment) target).getActivity());
//        }
//        for (AbstractRequest request : requests) {
//             request.cancel();
//        }
//        requests.clear();
//    }
}
