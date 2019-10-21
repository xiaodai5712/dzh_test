package dzh.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class FifthActivity extends AppCompatActivity
{
    // 安卓入门篇 09_02 外部储存

    String TAG = "测试FifthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        File f = getExternalFilesDir("");
        Log.d(TAG, "onCreate: 外部储存路径为：" + f);

    }
}
