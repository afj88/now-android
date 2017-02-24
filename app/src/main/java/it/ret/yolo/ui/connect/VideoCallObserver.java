package it.ret.yolo.ui.connect;

import android.view.View;

import com.inventia.connect.connectclient.ConnectClient;

import java.util.Map;

/**
 * Created by Alessandro Frigerio on 27/01/2017.
 */

public class VideoCallObserver implements ConnectClient.ConnectClientObserver {

    @Override
    public void onClientReady() {

    }

    @Override
    public void onRemoteTrying(String s) {

    }

    @Override
    public void onRemoteRinging(String s, String s1) {

    }

    @Override
    public void onRemoteJoined(String s, String s1, Map<String, ?> map) {

    }

    @Override
    public void onRemoteBye(String s, String s1) {

    }

    @Override
    public void onRemoteBusy(String s, String s1) {

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailed(String s) {

    }

    @Override
    public void onIncomingRinging(String s, String s1, Map<String, ?> map) {

    }

    @Override
    public void onConnectionReady() {

    }

    @Override
    public void onConnectionError(String s) {

    }

    @Override
    public void onConnectionClosed() {

    }

    @Override
    public void onCallFailed(String s) {

    }

    @Override
    public void onCallEstablished(String s, String s1, Map<String, ?> map) {

    }

    @Override
    public void onCallClosed(String s) {

    }

    @Override
    public void onCallForwarded(String s) {

    }

    @Override
    public View onGetStepView(String s, ConnectClient.Rect rect) {
        return null;
    }

    @Override
    public void onStepViewWillClose(View view, String s) {

    }
}
