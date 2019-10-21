package dzh.test.andr.senior;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import dzh.test.R;

public class AfComboBox
{
    final String TAG ="测试AfComboBox";

    // PopupWindow
    View contentView;
    PopupWindow popupWindow;

    // ListView
    LayoutInflater layoutInflater;
    ArrayList<Option> listData = new ArrayList<>();
    MyListAdapter listAdapter;

    public AfComboBox()
    {
    }
    // 添加下拉列表选项
    public void addOption(Option option)
    {
        listData.add(option);
    }
    public void addOptions(Option[] options)
    {
        for(Option option : options)
        {
            addOption(option);
        }
    }
    //
    public void show(Context context,View anchor,int xOff,int yOff)
    {
        // 创建view
        layoutInflater = LayoutInflater.from(context);
        contentView = layoutInflater.inflate(R.layout.af_combobox,null);

        //初始化窗口里的内容
        initContentView();
        // 创建PopupWindow,宽度恒定，高度自适应
        popupWindow = new PopupWindow(contentView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchor,xOff,yOff);
    }

    // 初始化
    private void initContentView()
    {
        // 初始化ListView
        listAdapter = new MyListAdapter();
        ListView listView = (ListView)contentView.findViewById(R.id.id_af_listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Option it = (Option)listAdapter.getItem(i);
                onItemClicked(view,it);
            }
        });
    }
    private void onItemClicked(View view,Option it)
    {
        // 关闭窗口
        popupWindow.dismiss();
        if(listener != null)
        {
            listener.onItemClickedListener(it);
        }
    }

    // 回调
    public interface OnItemClickedListener
    {
        public void onItemClickedListener(Option option);
    }
    public OnItemClickedListener listener;
    ///  每一条记录的数据
    public static class Option
    {
        public String name;
        public String value;
        public Drawable icon;
        public Option(){}
        public Option(String name,String value,Drawable icon)
        {
            this.name = name;
            this.value = value;
            this.icon = icon;
        }
    }

    // 适配器
    private class MyListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return listData.size();
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
            if(view == null)
            {
                // 使用外部类的 layoutInflater
                view = layoutInflater.inflate(R.layout.af_combobox_item,viewGroup,false);
            }
            // 获取以及显示数据
            Option it = (Option) getItem(i);
            TextView textView = (TextView) view.findViewById(R.id.id_combobox_item_title);
            textView.setText(it.name);
            if(it.icon != null)
            {
                // TextView 左侧显示图标
                textView.setCompoundDrawables(it.icon,null,null,null);
            }
            return view;
        }
    }
}
