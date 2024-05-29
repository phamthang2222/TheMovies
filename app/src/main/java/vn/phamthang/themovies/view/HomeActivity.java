package vn.phamthang.themovies.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import vn.phamthang.themovies.Interface.FavMovieFireBase.IGetFavMovieFromFireBaseView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.ViewPagerAdapter.ViewPagerMainActivityAdapter;
import vn.phamthang.themovies.databinding.ActivityHomeBinding;
import vn.phamthang.themovies.fragments.HomeFragment;
import vn.phamthang.themovies.fragments.SearchFragment;
import vn.phamthang.themovies.fragments.WishListFragment;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.request.MovieRequest;
import vn.phamthang.themovies.presenter.FireBase.GetFavMovieFromFireBasePresenter;
import vn.phamthang.themovies.ultis.Constant;


public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, IGetFavMovieFromFireBaseView, SearchFragment.onBack {
    ActivityHomeBinding binding;
    private GetFavMovieFromFireBasePresenter mGetFavMovieFromFireBasePresenter;

    private ViewPagerMainActivityAdapter viewPagerMainActivityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.prime_color));

        initView();
        initData();
        setupBottomNavigation();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getListFavFromFireBase();
    }
    private void initData() {
        mGetFavMovieFromFireBasePresenter = new GetFavMovieFromFireBasePresenter(this);
        getListFavFromFireBase();
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
        viewPagerMainActivityAdapter = new ViewPagerMainActivityAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerMainActivityAdapter.addFragment(new HomeFragment());
        viewPagerMainActivityAdapter.addFragment(new SearchFragment());
        viewPagerMainActivityAdapter.addFragment(new WishListFragment());
        binding.viewpagerHomeActivity.setAdapter(viewPagerMainActivityAdapter);
        binding.viewpagerHomeActivity.setOffscreenPageLimit(3);
        binding.viewpagerHomeActivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
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


    private void getListFavFromFireBase() {
        mGetFavMovieFromFireBasePresenter.getFavMovieFromFireBase();
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


    @Override
    public void GetFavMovieFromFireBaseSuccess(List<Movie> movieRequestArrayList) {
        if(Constant.wishListMovieLocal!=null){
            Constant.wishListMovieLocal.clear();
        }
        Constant.wishListMovieLocal = (ArrayList<Movie>) movieRequestArrayList;
    }

    @Override
    public void GetFavMovieFromFireBaseError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}