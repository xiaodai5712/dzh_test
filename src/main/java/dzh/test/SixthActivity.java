package dzh.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;

import static dzh.test.Global.i;

public class SixthActivity extends AppCompatActivity implements View.OnClickListener
{
    Button mLogin;
    Button mRegister;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        ActionBar acBar = getSupportActionBar();
        if(acBar != null)
        {
            acBar.hide();
        }
        mLogin = findViewById(R.id.id_six_login);
        mLogin.setOnClickListener(this);
        mRegister = findViewById(R.id.id_sixth_register);
        mRegister.setOnClickListener(this);

        // 从配置里加载用户名和密码
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        String password = sharedPreferences.getString("password","");
        if(username.length()>0 && password.length()>0)
        {
            // 自动填写密码和用户名
            ((EditText)findViewById(R.id.id_sixth_password)).setText(password);
            ((EditText)findViewById(R.id.id_sixth_username)).setText(username);
            //延时1.5秒登录
            autoLogin();
        }
    }


    // 自动登录方法
    private void autoLogin()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                logIn();
            }
        },1500);
    }

    private void logIn()
    {
        // 获得用户的界面输入
        String username = ((EditText)findViewById(R.id.id_sixth_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.id_sixth_password)).getText().toString();

        // 从文件里加载所用用户的数据
        File file = new File(getExternalFilesDir(""),"users.txt");
        UserManager um = new UserManager(file);
        try
        {
            um.load(); // 将数据载入并解析到um内部的ArrayList中
        }catch (Exception e){ }

        // 从已有的列表里查找用户
        User u = um.find(username);
        if(u == null) // find()方法是有可能找不到匹配的User的，此时返回null
        {
            Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        if(! password.equals(u.password))
        {
            Toast.makeText(this,"密码不正确",Toast.LENGTH_SHORT).show();
            return;
        }

        // 已经通过了检验，说明用户有效，此时把其放在sharedPreference里，由各个activity共享
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password",password);
        editor.putString("username",username);
        editor.apply();
        // 把用户信息放到全局对象里，一遍个Activity都可以访问用户信息
        i.username = username;  //这句目前为止还没有被用到

        // 进入主页面
//        Intent intent = new Intent(SixthActivity.this,MainActivity.class);
//        startActivity(intent);
//        finish();
        Toast.makeText(this,"完成登录",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_six_login:
            {
                logIn();
                break;
            }
            case R.id.id_sixth_register:
            {
                Intent intent = new Intent(SixthActivity.this,SeventhRegisterActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
