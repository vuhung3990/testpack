package example.com.my_sliding_tab_library;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Tuandv on 04-Sep-15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<ViewPagerItemObject> list;

    public ViewPagerAdapter(FragmentManager fm, List<ViewPagerItemObject> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * ViewPager Item object
     */
    public static class ViewPagerItemObject {
        private String title;
        private Fragment fragment;

        public ViewPagerItemObject(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        /**
         * @return title
         */
        public String getTitle() {
            return title;
        }

        /**
         * set title
         *
         * @param title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return fragment
         */
        public Fragment getFragment() {
            return fragment;
        }

        /**
         * set fragment
         *
         * @param fragment
         */
        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }
}