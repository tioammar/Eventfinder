package inforuh.eventfinder.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by tioammar
 * on 8/13/15.
 */
public class AuthenticatorService extends Service {

    private Authenticator authenticator;

    public AuthenticatorService() {
        super();
    }

    @Override
    public void onCreate() {
        authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
