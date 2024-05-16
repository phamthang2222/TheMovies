package vn.phamthang.themovies.adapters.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import vn.phamthang.themovies.fragments.SubFragmentHome.NowPlayingFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.PopularFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.TopRateFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.UpComingFragment;

public class ViewPagerMainActivityAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();
    public ViewPagerMainActivityAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpComingFragment();
            case 2:
                return new TopRateFragment();
            case 3:
                return new PopularFragment();
            default:
                return new NowPlayingFragment();
        }

    }

    @Override
    public int getCount()
    {
        return fragmentArrayList.size();
    }
    public void addFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
