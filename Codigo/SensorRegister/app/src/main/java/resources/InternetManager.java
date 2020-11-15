package resources;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.sensorregister.R;

public class InternetManager {
    private Context context;

    public InternetManager(Context applicationContext) {
        this.context = applicationContext;
    }

    public boolean check() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected)
            Toast.makeText(context, context.getString(R.string.isConnected), Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, context.getString(R.string.isDisconnected), Toast.LENGTH_LONG).show();
        return isConnected;
    }
}
