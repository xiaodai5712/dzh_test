package dzh.test.first_line_code;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import dzh.test.MainActivity;

public class BootCompleteReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // an Intent broadcast.

        Toast.makeText(context,"Boot Complete,手机已开",Toast.LENGTH_LONG).show();

    }
}
