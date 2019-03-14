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
        System.out.println("-- onShowIncomingCallUi --");
        //super.onShowIncomingCallUi();
        Intent intent = new Intent(context, IncomingCallActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onCallAudioStateChanged(CallAudioState state) {
        super.onCallAudioStateChanged(state);
    }

    @Override
    public void onAbort() {
        super.onAbort();
    }

    @Override
    public void onHold() {
        super.onHold();
    }

    @Override
    public void onUnhold() {
        super.onUnhold();
    }

    @Override
    public void onAnswer() {
        super.onAnswer();
    }

    @Override
    public void onReject() {
        super.onReject();
    }
}
