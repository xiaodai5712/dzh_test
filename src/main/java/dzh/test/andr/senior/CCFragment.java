package dzh.test.andr.senior;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import dzh.test.R;
import dzh.test.android.practice.Permissions;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CCFragment extends Fragment
{
    // 高级篇 0304 在Fragment中打开activity

    final int REQ_OPEN_FILE = 2;
    View contentView;

    public CCFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Permissions.check(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        contentView =  inflater.inflate(R.layout.fragment_cc, container, false);

        // 页面的初始化
        Button button = (Button) contentView.findViewById(R.id.id_cc_open_file);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               openFile();
            }
        });
        return contentView;
    }

    void openFile()
    {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Intent intent = new Intent(Intent.ACTION_PICK,uri);
        startActivityForResult(intent,REQ_OPEN_FILE);
    }

    // 结果先返回到TwelfthActivity.onActivityResult()中，然后再有其分发给自Fragment
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQ_OPEN_FILE)
        {
            if(resultCode == RESULT_OK)
            {
                try
                {
                    // 把contentURI 转成文件路径
                    Uri mediaUri = data.getData();
                    String filePath = filePathFromContentUri(getContext(),mediaUri);
                    // 加载图片
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    ImageView imageView = (ImageView)contentView.findViewById(R.id.id_cc_image_view);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    // 把ContentUri转成FilePath， 注意这里是android.net.Uri 而不出java.net.URI
    public static String filePathFromContentUri(Context context,Uri mediaUri)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(mediaUri,projection,null,null,null);
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(projection[0]);
        String imagePath = cursor.getString(column);
        return  imagePath;
    }
}
