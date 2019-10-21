package dzh.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener
{

    Button button ;
    ImageView imageView;
    final int REQ_PICK_IMAGE = 1;
    final String TAG = "测试Third";
    final int REQ_STORAGE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // 隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }
        checkPermissions();
        button = findViewById(R.id.id_get_picture);
        button.setOnClickListener(this);

    }

    // 检查权限
    private boolean checkPermissions()
    {
        final String[] ps = { Manifest.permission.READ_EXTERNAL_STORAGE };
        int rc = ActivityCompat.checkSelfPermission(this,ps[0]);
        if(rc != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,ps,REQ_STORAGE); // 获取权限有多种写法，也可以直接 requestPermissions(ps,0)
            return false;
        }
        return  true;
    }
    private void select()
    {
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // 这个URI是固定的
        Intent intent = new Intent( Intent.ACTION_PICK, uri);
        startActivityForResult(intent, REQ_PICK_IMAGE);
    }

    // 若权限被拒绝，则重新获取
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_STORAGE)
        {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"您拒绝授权",Toast.LENGTH_SHORT).show();

                 AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);  // getApplicationContext() 作为 context参数时，有时候不管用，但是不知道为什么？
                alertDialog.setTitle("警告");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("拒绝授予权限将无法使用");
                alertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },REQ_STORAGE);
                    }
                });
                alertDialog.show();
//                new AlertDialog.Builder(this)
//                        .setCancelable(false)
//                        .setTitle("警告")
//                        .setMessage("拒绝位置授权将导致无法使用本软件")
//                        .setPositiveButton("返回", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requestPermissions(new String[]{Manifest.permission_group.LOCATION}, REQ_STORAGE);
//                            }
//                        })
//                        .show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode == REQ_PICK_IMAGE)
        {
            if(resultCode == RESULT_OK)
            {
                Uri imageUri = data.getData();

                // 取出文件路径 ( 以下写法基本固定, 照抄即可, 不是本节课的重点 )
                // 其实这是又用imageUri作了一次查询操作，查询结果里有图片文件的路径
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver()
                        .query(imageUri, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(projection[0]);
                String imagePath = cursor.getString(column);
                Log.d(TAG, "选中文件的路径:" + imagePath);

                // 读取图片并显示
                Drawable image = Drawable.createFromPath(imagePath);
                imageView = (ImageView) findViewById(R.id.id_picture);
                imageView.setImageDrawable(image);
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        select();
    }
}
