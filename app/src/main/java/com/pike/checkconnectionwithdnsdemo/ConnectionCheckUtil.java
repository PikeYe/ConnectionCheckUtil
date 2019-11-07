package com.pike.checkconnectionwithdnsdemo;

import com.orhanobut.logger.Logger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class ConnectionCheckUtil {

    private static final String TAG = "ConnectionCheckUtil";
    private static final int timeout = 1000;
    private static final String dns1 = "8.8.8.8";
    private static final String dns2 = "8.8.4.4";

    public interface OnConnectionCheckListener{
        void onConnected();
        void onDisconnected();
    }

    public static void connectionCheck(OnConnectionCheckListener callBack) {
        Observable check1 = getConnectionCheckObservable(dns1);
        Observable check2 = getConnectionCheckObservable(dns2);
        Observable.mergeDelayError(check1, check2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(getResultCheckObserver(callBack));
    }

    private static Observable getConnectionCheckObservable(String ip) {
        return Observable.create((ObservableOnSubscribe<String>) e -> {
            try {
                Socket s;
                s = new Socket();
                InetAddress host = InetAddress.getByName(ip);
                s.connect(new InetSocketAddress(host, 53), timeout);
                s.close();
                if(!e.isDisposed()) {
                    Logger.t(TAG).d("onNext" + ip);
                    e.onNext(ip);
                }
            } catch (Exception ex) {
                if(!e.isDisposed()) {
                    Logger.t(TAG).d("onError" + ex.getMessage());
                    e.onError(ex);
                }
            }
        });
    }
    private static Observer<String> getResultCheckObserver(OnConnectionCheckListener callBack) {
        return new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Logger.t(TAG).d("onSubscribe : " + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull String ip) {
                Logger.t(TAG).d("onNext : value : " + ip + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.t(TAG).d("onError : value : " + e.getMessage() + "\n");
                callBack.onDisconnected();
            }

            @Override
            public void onComplete() {
                Logger.t(TAG).d( "onComplete" + "\n");
                callBack.onConnected();
            }
        };
    }

    public static void initRxJavaDisposeErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> {
        });
    }
}
