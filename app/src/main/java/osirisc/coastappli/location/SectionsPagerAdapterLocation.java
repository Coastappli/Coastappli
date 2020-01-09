package osirisc.coastappli.location;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import osirisc.coastappli.R;

/**
 * A FragmentPagerAdapter returns a fragment corresponding to the tab selected by the user
 * In this case it returns one of the three tabs from the LocationMainActivity: Information, Indicators or Trace
 */
public class SectionsPagerAdapterLocation extends FragmentPagerAdapter {

    // We first define the titles of the tabs
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_information_name, R.string.tab_indicators_name, R.string.tab_trace_name}; // Put as many as you the number of tabs you want

    private final Context mContext;

    /**
     * We initialize the FragmentPageAdapter
     * @param context
     * @param fm
     */
    public SectionsPagerAdapterLocation(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * This function retrieves the wanted fragment depending on its position
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabInformationFragment();
            case 1:
                return new TabIndicatorsFragment();
            case 2:
                return new TabTraceFragment();
            default:
                return null;
        }
    }

    /**
     * This function retrieves the correct title for each tab corresponding to its position
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     * This function retrieves the number of tabs
     * @return
     */
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}