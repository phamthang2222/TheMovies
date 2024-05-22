package vn.phamthang.themovies.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.ViewPagerAdapter.ViewPagerMainActivityAdapter;
import vn.phamthang.themovies.databinding.ActivityHomeBinding;
import vn.phamthang.themovies.fragments.HomeFragment;
import vn.phamthang.themovies.fragments.SearchFragment;
import vn.phamthang.themovies.fragments.WhisListFragment;


public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, SearchFragment.onBack   {
    ActivityHomeBinding binding;
    private ViewPagerMainActivityAdapter viewPagerMainActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        initView();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    binding.viewpagerHomeActivity.setCurrentItem(0, true);
                    return true;
                case R.id.search:
                    binding.viewpagerHomeActivity.setCurrentItem(1, true);
                    return true;
                case R.id.watchlist:
                    binding.viewpagerHomeActivity.setCurrentItem(2, true);
                    return true;
            }
            return false;
        });
    }

    private void initView() {
        viewPagerMainActivityAdapter = new ViewPagerMainActivityAdapter(getSupportFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerMainActivityAdapter.addFragment(new HomeFragment());
        viewPagerMainActivityAdapter.addFragment(new SearchFragment());
        viewPagerMainActivityAdapter.addFragment(new WhisListFragment());
        binding.viewpagerHomeActivity.setAdapter(viewPagerMainActivityAdapter);
        binding.viewpagerHomeActivity.setOffscreenPageLimit(3);
        binding.viewpagerHomeActivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        binding.bottomNavigation.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        binding.bottomNavigation.setSelectedItemId(R.id.search);
                        break;
                    case 2:
                        binding.bottomNavigation.setSelectedItemId(R.id.watchlist);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    @Override
    public void onSearch() {
        binding.viewpagerHomeActivity.setCurrentItem(1, true);
        binding.bottomNavigation.setSelectedItemId(R.id.search);
    }

    @Override
    public void onBack() {
        binding.viewpagerHomeActivity.setCurrentItem(0, true);
        binding.bottomNavigation.setSelectedItemId(R.id.home);
    }



}