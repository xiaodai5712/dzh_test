package dzh.test.android.practice;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions
{
    static final int PERMISSION_REQ_CODE = 1;
    public static void check(Activity activity)
    {
        final String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA
        };

        if(ContextCompat.checkSelfPermission(activity,permissions[0])
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,permissions,PERMISSION_REQ_CODE);
        }
    }

    // 权限申请的结果
    // requestCode：请求码
    // permissions: 申请的N个权限
    // grantResults: 每个权限是否被授权
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
//    {
//
//        if(requestCode == PERMISSION_REQ_CODE)
//        {
//            for(int i=0; i<permissions.length;i++)
//            {
//                if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
//                {
//                    // 惨,用户没给我们授权...这意味着有此功能就不能用了
//                }
//            }
//        }
//    }

}
