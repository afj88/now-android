package it.ret.yolo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inventia.connect.connectclient.ConnectClient;
import com.inventia.connect.connectclient.ConnectFragment;
import com.inventia.connect.connectclient.ConnectOperatorAvailability;
import com.inventia.connect.connectclient.ConnectVersion;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

import it.ret.yolo.R;
import it.ret.yolo.ui.connect.VideoCallObserver;

/**
 * Created by Alessandro Frigerio on 27/01/2017.
 */

public class VideoCallFragment extends ConnectFragment implements VideoCallObserver.VideoCallObserverListener {

    //    private static final String MOBILE_ENTRY_POINT_URL = "file:///android_asset/connectsdkassets/mobile.html";
    private static final String MOBILE_ENTRY_POINT_URL = "https://cnpvita.inventia.biz/connect-server-cnpvita/mobile/mobile.html";
    private static final String CONNECT_DATA_FIRST_NAME = "nome";
    private static final String CONNECT_DATA_SECOND_NAME = "cognome";
    private static final String CONNECT_DATA_BIRTH_DATE = "data-nascita";
    private static final String CONNECT_DATA_FISCAL_CODE = "codice-fiscale";
    private static final String CONNECT_DATA_EMAIL = "email";
    private static final String CONNECT_DATA_LINK = "link";
    private static final String CONNECT_CONFIG_SESSION_TOKEN = "sessionToken";
    private static final String CONNECT_CONFIG_PROCESS_ID = "processId";
    private static final String TAG = "VideoCallFragment";
    private static final String CONNECT_CONFIG_SERVER_ADDRESS = "serverAddress";
    private String firstName;
    private String secondName;
    private DateTime birthDate;
    private String fiscalCode;
    private String email;
    private String link;

    private String serverAddress;
    private boolean isSameVersion;
    private String sessionToken;
    private View root;
    private ConnectClient connectClient;

    public static VideoCallFragment newInstance(String firstName, String secondName, @NonNull DateTime birthDate, String fiscalCode, String email, String link) {
        Bundle args = new Bundle();
        args.putString(CONNECT_DATA_FIRST_NAME, firstName);
        args.putString(CONNECT_DATA_SECOND_NAME, secondName);
        args.putLong(CONNECT_DATA_BIRTH_DATE, birthDate.getMillis());
        args.putString(CONNECT_DATA_FISCAL_CODE, fiscalCode);
        args.putString(CONNECT_DATA_EMAIL, email);
        args.putString(CONNECT_DATA_LINK, link);

        VideoCallFragment videoCallFragment = new VideoCallFragment();
        videoCallFragment.setArguments(args);

        return videoCallFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(CONNECT_CONFIG_SERVER_ADDRESS)) {
            this.serverAddress = savedInstanceState.getString(CONNECT_CONFIG_SERVER_ADDRESS);
        }

        Bundle args = getArguments();
        this.firstName = args.getString(CONNECT_DATA_FIRST_NAME);
        this.secondName = args.getString(CONNECT_DATA_SECOND_NAME);
        this.birthDate = new DateTime(args.getLong(CONNECT_DATA_BIRTH_DATE));
        this.fiscalCode = args.getString(CONNECT_DATA_FISCAL_CODE);
        this.email = args.getString(CONNECT_DATA_EMAIL);
        this.link = args.getString(CONNECT_DATA_LINK);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (this.serverAddress == null || this.serverAddress.equals("")) {
            ConnectClient.getServerAddress(MOBILE_ENTRY_POINT_URL, new ConnectClient.ConnectMethodCallback<String, String>() {
                @Override
                public void onSuccess(String s) {
                    if (s != null) {
                        serverAddress = s;
                        checkVersion();
                    }
                }

                @Override
                public void onFail(String s) {
                    Log.d(TAG, "Network error or invalid entry point");
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(CONNECT_CONFIG_SERVER_ADDRESS, this.serverAddress);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

    private void checkVersion() {
        ConnectClient.checkVersion(serverAddress, new ConnectClient.ConnectMethodCallback<ConnectVersion, String>() {
            @Override
            public void onSuccess(ConnectVersion connectVersion) {
                isSameVersion = connectVersion.getCompatibility();
                if (isSameVersion) {
                    getOperatorAvailability();
                } else {
                    Log.d(TAG, "checkVersion FAIL - server is not compatible");
                }
            }

            @Override
            public void onFail(String s) {
                Log.d(TAG, "checkVersion ERROR: " + s);
            }
        });
    }

    private void getOperatorAvailability() {
        ConnectClient.getOperatorAvailability(serverAddress, new ConnectClient.ConnectMethodCallback<ConnectOperatorAvailability, String>() {
            @Override
            public void onSuccess(ConnectOperatorAvailability connectOperatorAvailability) {
                boolean isOperatorLogged = connectOperatorAvailability.getLoggedOperator();
                if (isOperatorLogged) {
                    getSessionToken();
                } else {
                    Log.w(TAG, "getOperatorAvailability | *** No operator available ***");
                    showNoOperatorAvailableMessage(root);
                }
            }

            @Override
            public void onFail(String s) {
                Log.d(TAG, "getOperatorAvailability ERROR: " + s);
            }
        });
    }

    private void getSessionToken() {

        DateTimeFormatter isoDateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");

        Map<String, String> map = new HashMap<>();
        map.put(CONNECT_DATA_FIRST_NAME, this.firstName);
        map.put(CONNECT_DATA_SECOND_NAME, this.secondName);
        map.put(CONNECT_DATA_BIRTH_DATE, isoDateTimeFormatter.print(this.birthDate.getMillis()));
        map.put(CONNECT_DATA_FISCAL_CODE, this.fiscalCode);
        map.put(CONNECT_DATA_EMAIL, this.email);
        map.put(CONNECT_DATA_LINK, this.link);

        ConnectClient.getSessionToken(map, this.serverAddress, new ConnectClient.ConnectMethodCallback<String, String>() {

            @Override
            public void onSuccess(String s) {
                sessionToken = s;
                initConnectClient();
            }

            @Override
            public void onFail(String s) {
                Log.d(TAG, "getSessionToken FAIL | ERROR: " + s);
            }
        });
    }

    private void initConnectClient() {
        connectClient = this.getConnectClient();

        HashMap<String, String> config = new HashMap<>();
        config.put(ConnectClient.kConnectConfigAppUrl, MOBILE_ENTRY_POINT_URL);
        config.put(CONNECT_CONFIG_SESSION_TOKEN, this.sessionToken);
        config.put(CONNECT_CONFIG_PROCESS_ID, "OpenAccount");
        config.put(ConnectClient.kConnectConfigKeyAutoConnect, String.valueOf(true));
        config.put(ConnectClient.kConnectConfigKeyAutoStartCall, String.valueOf(true));

        VideoCallObserver videoCallObserver = new VideoCallObserver(this);
        connectClient.initClient(videoCallObserver, config);

        root.setVisibility(View.VISIBLE);
    }

    private void showNoOperatorAvailableMessage(View view) {
        Snackbar.make(view, R.string.no_operator_available, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onCallClosed() {

    }
}
