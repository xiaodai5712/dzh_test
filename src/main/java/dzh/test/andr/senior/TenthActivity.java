package dzh.test.andr.senior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dzh.test.R;

public class TenthActivity extends AppCompatActivity
{
    // 高级篇 0201 弹出式窗口

    final String TAG = "测试TenthActivity";

    ArrayList<MyListItem> listData = new ArrayList<>();
    MyListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenth);
        //初始化 ListView
        listAdapter = new MyListAdapter();
        ListView listView = (ListView)findViewById(R.id.id_tenth_list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                MyListItem item = (MyListItem)listAdapter.getItem(i);
                onItemClicked(view,item);
            }
        });
        demo();
    }
    //演示数据
    private void demo()
    {
        MyListItem it;
        it = new MyListItem();
        it.text = "坚持练习，保持热爱，时间会报答你的努力。";
        listData.add(it);

        it = new MyListItem();
        it.text = "编程是一门职业技术，不要以混过考试的目的来学习编程。";
        listData.add(it);

        it = new MyListItem();
        it.text = "最佳实践：每天2集左右，多了没用。必须坚持每天都学都练，三天打渔两天晒网是学不会的。";
        listData.add(it);

        it = new MyListItem();
        it.text = "曾经有一份课程摆在你面前，你却没有珍惜，直追悔到几年之后你才莫及。";
        listData.add(it);

        listAdapter.notifyDataSetChanged();
    }

    // 长按一条记录时，弹出上下文菜单
    private void onItemClicked(View view,MyListItem item)
    {
        AfContextMenu menu = new AfContextMenu();
        menu.addMenuItem("发送给朋友","sendTo");
        menu.addMenuItem("收藏","favorite");
        menu.addMenuItem("删除","remove");
        menu.addMenuItem("更多","more");


        menu.show(this,view,view.getWidth()/2,-view.getHeight()/2);
        menu.listener = new AfContextMenu.OnMenuItemClickedListener()
        {
            @Override
            public void onMenuClicked(String option, String value)
            {
                Toast.makeText(TenthActivity.this, "点击了菜单项: " + value, Toast.LENGTH_SHORT).show();
            }
        };
    }

    // 记录每一条数据
    private static class MyListItem
    {
        public String text;
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
                view = getLayoutInflater().inflate(R.layout.list_item_msg,viewGroup,false);
            }
            MyListItem item = listData.get(i);
            ((TextView)view.findViewById(R.id.id_item_text)).setText(item.text);
            return view;
        }
    }


}
