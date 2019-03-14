package info.ankurpandya.selfmanagedcallingapp;

import android.os.Build;
import android.os.Bundle;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;

public class MyConnectionService extends ConnectionService {

    private static final String TAG = "SelfManaged";

    @Override
    public Connection onCreateIncomingConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
        Log.d(TAG, "onCreateIncomingConnection");
        MyConnection connection = new MyConnection(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            connection.setConnectionProperties(Connection.PROPERTY_SELF_MANAGED);
        }
        connection.setCallerDisplayName("TestID", TelecomManager.PRESENTATION_ALLOWED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            Bundle extras = new Bundle();
            extras.putBoolean(Connection.EXTRA_ANSWERING_DROPS_FG_CALL, true);
            extras.putString(Connection.EXTRA_CALL_SUBJECT, "Test call subject text");
            connection.putExtras(extras);
        }
        return connection;
    }

    @Override
    public void onCreateIncomingConnectionFailed(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
        Log.d(TAG, "onCreateIncomingConnectionFailed");
        super.onCreateIncomingConnectionFailed(connectionManagerPhoneAccount, request);
    }

    @Override
    public void onCreateOutgoingConnectionFailed(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
        Log.d(TAG, "onCreateOutgoingConnectionFailed");
        super.onCreateOutgoingConnectionFailed(connectionManagerPhoneAccount, request);
    }

    @Override
    public Connection onCreateOutgoingConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
        Log.d(TAG, "onCreateOutgoingConnection");
        return super.onCreateOutgoingConnection(connectionManagerPhoneAccount, request);
    }
}
