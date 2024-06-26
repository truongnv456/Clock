package com.paci.training.android.truongnv92.clock.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.paci.training.android.truongnv92.clock.ClockConstant;
import com.paci.training.android.truongnv92.clockservice.IClockService;

public class ServiceRepository {
    private static final String TAG = ServiceRepository.class.getSimpleName();
    private IClockService mClockService;
    private Handler handler = new Handler();
    private boolean isServiceBound = false;

    public void bindService(Context context) {
        Intent intent = new Intent(ClockConstant.ACTION);
        intent.setPackage(ClockConstant.PACKAGE_NAME);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(Context context) {
        if (isServiceBound) {
            context.unbindService(mServiceConnection);
            isServiceBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mClockService = IClockService.Stub.asInterface(service);
            isServiceBound = true;
            Log.i(TAG, "onServiceConnected: Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mClockService = null;
            isServiceBound = false;
            Log.i(TAG, "onServiceDisconnected: Service disconnected");
        }
    };

    // Kiểm tra xem dịch vụ đã kết nối thành công chưa và mClockService có null không
    // Kết nối dịch vụ AIDL
    public String getCurrentTimeVietNam() {
        try {
            return mClockService.getCurrentTimeVietNam();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException: " + e.getMessage());
            return "Error get time Vietnam";
        }
    }

    // Lấy thời gian hiện tại cho USA
    public String getCurrentTimeUsa() {
        try {
            return mClockService.getCurrentTimeUSA();
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException: " + e.getMessage());
            return "Error get time Usa";
        }
    }

    public interface TimeCallback {
        void onTimeReceived(String time);
    }
}
