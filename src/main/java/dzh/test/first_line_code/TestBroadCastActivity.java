package dzh.test.first_line_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.net.NetworkInterface;

import dzh.test.R;

public class TestBroadCastActivity extends AppCompatActivity
{
    // 第一行代码 第5章 5.2 接收系统光膜
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_broad_cast);

        intentFilter = new IntentFilter(); // 创建过滤器对象
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE"); // 过滤器的内容
        networkChangeReceiver = new NetworkChangeReceiver(); // 创建接收器
        registerReceiver(networkChangeReceiver,intentFilter); // 注册过滤器
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        @Override
        public void onReceive(Context context, Intent intent)  // 接收到广播之后，所执行的方法
        {
            if(networkInfo != null && networkInfo.isAvailable())
            {
                Toast.makeText(context,"网络连接正常",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context,"网络不可用",Toast.LENGTH_LONG).show();
            }

        }
    }
}
