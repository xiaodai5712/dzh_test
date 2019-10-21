package dzh.test.android.practice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

import dzh.test.R;

public class NinthActivity extends AppCompatActivity implements View.OnTouchListener
{

    final String TAG = "测试NinthActivity";
    final int REQ_OPEN_FILE = 101;
    VideoView videoView;
    SeekBar seekBar;
    ImageButton mPlayPause;
    Handler msgHandler;
    Timer timer;
    TimerTask timerTask;
    LinearLayout mControl;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 这个是设置全屏，即把系统的导航栏隐藏

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }
        // 权限的检查与获取
        Permissions.check(this);



        // 初始化界面
        videoView = findViewById(R.id.id_ninth_video); // 播放器
        seekBar = (SeekBar) findViewById(R.id.id_ninth_seekbar); // 可拖放进度条
        seekBar.setMax(100);

        msgHandler = new MyHandler();

        // 暂停和播放键
        mPlayPause = findViewById(R.id.id_play_pause);
        mPlayPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(videoView.isPlaying())
                {
                    Log.d(TAG, "onClick: 正在播放，即将暂停");
                    videoView.pause();
                    mPlayPause.setImageDrawable(getDrawable(R.drawable.ic_play)); // 动态的为按钮设置资源
                }
                else
                {
                    Log.d(TAG, "onClick: 暂停中，即将播放");
                    videoView.start();
                    mPlayPause.setImageDrawable(getDrawable(R.drawable.ic_pause));
                }
            }
        });

        // 拖动进度条播放
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            // 控制视频跳转到目标位置
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                if(videoView.isPlaying())
                {
                    int progress = seekBar.getProgress();
                    int position = progress * videoView.getDuration() / 100;
                    videoView.seekTo(position);
                }
            }
        });

        // 点击视频界面，隐藏或者显示控制条
        mControl = findViewById(R.id.id_ninth_control_bar);
        videoView.setOnTouchListener(this);
        Intent intent = getIntent();


        Uri mediaUri = intent.getData();
        if(mediaUri != null)
        {
            videoView.setVideoURI(mediaUri);
            videoView.start();
        }
    }

    @Override
    protected void onStart()
    {
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onStart();
        if(timer == null)
        {
            timerTask = new MyTimerTask();
            timer = new Timer();
            timer.schedule(timerTask,100,100);
        }
    }

    @Override
    protected void onStop()
    {
        if(timer != null)
        {
            timer.cancel();
            timer = null;  //界面隐藏时，将timer的任务取消，同时释放内存，如果不这样做cpu 的资源会被浪费掉
        }
        super.onStop();
    }

    public void doOpen(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*"); // 只显示视频
        startActivityForResult(intent,REQ_OPEN_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_OPEN_FILE)
        {
            if(resultCode == RESULT_OK)
            {
                if(data.getData() != null)
                {
                    Uri mediaUri = data.getData();
                    Log.d(TAG, "onActivityResult: Uri:" + mediaUri);
                    videoView.setVideoURI(mediaUri);
                    videoView.start();
                }
            }
        }
    }

    public void showProgress(int duration,int position)
    {
        int percent = position * 100 / duration; // 将原来的百分数转成整数
        seekBar.setProgress(percent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        if(mControl.getVisibility() == View.GONE)
        {
            mControl.setVisibility(View.VISIBLE);
        }
        else
        {
            mControl.setVisibility(View.GONE);
        }

        return false;

    }

    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            if(msg.what == 1)
            {
                int duration = msg.arg1;
                int position = msg.arg2;
                showProgress(duration,position);

            }
        }
    }

    private class MyTimerTask extends TimerTask
    {

        @Override
        public void run()
        {
            // 如果播放器没playing，则啥也不干，直接return
            if( !videoView.isPlaying())  return;

            // 获取当前播放进度
            int duration = videoView.getDuration(); // 总时长，单位:毫秒
            int position = videoView.getCurrentPosition(); // 当前播放位置

            Message msg = new Message();
            msg.what = 1;
            msg.arg1 = duration;
            msg.arg2 = position;
            msgHandler.sendMessage(msg);


        }
    }
}
