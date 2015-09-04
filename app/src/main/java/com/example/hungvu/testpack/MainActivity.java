package com.example.hungvu.testpack;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import example.com.my_sliding_tab_library.SlidingTabLayout;
import example.com.my_sliding_tab_library.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPagerAdapter adapter;
    private ViewPager pager;
    private SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // require style "Theme.AppCompat.Light.NoActionBar"
        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        final List<ViewPagerAdapter.ViewPagerItemObject> listData = new ArrayList<>();
        listData.add(new ViewPagerAdapter.ViewPagerItemObject("tab1", new ViewPagerItemFragment()));
        listData.add(new ViewPagerAdapter.ViewPagerItemObject("tab2", new ViewPagerItemFragment()));
        listData.add(new ViewPagerAdapter.ViewPagerItemObject("tab3", new ViewPagerItemFragment()));
        listData.add(new ViewPagerAdapter.ViewPagerItemObject("tab4", new ViewPagerItemFragment()));
        listData.add(new ViewPagerAdapter.ViewPagerItemObject("tab5", new ViewPagerItemFragment()));
        listData.add(new ViewPagerAdapter.ViewPagerItemObject("tab6", new ViewPagerItemFragment()));
        listData.add(new ViewPagerAdapter.ViewPagerItemObject("tab7", new ViewPagerItemFragment()));

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), listData);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.Build_In_Tab_Indicator_Color);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager, R.color.build_in_title_selector);

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // selected page here
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

