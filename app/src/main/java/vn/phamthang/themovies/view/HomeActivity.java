package vn.phamthang.themovies.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationBarView;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.ViewPagerAdapter.ViewPagerMainActivityAdapter;
import vn.phamthang.themovies.databinding.ActivityHomeBinding;
import vn.phamthang.themovies.fragments.HomeFragment;
import vn.phamthang.themovies.fragments.SearchFragment;
import vn.phamthang.themovies.fragments.WhisListFragment;


public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, SearchFragment.onBack   {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.search:
                        replaceFragment(new SearchFragment());
                        break;
                    case R.id.watchlist:
                        replaceFragment(new WhisListFragment());
                        break;
                }
                return true;
            }
        });


    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onSearch() {
        replaceFragment(new SearchFragment());
        binding.bottomNavigation.setSelectedItemId(R.id.search);
    }

    @Override
    public void onBack() {
        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setSelectedItemId(R.id.home);
    }
}