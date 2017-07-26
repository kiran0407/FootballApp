package kiran541.ench.com.footballapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Firebasemessagingservice extends Service {
    public Firebasemessagingservice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
