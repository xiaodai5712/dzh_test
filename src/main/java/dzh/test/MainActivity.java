package dzh.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import dzh.test.andr.senior.EleventhActivity;
import dzh.test.andr.senior.FifteenthActivity;
import dzh.test.andr.senior.FourteenthActivity;
import dzh.test.andr.senior.TenthActivity;
import dzh.test.andr.senior.ThirteenthActivity;
import dzh.test.andr.senior.TwelfthActivity;
import dzh.test.android.practice.EighthActivity;
import dzh.test.android.practice.NinthActivity;
import dzh.test.first_line_code.TestBroadCastActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private final static String TAG = "测试 MainActivity";
    private ProgressBar progressBar;
    Button mSecond ;
    Button mThird;
    Button mDlg;
    Button mFourth;
    private MyBroadCastReceiver broadCastReceiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }


//        ViewGroup.LayoutParams lp = btn.getLayoutParams();
//        int width = 500;
//        lp.width = width;
//        lp.height=500;//lp.height=LayoutParams.WRAP_CONTENT;
//        btn.setLayoutParams(lp);

        mSecond = findViewById(R.id.id_button_second);
        mSecond.setOnClickListener(this);

        mThird = findViewById(R.id.id_btn_third);
        mThird.setOnClickListener(this);

        mDlg = findViewById(R.id.id_btn_dlg);
        mDlg.setOnClickListener(this);

        mFourth = findViewById(R.id.id_btn_fourth);
        mFourth.setOnClickListener(this);

        broadCastReceiver = new MyBroadCastReceiver();
        filter = new IntentFilter();
        filter.addAction("android.intent.action.BOOT_COMPLETED");
        filter.addAction("zzzz");
        registerReceiver(broadCastReceiver,filter);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_btn_dlg:
            {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("How are you");
                dialog.setCancelable(false);
                dialog.setMessage("nihcoa");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(MainActivity.this,"你已经确认",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(MainActivity.this,"你已经取消",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.id_button_second:
            {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.id_btn_third:
            {
                Intent intent = new Intent(getApplicationContext(),ThirdActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.id_btn_fourth:
            {
                Intent intent = new Intent(getApplicationContext(), FifteenthActivity.class);
                startActivity(intent);
//                Intent intent = new Intent("dzh.MY_TEST");
//                sendBroadcast(intent);
            }


        }
    }
    public class MyBroadCastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Toast.makeText(context,"已经开机",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceive:接收开机信息 hahha");
        }
    }
}
