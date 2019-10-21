package dzh.test.andr.senior;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import dzh.test.R;

public class TwelfthActivity extends AppCompatActivity
{
    // 高级篇 第三章 多页面滑动布局 Fragment
    // 0302 实战练习：仿微信滑动布局

    Fragment[] fragments = new Fragment[3];
    AfTabBarAdapter tabBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twelfth);
        fragments[0] = new MsgFragment();
        fragments[1] = new CCFragment();
        fragments[2] = new UserFragment();
        final ViewPager viewPager = findViewById(R.id.id_twelfth_view_pager);
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());// 这一点比较特别，viewPager的adapter在实例化的时候需要 FragmentManager作为参数
        // 使用getSupportFragmentManager获得
        viewPager.setAdapter(myFragmentAdapter);

        AfTabBarAdapter.Item[] labels = new AfTabBarAdapter.Item[3];
        labels[0] = new AfTabBarAdapter.Item("微信","msg");
        labels[0].iconActive = getDrawable(R.drawable.ic_msg_active);
        labels[0].iconNormal = getDrawable(R.drawable.ic_msg_normal);
        labels[1] = new AfTabBarAdapter.Item("发现","cc");
        labels[1].iconNormal = getDrawable(R.drawable.ic_find_normal);
        labels[1].iconActive = getDrawable(R.drawable.ic_find_active);
        labels[2] = new AfTabBarAdapter.Item("我","user");
        labels[2].iconNormal = getDrawable(R.drawable.ic_user_normal);
        labels[2].iconActive = getDrawable(R.drawable.ic_user_active);
        tabBarAdapter = new AfTabBarAdapter(this);
        tabBarAdapter.addItems(labels);

        GridView gridView = (GridView)findViewById(R.id.id_twelfth_grid_view);
        gridView.setAdapter(tabBarAdapter);
        gridView.setNumColumns(labels.length); // gridView中一有几列
        tabBarAdapter.setActive(0,true); // 生成时默认选中第 1 项
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // 使viewPager切换到点击的页面
                viewPager.setCurrentItem(i);
                // 设置对应图标的高亮
                tabBarAdapter.setActive(i,true);
            }
        });

        // 滑动切换页面时，设置对应的图标高亮
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                tabBarAdapter.setActive(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        for(Fragment f: fragments)
        {
            f.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 自定义适合ViewPager的adapter
    private class MyFragmentAdapter extends FragmentPagerAdapter
    {

        public MyFragmentAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return fragments[position];
        }

        @Override
        public int getCount()
        {
            return fragments.length;
        }
    }
}
