package com.flyzebra.live555.rtsp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: FlyZebra
 * Time: 18-5-13 下午6:55.
 * Discription: This is RtspClient
 */
public class RtspClient {
    private IRtspCallBack iRtspCallBack;
    private static final Executor executor = Executors.newFixedThreadPool(1);

    static {
        System.loadLibrary("rtspclient");
    }

    public RtspClient(IRtspCallBack iRtspCallBack) {
        this.iRtspCallBack = iRtspCallBack;
    }

    public void connect(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                openUrl(url);
            }
        }).start();
    }

    public void close() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }).start();
    }

    private void onResult(String resultCode) {
        if (iRtspCallBack != null) iRtspCallBack.onResult(resultCode);
    }

    private void onVideo(byte[] videoBytes) {
        if (iRtspCallBack != null) iRtspCallBack.onVideo(videoBytes);
    }

    private void onAudio(byte[] audioBytes) {
        if (iRtspCallBack != null) iRtspCallBack.onAudio(audioBytes);
    }

    private void onRecvRTP(byte[] sps, byte[] pps) {
        if (iRtspCallBack != null) iRtspCallBack.onRecvRTP(sps, pps);
    }

    private native void openUrl(String url);

    private native void stop();

}
