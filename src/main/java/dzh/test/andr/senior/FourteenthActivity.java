package dzh.test.andr.senior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dzh.test.R;

public class FourteenthActivity extends AppCompatActivity
{
    // 0502 界面的备份与还原
    // 0605 秒表
    EditText editText;
    String TAG = "测试FourteenthAct";
    boolean isRun = false;
    WorkThread task;
    MyHandler myHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourteenth);
        editText = findViewById(R.id.id_fourteenth_text);
        if(savedInstanceState != null)
        {
            Log.d(TAG, "onCreate: Bundle 不为空" +savedInstanceState.getString("text") );
            editText.setText(savedInstanceState.getString("text"));
        }
    }


    // 点击开始
    public void startCount(View view)
    {
        if(! isRun)
        {
            // 开始计时
            task = new WorkThread();
            task.doStart();
            isRun = true; // 此时状态已经设置为启动
            ((Button)findViewById(R.id.id_fourteenth_start_btn)).setText("停止");
        }
        else
        {
            // 停止计时
            task.doStop();
            isRun = false;
            ((Button)findViewById(R.id.id_fourteenth_start_btn)).setText("开始");

        }
    }
    // 刷新显示
    public void showTime(int duration)
    {
        int ms = duration % 1000; // 这个是毫秒
        int seconds = duration / 1000; // 这个是秒
        // 转换成 MM:SS
        int ss = seconds % 60; // 这个是秒的数位
        int mm = seconds / 60; // 这个是分的数位
        String text = String.format("%03d:%02d:%02d",mm,ss,ms/10);// 这部分的写法我之前没接触过 比如把 2 写成 02用的是 %02d
        ((TextView)findViewById(R.id.id_fourteenth_clock)).setText(text);
    }


    // 自定义消息循环
    class MyHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            if(msg.what == 1)
            {
                // 从消息里取出参数
                int duration = msg.arg1;
                showTime(duration);
            }
            super.handleMessage(msg);
        }
    }
    // 计时线程
    class WorkThread extends Thread
    {
        boolean quitFlag = false;
        private void doStart()
        {
            start();
        }
        // 停止线程
        public void doStop()
        {
            // 终止线程的基本写法固定
            quitFlag = true;
            this.interrupt(); // 这里 this 指的是这当前的线程对象
            try
            {
                this.wait();
            } catch (Exception e)  // 只是改变了Exception的类型就会造成程序的崩溃 用Exception可以，用InterruptException就会崩溃，看来我对  异常了解还是不够透彻
            {
                Log.d(TAG, "doStop: 这里出现了问题");
            }
        }
        @Override
        public void run()
        {
            // 开始时间
            long startTime = System.currentTimeMillis();
            while (! quitFlag)
            {
                // 计算时差
                long now = System.currentTimeMillis();
                int duration = (int)(now - startTime);

                // 发送消息
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = duration;  // arg1 表示时差
                myHandler.sendMessage(msg);

                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    Log.d(TAG, "run:走了这里 ");
                    break; // 外部调用interrupt()时，抛出此异常
                }
            }
        }
    }




}
