package vn.phamthang.themovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;

import vn.phamthang.themovies.Interface.MostMovie.IMovieView;
import vn.phamthang.themovies.R;
import vn.phamthang.themovies.adapters.SearchMovieAdapter;
import vn.phamthang.themovies.databinding.FragmentSreachBinding;
import vn.phamthang.themovies.objects.BestMovieRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.ultis.MessageEvent;
import vn.phamthang.themovies.view.DetailActivity;

public class SearchFragment extends Fragment implements IMovieView, SearchMovieAdapter.OnItemClickListener, vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView {


    private MoviePresenter moviePresenter;
    private DetailMoviePresenter mDetailMoviePresenter;
    private ArrayList<Result> listMovie;
    private SearchMovieAdapter mAdapter;

    FragmentSreachBinding binding;
    private String stringSearch = "";

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SearchFragment", "OnCreate");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("SearchFragment", "OnDestroy");

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Log.e("SearchFragment", "onResume");


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("SearchFragment", "OnDetach");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        binding.imgSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringSearch = binding.edtFind.getText().toString();
                if (!binding.edtFind.getText().toString().isEmpty()) {
                    getMovie(stringSearch);
                }
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            if (getActivity() instanceof onBack) {
                ((onBack) getActivity()).onBack();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSreachBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    private void initData() {
        mDetailMoviePresenter = new DetailMoviePresenter(this);
        moviePresenter = new MoviePresenter(this);
        listMovie = new ArrayList<>();
        mAdapter = new SearchMovieAdapter(listMovie, this);

        binding.rcvSearchMovie.setAdapter(mAdapter);
//        binding.rcvSearchMovie.setLayoutManager(
//                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


    }

    private void getMovie(String input) {
        moviePresenter.getSearchMovie(input);
    }

    @Override
    public void getMovieSuccess(BestMovieRespone response) {
        listMovie.clear();
        mAdapter.updateData((ArrayList<Result>) response.getResults());
    }

    @Override
    public void getMovieError(String error) {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        stringSearch = event.getTextMessage();
        binding.edtFind.setText(stringSearch);
        getMovie(stringSearch);
//        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void getDetailMovieSuccess(Movie response) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie", (Serializable) response);
        startActivity(intent);
    }

    @Override
    public void getDetailMovieError(String message) {

    }

    @Override
    public void onItemClick(int idMovie) {
        mDetailMoviePresenter.getDetailMovie(idMovie);
    }

    public interface onBack {
        void onBack();
    }
}