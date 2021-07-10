package com.quickblox.sample.videochat.java.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by poonam.
 */

public class ConnectionDetector {
    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}

/*
class RequestInterceptor implements Interceptor {

    final ConnectivityManager connectivityManager;
    public RequestInterceptor(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isConnected()) {
            try {
                throw new OfflineException ("no internet");
            } catch (OfflineException e) {
                e.printStackTrace();
            }
        }

        Request.Builder r = chain.request().newBuilder();
        return chain.proceed(r.build());
    }

    protected boolean isConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    class OfflineException extends Throwable {
        public OfflineException(String no_internet) {
        }
    }


}
*/




