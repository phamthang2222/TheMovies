package vn.phamthang.themovies.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;

import vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView;
import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.adapters.SpecialMovieAdapter;
import vn.phamthang.themovies.adapters.ViewPagerAdapter.ViewPagerMainActivityAdapter;
import vn.phamthang.themovies.databinding.FragmentHomeBinding;
import vn.phamthang.themovies.fragments.SubFragmentHome.NowPlayingFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.PopularFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.TopRateFragment;
import vn.phamthang.themovies.fragments.SubFragmentHome.UpComingFragment;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.ultis.MessageEvent;
import vn.phamthang.themovies.view.DetailActivity;

public class HomeFragment extends Fragment implements IMovieView, IMovieDetailView, SpecialMovieAdapter.OnItemClickListener {

    private MoviePresenter mMoviePresenter;
    private DetailMoviePresenter mDetailMoviePresenter;
    private ArrayList<Result> mListMovie;
    private SpecialMovieAdapter specialMovieAdapter;
    private String edtSearch;

    FragmentHomeBinding binding;

    public HomeFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
        getMostMovie();

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        ViewPagerMainActivityAdapter viewPagerMainActivityAdapter = new ViewPagerMainActivityAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerMainActivityAdapter.addFragment(new NowPlayingFragment(), "Now Playing");
        viewPagerMainActivityAdapter.addFragment(new UpComingFragment(), "Up Coming");
        viewPagerMainActivityAdapter.addFragment(new TopRateFragment(), "Top Rate");
        viewPagerMainActivityAdapter.addFragment(new PopularFragment(), "Popular");
        binding.viewPager.setAdapter(viewPagerMainActivityAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    private void getMostMovie() {
        mMoviePresenter.getDiscoverMovie();
    }

    private void initView() {
        binding.imgFind.setOnClickListener(v -> {
            edtSearch = binding.edtFind.getText().toString().trim();
            if (!edtSearch.isEmpty()) {

                EventBus.getDefault().post(new MessageEvent(edtSearch));

                if (getActivity() instanceof OnFragmentInteractionListener) {
                    ((OnFragmentInteractionListener) getActivity()).onSearch();
                }
            }
        });
    }

    private void initData() {
        mMoviePresenter = new MoviePresenter(this);
        mDetailMoviePresenter = new DetailMoviePresenter(this);
        mListMovie = new ArrayList<>();
        specialMovieAdapter = new SpecialMovieAdapter(mListMovie, this);


        binding.rcvListMostMovie.setAdapter(specialMovieAdapter);
        binding.rcvListMostMovie.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void getMovieSuccess(BestMovieRespone response) {
        mListMovie.clear();
        specialMovieAdapter.updateData((ArrayList<Result>) response.getResults());
    }

    @Override
    public void getMovieError(String error) {

    }

    @Override
    public void onItemClick(int idMovie) {
        mDetailMoviePresenter.getDetailMovie(idMovie);
    }

    @Override
    public void getDetailMovieSuccess(Movie response) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie", (Serializable) response);
        startActivity(intent);
    }

    @Override
    public void getDetailMovieError(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        void onSearch();
    }
}