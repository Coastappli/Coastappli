package osirisc.coastappli.place;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import osirisc.coastappli.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_information_name, R.string.tab_trace_name, R.string.tab_indicators_name}; //en mettre autant que le nombre de pages voulues
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabInformationFragment tab_info = new TabInformationFragment();
                return tab_info;
            case 1:
                TabTraceFragment tab_trace = new TabTraceFragment();
                return tab_trace;
            case 2:
                TabIndicatorsFragment tab_indicators = new TabIndicatorsFragment();
                return tab_indicators;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3; //Défini le nombre de pages du tabbed activity
    }
}