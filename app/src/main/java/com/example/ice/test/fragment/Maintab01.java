package com.example.ice.test.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.impleclass.ClassifyManager;
import com.example.ice.test.ui.AddClassifyActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Maintab01 extends Fragment {


    @InjectView(R.id.maintab01_tablayout)
    protected TabLayout tabLayout;
    @InjectView(R.id.maintab01_viewpage)
    protected ViewPager viewPager;
    @InjectView(R.id.maintab01_fab)
    protected FloatingActionButton fab;

    private LayoutInflater mInflater=null;
    private List<String> mTitleList;
    private List<View> mViewList;
    private String[][] data;
    public Maintab01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_maintab01, container, false);
        ButterKnife.inject(this,view);

        mInflater=getActivity().getLayoutInflater();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTitleList=new ArrayList<>();
        mViewList=new ArrayList<>();
        fromDBGetData();
        makeViews();
    }

    @OnClick(R.id.maintab01_fab)
    public void fabOnClick(){
        startActivity(new Intent().setClass(getActivity(), AddClassifyActivity.class));
    }
    /***
     * data[][]二维数组是数据库表 BookClassify的完全复制体
     * data2[][] 是将 ClassifyName 相同的合并在一起，两个字符串之间用空格分开
     * @function 动态添加viewpager和 tablayout
     */
    private void makeViews(){
        String[][] data2=new String[data.length][2];
        for(int i=0;i<data.length;i++){
            System.out.println("Maintab01    data:"+data[i][0]+" "+data[i][1]);
            data2[i][1]="0";
            data2[i][0]="0";
            for(int j=0;j<=i;j++){
                if(data2[j][1].equals("0"))
                {
                    data2[j][1]=data[i][1];
                    data2[j][0]=data[i][0];
                    break;
                }
                if(data2[j][1].equals(data[i][1]))
                {
                    data2[j][0]+=" "+data[i][0];
                    break;
                }
            }
        }
        for(int i=0;i<data2.length;i++){
            System.out.println("Maintab01    data2:"+data2[i][0]+" "+data2[i][1]);
            if(data2[i][1]==null||data2[i][1].equals("0"))
            {
                break;
            }

            ArrayList<HashMap<String, String>> listitem = new ArrayList<>();
            View view=mInflater.inflate(R.layout.maintab01_vp_layout,null);
      //      TextView textView=(TextView)view.findViewById(R.id.maintab01_viewpage_tv);
      //      textView.setText("1");
            ListView listView=(ListView)view.findViewById(R.id.maintab01_viewpage_lv);
            String[] booknames=data2[i][0].split(" ");
            for(int j=0;j<booknames.length;j++){
                HashMap<String,String> map=new HashMap<>();
                System.out.println(booknames[j]);
                map.put("bookname",booknames[j]);
                listitem.add(map);
            }

            SimpleAdapter simpleAdapter= new SimpleAdapter(getActivity(), listitem, R.layout.maintab01_lv_item,
                    new String[]{"bookname"},
                    new int[]{R.id.maintab01_item_tv});
            simpleAdapter.notifyDataSetInvalidated();
            listView.setAdapter(simpleAdapter);
            mTitleList.add(data2[i][1]);
            mViewList.add(view);


            tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(i)));
        }
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(mViewList);
        viewPager.setAdapter(myPagerAdapter);//给viewpage 设置适配器
        tabLayout.setupWithViewPager(viewPager);
    }
    private void fromDBGetData(){
        String sql="select name,ClassifyName from BookClassify,Book where BookClassify.bookid=Book.bookid";
        DatabaseFac databaseFac = new DatabaseFac(getActivity());
        ClassifyManager classifyManager=new ClassifyManager(databaseFac.getRead());
        Cursor cursor = classifyManager.rawQuery(sql,null);
        data=new String[cursor.getCount()][2];
        int i=0;
        while (cursor.moveToNext()){
            data[i][0]=cursor.getString(0);
            data[i][1]=cursor.getString(1);
            i++;
        }
        cursor.close();
        databaseFac.close();
    }
    class MyPagerAdapter extends PagerAdapter
    {
        private List<View> mViewList;
        public MyPagerAdapter(List<View> mViewList)
        {
            this.mViewList=mViewList;
        }
        @Override
        public int getCount() {
            return mViewList.size();//返回页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container,int position)
        {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container,int position,Object object)
        {
            container.removeView(mViewList.get(position));
        }
        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTitleList.get(position);//页卡标题
        }
    }
}
