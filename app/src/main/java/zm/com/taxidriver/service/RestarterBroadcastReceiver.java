package zm.com.taxidriver.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "Broadcast: Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, AppService.class));
    }
}
