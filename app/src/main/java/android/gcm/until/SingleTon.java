package android.gcm.until;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Tuandv on 8/7/2015.
 */
public class SingleTon extends Application {
    public static Bus bus;
    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Bus(ThreadEnforcer.MAIN);
    }
}
