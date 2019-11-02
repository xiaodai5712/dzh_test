package dzh.test.andr.senior;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import dzh.test.R;

public class AfDialog
{
    final String TAG = "测试AfDialog";

    // PopupWindow
    View contentView;
    PopupWindow popupWindow;
    LayoutInflater layoutInflater;
    public AfDialog()
    {

    }

    public void show(Context context,View anchor)
    {
        layoutInflater = LayoutInflater.from(context);
        contentView = layoutInflater.inflate(R.layout.af_wait_dialog,null);

        // 创建popupWindow
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(anchor, Gravity.CENTER,0,0);


    }
    // 关闭窗口
    public void dismiss()
    {
        popupWindow.dismiss();
    }
}
