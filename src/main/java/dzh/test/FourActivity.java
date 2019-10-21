package dzh.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FourActivity extends AppCompatActivity implements View.OnClickListener
{
    // 安卓入门篇 09.01 内部储存

    static String TAG = "测试FourActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        // 查看本app的私有目录
        File appDir = getFilesDir();
        Log.d(TAG, "本APP的私有目录为:" + appDir);

        // 加载数据
        try
        {
            loadData();
            Toast.makeText(this, "已加载数据", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            Log.w(TAG, "无法加载数据!");
        }
        Button mSave = findViewById(R.id.id_four_btn_one);
        mSave.setOnClickListener(this);
        Button mLoadData = findViewById(R.id.id_four_btn_two);
        mLoadData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_four_btn_one:
            {
                String text = ((EditText)findViewById(R.id.id_four_edit)).getText().toString();

                // 在私有目录下创建文件abc.txt，然后往里面写入一个字符串
                File appDir = getFilesDir();
                File f = new File(appDir, "abc.txt");
                FileOutputStream fstream = null;
                try{
                    fstream = new FileOutputStream(f);
                    fstream.write( text.getBytes("UTF-8"));
                }catch(Exception e){
                }
                finally {
                    try{ fstream.close();} catch (Exception e2){}
                }

                Toast.makeText(this, "已保存", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.id_four_btn_two:
            {
                Intent intent = new Intent(FourActivity.this,FifthActivity.class);
                startActivity(intent);


                break;
            }
        }
    }
    private void loadData () throws Exception
    {
        File f = new File( getFilesDir(), "abc.txt");
        FileInputStream fstream = null;
        try{
            fstream = new FileInputStream(f);
            byte[] data = new byte[1024]; // 当文件很大时，应分作多次读取, 这里是简易写法仅作演示
            int n = fstream.read(data);
            if(n> 0)
            {
                // 当读取的到byte[]转成String
                String str = new String(data,0, n, "UTF-8");
                ((EditText)findViewById(R.id.id_four_edit)).setText(str);
                Log.d(TAG, "加载内容: " + str);
            }
        }finally
        {
            try{ fstream.close();}catch (Exception e){}
        }
    }
}
