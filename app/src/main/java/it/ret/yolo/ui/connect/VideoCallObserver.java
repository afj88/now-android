package it.ret.yolo.ui.connect;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.annimon.stream.Stream;
import com.inventia.connect.connectclient.ConnectClient;

import java.util.Map;

/**
 * Created by Alessandro Frigerio on 27/01/2017.
 */

public class VideoCallObserver implements ConnectClient.ConnectClientObserver {

    private static String TAG = "VideoCallObserver";

    private VideoCallObserverListener listener;

    public VideoCallObserver(VideoCallObserverListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClientReady() {

        Log.d(TAG, "onClient Ready");
    }

    @Override
    public void onRemoteTrying(String s) {
        Log.d(TAG, "onRemoteTrying - " + s);
    }

    @Override
    public void onRemoteRinging(String s, String s1) {
        Log.d(TAG, "onRemoteRinging - " + s + " - " + s1);
    }

    @Override
    public void onRemoteJoined(String s, String s1, Map<String, ?> map) {

        String mapText = getText(map);

        Log.d(TAG, "onRemoteRinging - " + s + " - " + s1 + " - " + mapText);
    }

    @Override
    public void onRemoteBye(String s, String s1) {

        Log.d(TAG, "onRemoteBye - " + s + " - " + s1);
    }

    @Override
    public void onRemoteBusy(String s, String s1) {
        Log.d(TAG, "onRemoteBusy - " + s + " - " + s1);
    }

    @Override
    public void onLoginSuccess() {
        Log.d(TAG, "onLoginSuccess");
    }

    @Override
    public void onLoginFailed(String s) {
        Log.d(TAG, "onLoginFailed - " + s);
    }

    @Override
    public void onIncomingRinging(String s, String s1, Map<String, ?> map) {

        String mapText = getText(map);

        Log.d(TAG, "onRemoteRinging - " + s + " - " + s1 + " - " + mapText);
    }

    @Override
    public void onConnectionReady() {
        Log.d(TAG, "onConnectionReady");
    }

    @Override
    public void onConnectionError(String s) {
        Log.d(TAG, "onConnectionError - " + s);
    }

    @Override
    public void onConnectionClosed() {
        Log.d(TAG, "onConnectionClosed");
    }

    @Override
    public void onCallFailed(String s) {
        Log.d(TAG, "onCallFailed - " + s);
    }

    @Override
    public void onCallEstablished(String s, String s1, Map<String, ?> map) {

        String mapText = getText(map);

        Log.d(TAG, "onRemoteRinging - " + s + " - " + s1 + " - " + mapText);
    }

    @Override
    public void onCallClosed(String s) {
        Log.d(TAG, "onCallClosed - " + s);
        listener.onCallClosed();
    }

    @Override
    public void onCallForwarded(String s) {
        Log.d(TAG, "onCallForwarded - " + s);
    }

    @Override
    public View onGetStepView(String s, ConnectClient.Rect rect) {

        int x = 0;
        int j = x++;

        return null;
    }

    @Override
    public void onStepViewWillClose(View view, String s) {

        int x = 0;
        int j = x++;

    }

    @NonNull
    private String getText(Map<String, ?> map) {
        StringBuilder sb = new StringBuilder();
        Stream.of(map).forEach(x -> {
            sb.append(x.getKey());
            sb.append(" ::: ");
            sb.append(x.getValue().toString());
            sb.append("\\n");
        });

        return sb.toString();
    }

    public interface VideoCallObserverListener {
        void onCallClosed();
    }
}
