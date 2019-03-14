package info.ankurpandya.selfmanagedcallingapp;

import android.content.Context;
import android.content.Intent;
import android.telecom.CallAudioState;
import android.telecom.Connection;

public class MyConnection extends Connection {

    private Context context;

    public MyConnection(Context context) {
        this.context = context;
    }

    @Override
    public void onShowIncomingCallUi() {
        //super.onShowIncomingCallUi();
        Intent intent = new Intent(context, IncomingCallActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCallAudioStateChanged(CallAudioState state) {
        super.onCallAudioStateChanged(state);
    }


}
