package info.ankurpandya.selfmanagedcallingapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TelecomManager telecomManager;

    private static final String callingAccountId = "abc123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        boolean checkAccountConnection = checkAccountConnection(this);
        System.out.println("-- checkAccountConnection [" + checkAccountConnection + "] --");

        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStartCall();
            }
        });

        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        setupCallAccount();
    }

    private void handleStartCall() {
        PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(
                new ComponentName(this.getApplicationContext(), MyConnectionService.class),
                callingAccountId
        );

        PhoneAccount phoneAccount = PhoneAccount.builder(phoneAccountHandle, callingAccountId)
                .setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER).build();
        telecomManager.registerPhoneAccount(phoneAccount);
        Bundle extras = new Bundle();
        //providerName
        Uri uri = Uri.fromParts(PhoneAccount.SCHEME_TEL, "0123456789", null);
        extras.putParcelable(TelecomManager.EXTRA_INCOMING_CALL_ADDRESS, uri);
        extras.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle);
        telecomManager.addNewIncomingCall(phoneAccountHandle, extras);
    }

    private void setupCallAccount() {
        ComponentName componentName = new ComponentName(
                "info.ankurpandya.selfmanagedcallingapp",
                "info.ankurpandya.selfmanagedcallingapp.MyConnectionService"
        );
        PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(componentName, "Admin");
        PhoneAccount phoneAccount = PhoneAccount.builder(phoneAccountHandle, "Admin").build();

        telecomManager.registerPhoneAccount(phoneAccount);
    }

    private boolean checkAccountConnection(Context context) {
        boolean isConnected = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                final List<PhoneAccountHandle> enabledAccounts = telecomManager.getCallCapablePhoneAccounts();
                for (PhoneAccountHandle account : enabledAccounts) {
                    if (account.getComponentName().getClassName().equals(MyConnectionService.class.getCanonicalName())) {
                        isConnected = true;
                        break;
                    }
                }
            }
        }
        return isConnected;
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "com.android.server.telecom",
                "com.android.server.telecom.settings.EnableAccountPreferenceActivity"
        ));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
