package dzh.test.andr.senior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dzh.test.R;

public class EleventhActivity extends AppCompatActivity
{
    // 高级篇 0203 多字段查询
     // 学生列表
    MyListAdapter listAdapter;
    ArrayList<MyListItem> listData = new ArrayList<>();

    // 所有过滤条件
    AfComboBox.Option [] optionsA = new AfComboBox.Option[3];
    AfComboBox.Option[] optionsB = new AfComboBox.Option[4];

    // 当前过滤条件
    AfComboBox.Option filter1,filter2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleventh);

        // 过滤条件
        optionsA[0] = new AfComboBox.Option("性别:不限", "all", null);
        optionsA[1] = new AfComboBox.Option("男", "male", null);
        optionsA[2] = new AfComboBox.Option("女", "female", null);

        optionsB[0] = new AfComboBox.Option("年龄:不限", "all", null);
        optionsB[1] = new AfComboBox.Option("<18岁", "lt18", null);
        optionsB[2] = new AfComboBox.Option("18-20岁", "18-20", null);
        optionsB[3] = new AfComboBox.Option(">20岁", "gt20", null);

        // 默认过滤条件
        filter1 = optionsA[0];
        filter2 = optionsB[0];
        iniListView();
        showFilters();
    }

    // 点击第一个查询条件
    public void changeFilter1(View view)
    {
        AfComboBox cbx = new AfComboBox();
        cbx.addOptions(optionsA);
        cbx.show(this,view,0,0);

        cbx.listener = new AfComboBox.OnItemClickedListener()
        {
            @Override
            public void onItemClickedListener(AfComboBox.Option option)
            {
                filter1 = option;
                showFilters();
            }
        };
    }

    // 第二个查询条件
    public void changeFilter2(View view)
    {
        AfComboBox cbx = new AfComboBox();
        cbx.addOptions(optionsB);
        cbx.show(this,view,0,0);

        cbx.listener = new AfComboBox.OnItemClickedListener()
        {
            @Override
            public void onItemClickedListener(AfComboBox.Option option)
            {
                filter2 = option;
                showFilters();
            }
        };
    }

    // 显示当前过滤条件
    private void showFilters()
    {
        ((TextView)findViewById(R.id.id_eleventh_filter1)).setText(filter1.name);
        ((TextView)findViewById(R.id.id_eleventh_filter2)).setText(filter2.name);

        listAdapter.doFilter();

    }
    private void iniListData()
    {
        listData.add(new MyListItem("赵",true, 17));
        listData.add(new MyListItem("钱",true, 24));
        listData.add(new MyListItem("孙",false, 22));
        listData.add(new MyListItem("李",true, 18));
        listData.add(new MyListItem("周",false, 19));
        listData.add(new MyListItem("吴",true, 19));
        listData.add(new MyListItem("郑",true, 21));
        listData.add(new MyListItem("王",true, 18));
    }
    private void iniListView()
    {
        // 初始化 ListView
        listAdapter = new MyListAdapter();
        ListView listView = (ListView)findViewById(R.id.id_id_eleventh_listView);
        listView.setAdapter(listAdapter);

        iniListData();
    }

    // 演示数据
    private static class MyListItem
    {
        public String name;
        public boolean sex;
        public int age;
        public MyListItem(){}
        public MyListItem(String name, boolean sex, int age)
        {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }
    }
    /// 适配器
    private class MyListAdapter extends BaseAdapter
    {
        ArrayList<MyListItem> listCopy = new ArrayList<>();

        public MyListAdapter(){   }

        // 条件过滤
        public void doFilter()
        {
            listCopy.clear(); // 在过滤之前先将listCopy中的数据清空

            // 遍历listData中所有数据项，查找符合条件的项，添加到listCopy
            for(MyListItem item : listData)
            {
                // 过滤条件1： 按性别
                if(filter1.value.equals("male"))
                {
                    if(!item.sex) continue;
                }
                else if(filter1.value.equals("female"))
                {
                    if(item.sex) continue;
                }
                // 过滤条件2：按年龄
                if(filter2.value.equals("lt18"))
                {
                    if(item.age >= 18) continue;
                }
                else if(filter2.value.equals("18-20"))
                {
                    if(item.age<18 || item.age>20) continue;
                }
                else if(filter2.value.equals("gt20"))
                {
                    if(item.age <=20) continue;
                }

                // 符合条件则放到listCopy中
                listCopy.add(item);
            }
            notifyDataSetChanged();
        }
        @Override
        public int getCount()
        {
            return listCopy.size();
        }

        @Override
        public Object getItem(int i)
        {
            return listCopy.get(i);
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
                view = getLayoutInflater().inflate(R.layout.list_item_student,viewGroup,false);
            }

            // 获取、显示数据室
            MyListItem item = (MyListItem)getItem(i);
            ((TextView)view.findViewById(R.id.id_item_name)).setText(item.name);
            ((TextView)view.findViewById(R.id.id_item_sex)).setText(item.sex?"男":"女");
            ((TextView)view.findViewById(R.id.id_item_age)).setText(item.age + "岁");
            return view;
        }
    }
}
