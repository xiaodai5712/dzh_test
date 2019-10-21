package dzh.test.andr.senior;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import dzh.test.R;

public class AfTabBarAdapter extends BaseAdapter
{

    // 这个类是用来给页面底部的导航栏设置项目的适配器
    Context context;
    LayoutInflater inflater;

    // 颜色和字体
    int colorNormal = Color.argb(0xFF, 0x44, 0x44, 0x44);
    int colorActive = Color.argb(0xFF, 0x00, 0xFF, 0x00);

    // 标签项数据
    ArrayList<Item> listData = new ArrayList<>();

    public AfTabBarAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    // 添加一项
    public void addItem(Item it)
    {
        listData.add(it);
    }
    // 添加多项
    public void addItems(Item[] items)
    {
        listData.addAll(Arrays.asList(items));
    }

    // 设置选中项,改变所点击按钮的颜色
    public void setActive(int position ,boolean update)
    {
        for(int i = 0;i < listData.size(); i ++)
        {
            Item it = listData.get(i);
            if(i == position)   it.active = true;
            else it.active = false;
        }
        if(update) notifyDataSetChanged();
    }
    public static class Item
    {
        String label; // 标签显示
        String value; // 关联的数据
        public Drawable iconNormal;// 普通状态的图标
        public Drawable iconActive; // 选中时高亮的图标
        public boolean active = false;
        public Item()
        {

        }
        public Item(String label,String value)
        {
            this.label = label;
            this.value = value;
        }
        public Item (String label,String value,Drawable iconNormal,Drawable iconActive)
        {
            this.value = value;
            this.label = label;
            this.iconActive = iconActive;
            this.iconNormal = iconNormal;
        }
    }

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
            view = inflater.inflate(R.layout.af_tab_bar_item,viewGroup,false);
        }
        Item it = (Item)getItem(i);
        TextView label = (TextView)view.findViewById(R.id.id_tab_bar_label);
        ImageView imageView = (ImageView)view.findViewById(R.id.id_tab_bar_icon);

        label.setText(it.label);
        if(it.active)
        {
            if(it.label.length() > 0)
            {
                label.setTextColor(colorActive);
            }
            if(it.iconActive != null)
            {
                imageView.setImageDrawable(it.iconActive);
            }
            else
            {
                imageView.setImageDrawable(it.iconNormal);
            }

        }
        else
        {
            if(it.label.length() > 0)
            {
                label.setTextColor(colorNormal);
            }
            if(it.iconNormal != null)
            {
                imageView.setImageDrawable(it.iconNormal);
            }
        }
        return view;
    }
}
