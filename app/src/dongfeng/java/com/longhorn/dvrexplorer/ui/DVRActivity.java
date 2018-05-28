package com.longhorn.dvrexplorer.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hsae.autosdk.dvr.IDvrStateNotify;
import com.longhorn.dvrexplorer.R;

import java.lang.reflect.Constructor;

/**
 * Created by FlyZebra on 2018/5/23.
 * Descrip:
 */

public class DVRActivity extends Activity {
    private Button bt_home, bt_record, bt_file, bt_set;

    private IDvrStateNotify iDvrStateNotify;
    private ServiceConnection  serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iDvrStateNotify = IDvrStateNotify.Stub.asInterface(service);
            try {
                iDvrStateNotify.notityLinkStatus(1);
                iDvrStateNotify.notitySDCardStatus(2);
                iDvrStateNotify.notityTakePhotoRespond(3);
                iDvrStateNotify.notityLinkStatus(4);
                iDvrStateNotify.notityUpdateSchedule(5);
                iDvrStateNotify.notityWorkStatus(6);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //TODO:服务断开处理
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvr);

        initService();

        bt_home = findViewById(R.id.ac_dvr_bt_home);
        bt_record = findViewById(R.id.ac_dvr_bt_record);
        bt_set = findViewById(R.id.ac_dvr_bt_set);
        bt_file = findViewById(R.id.ac_dvr_bt_file);

        addFragment("RtspFragment");
        bt_record.setEnabled(false);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addFragment("RtspFragment")) {
                    bt_record.setEnabled(false);
                    bt_file.setEnabled(true);
                    bt_set.setEnabled(true);
                }
            }
        });

        bt_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addFragment("FileFragment")) {
                    bt_record.setEnabled(true);
                    bt_file.setEnabled(false);
                    bt_set.setEnabled(true);
                }
            }
        });

        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addFragment("SetFragment")) {
                    bt_record.setEnabled(true);
                    bt_file.setEnabled(true);
                    bt_set.setEnabled(false);
                }
            }
        });
    }

    private void initService() {
//        ComponentName componentName = new ComponentName("com.longhorn.dvrstatenotify","com.longhorn.dvrstatenotify.service.DvrStateNotifyService");
        Intent intent = new Intent();
//        intent.setComponent(componentName);
        intent.setAction("com.hsae.auto.DVR_SERVICE");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private boolean addFragment(String fName) {
        try {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Class<?> cls = Class.forName("com.longhorn.dvrexplorer.ui.fragment." + fName);
            Constructor<?> cons = cls.getConstructor();
            Fragment fragment = (Fragment) cons.newInstance(); //
            ft.replace(R.id.ac_dvr_fm01, fragment).commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFragment(Fragment fragment1, Fragment fragment2) {
        try {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.hide(fragment1);
            ft.add(R.id.ac_dvr_fm01, fragment2);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        try {
            unbindService(serviceConnection);
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
