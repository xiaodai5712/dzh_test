package dzh.test.andr.senior;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.ProtocolFamily;

import dzh.test.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MsgFragment extends Fragment
{

    // 高级篇 0303 在fragment中添加listView
    String[] data = new String[4];
    View view;
    public MsgFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_msg, container, false);
        MyListAdapter listAdapter = new MyListAdapter();
        ListView listView = (ListView)view.findViewById(R.id.id_twelfth_list_view);
        listView.setAdapter(listAdapter);
        initContent();

        return view;
    }
    private void initContent()
    {
        data[0] = "曾经有一份课程摆在你面前，你却没有珍惜，直追悔到几年之后你才莫及";
        data[1] = "最佳实践：每天2集左右，多了没用。必须坚持每天都学都练，三天打渔两天晒网是学不会的";
        data[2] = "编程是一门职业技术，不要以混过考试的目的来学习编程";
        data[3] = "坚持练习，保持热爱，时间会报答你的努力。";
    }

    public class MyListAdapter extends BaseAdapter
    {
        LayoutInflater inflater; // 只有LayoutInflater 才能把xml文件转换成页面布局
        public MyListAdapter()
        {
            inflater = LayoutInflater.from(getContext()); // 内部类的inflater通过外部类的context获得，只有activity才有inflater
        }
        @Override
        public int getCount()
        {
            return data.length;
        }

        @Override
        public Object getItem(int i)
        {
            return data[i];
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
                view = inflater.inflate(R.layout.list_item_msg,viewGroup,false);
            }
            ((TextView)view.findViewById(R.id.id_item_text)).setText(data[i]);
            return view;
        }
    }
}
