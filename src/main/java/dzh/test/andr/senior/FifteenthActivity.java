package dzh.test.andr.senior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

import dzh.test.R;

public class FifteenthActivity extends AppCompatActivity
{
    // senior 0702 小任务的一般实现

    AfDialog dlg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifteenth);
    }

    // 点击 保存 按钮
    public void doSave(View view)
    {
        // 获取输入的内容
        EditText editText = findViewById(R.id.id_fifteenth_editText);
        String content = editText.getText().toString().trim();
        // 弹出窗口
        dlg = new AfDialog();
        dlg.show(this,view);

        // 创建线程
        MyTask myTask = new MyTask(content);
        myTask.execute();
    }
    private void saveContent(String text) throws Exception
    {
        File dataDir = getExternalFilesDir("data");
        File f = new File(dataDir,"example.text");
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(text.getBytes("UTF-8"));
        }finally
        {
            try{fileOutputStream.close();}catch (Exception e) { }
        }
    }
    // 继承SmallTack,实现自己的小任务
    public class MyTask extends AfSmallTask
    {
        String content;
        public MyTask (String content)
        {
            this.content = content;
        }
        @Override
        protected Object doInBackground()
        {
            // 在线程里完成小任务
            try
            {
                saveContent(content);
                Thread.sleep(3000); // 如果不让线程睡3秒的话，这部分的任务很快就会被执行完毕，根本看不到弹窗开启和关闭的过程
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result)
        {
            // 更新ui
            dlg.dismiss();
        }
    }

}
