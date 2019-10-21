package dzh.test;

        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.File;

public class SeventhRegisterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh_register);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }
    }

    public void doRegister(View view)
    {
        // 获取用户输入的信息
        String username = ((EditText)findViewById(R.id.id_seventh_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.id_seventh_password)).getText().toString();
        String verify =((EditText)findViewById(R.id.id_seventh_verify)).getText().toString();

        // 判断用户前后两次输入的密码是否一致
        if(!password.equals(verify))
        {
            Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        // 保存用户信息
        File file = new File(getExternalFilesDir(""),"users.txt");
        UserManager um = new UserManager(file);
        try
        {
            um.load(); // load()方法中会抛出异常，所以必须要进行处理
        }catch (Exception e){ }
        // 检查该用户名是否已经存在
        if(um.find(username) != null)
        {
            Toast.makeText(this,"用户已经存在",Toast.LENGTH_SHORT).show();
        }

        else
        {
            // 添加用户
            um.add(new User(username,password));
            try
            {
              um.save();
            }catch (Exception e)
            {
               e.printStackTrace();
            }
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
