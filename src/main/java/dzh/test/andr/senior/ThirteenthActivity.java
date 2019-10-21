package dzh.test.andr.senior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dzh.test.R;

public class ThirteenthActivity extends AppCompatActivity
{

    //0402 实战练习 - 仿QQ抽屉菜单
    final String TAG = "测试ThirteenthActivity";
    DrawerLayout drawerLayout;

    MyDrawerListAdapter drawerListAdapter;
    ArrayList<MyDrawerListItem> drawerListData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thriteenth);

        int orientation=getResources().getConfiguration().orientation;

        // 初始化 drawerLayout
        drawerLayout = (DrawerLayout)findViewById(R.id.id_drawer_layout);
        drawerLayout.setScrimColor(0xAACCCCCC); // 抽屉拉开时，contentView 的灰化颜色

        // 设置DrawerLayout里的ListView
        // 初始化ListView
        ListView listView = (ListView)findViewById(R.id.id_drawer_menu);
        drawerListAdapter = new MyDrawerListAdapter();
        listView.setAdapter(drawerListAdapter);
        iniData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                MyDrawerListItem item = (MyDrawerListItem) drawerListAdapter.getItem(i);
                onDrawerItemClicked(item);
            }
        });
    }

    private void onDrawerItemClicked(MyDrawerListItem item)
    {
        Toast.makeText(this,String.format("点击了菜单项：%s",item.cmd),Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawer(GravityCompat.START,true);
    }

    private void iniData()
    {
        // 初始化抽屉里的菜单项
        drawerListData.add(new MyDrawerListItem("我的超级会员", "cmd1", null));
        drawerListData.add(new MyDrawerListItem("QQ钱包",   "cmd2", null));
        drawerListData.add(new MyDrawerListItem("个性装扮", "cmd3", null));
        drawerListData.add(new MyDrawerListItem("我的收藏", "cmd4", null));
        drawerListData.add(new MyDrawerListItem("我的相册", "cmd5", null));
        drawerListData.add(new MyDrawerListItem("我的文件", "cmd6", null));
    }
    // 每一条数据的记录
    static class MyDrawerListItem
    {
        public String title; // 菜单项的显文本
        public String cmd; // 菜单项显示的命令
        public Drawable icon; // 菜单项左侧显示的图标

        public MyDrawerListItem() {}
        public MyDrawerListItem(String title,String cmd,Drawable icon)
        {
            this.cmd = cmd;
            this.title = title;
            this.icon = icon;
        }
    }

    // 适配器
    private class MyDrawerListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return drawerListData.size();
        }

        @Override
        public Object getItem(int i)
        {
            return drawerListData.get(i);
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
                view = getLayoutInflater().inflate(R.layout.thirteenth_list_item_drawer,viewGroup,false);
            }
            MyDrawerListItem item = (MyDrawerListItem) getItem(i);
            ((TextView)view.findViewById(R.id.id_id_thirteenth_list_item)).setText(item.title);
            return view;
        }
    }
}
