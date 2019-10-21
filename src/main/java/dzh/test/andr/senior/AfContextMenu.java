package dzh.test.andr.senior;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import dzh.test.R;

public class AfContextMenu
{
    final String TAG = "测试AfContextMenu";

    // PopUpWindow
    View contentView;
    PopupWindow popupWindow;

    // ListView
    LayoutInflater layoutInflater;
    MyListAdapter listAdapter;
    ArrayList<MyListItem> listData = new ArrayList<>();

    public AfContextMenu()
    {

    }
    public PopupWindow getPopupWindow()
    {
        return popupWindow;
    }

    // 添加菜单项
    public void addMenuItem(String option, String value)
    {
        MyListItem item = new MyListItem();
        item.option = option;
        item.value = value;
        listData.add(item);
    }

    // context,所在Activity
    // anchor，点中的View
    // xOff,yOff,偏移量
    public void show(Context context,View anchor, int xOff, int yOff)
    {
        // 创建View
        layoutInflater = LayoutInflater.from(context); // 每个context 会自带一个LayoutInflater
        contentView = layoutInflater.inflate(R.layout.af_context_menu,null);

        // 初始化窗口里的内容
        initContentView();

        // 创建popupWindow，宽度恒定，高度自适应
        popupWindow = new PopupWindow(contentView,400,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchor,xOff,yOff);


    }

    // 初始化
    public void initContentView()
    {
        // 初始化ListView
        listAdapter = new MyListAdapter();
        final ListView listView = (ListView)contentView.findViewById(R.id.id_list_view);
        listView.setAdapter(listAdapter);// 为listView 添加适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                MyListItem item = (MyListItem)listAdapter.getItem(i);
                onItemClicked(view,item);
            }
        });

    }

    // 回调
    public interface OnMenuItemClickedListener  // 将自定义的接口作为内部类
    {
        public void onMenuClicked(String option, String value);
    }

    public OnMenuItemClickedListener listener; // 自定义一个接口作为实例域

    // 当菜单被点击时
    private void onItemClicked(View view,MyListItem item)
    {
        // 关闭窗口
        popupWindow.dismiss();

        // 回调
        if(listener != null)
        {
            listener.onMenuClicked(item.option,item.value);
        }
    }


    // 自定义的适配器
    private class MyListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return listData.size(); // 这个方法要返回的是listView 中item的总数
        }

        @Override
        public Object getItem(int i)
        {
            return listData.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            // 创建控件
            if(view == null)
            {
                // 使用外部类的layoutInflater
                view = layoutInflater.inflate(R.layout.af_context_menu_item,viewGroup,false);
            }
            // 获取 并显示数据
            MyListItem item = (MyListItem)getItem(i);
            ((TextView)view.findViewById(R.id.id_menu_item_text)).setText(item.option);
            return view;
        }
    }
    // 每一条数据的记录
    private static class MyListItem
    {
        public String option;
        public String value;
    }
}
