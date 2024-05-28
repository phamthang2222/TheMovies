package vn.phamthang.themovies.fragments.DetailSimilarMovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import vn.phamthang.themovies.Interface.CastingMovie.ICastingMovieView;
import vn.phamthang.themovies.adapters.ListCastAdapter;
import vn.phamthang.themovies.databinding.FragmentCastBinding;
import vn.phamthang.themovies.objects.Cast.Cast;
import vn.phamthang.themovies.objects.Cast.CastingRespone;
import vn.phamthang.themovies.objects.Movie;
import vn.phamthang.themovies.presenter.CastingMoviePresenter;
import vn.phamthang.themovies.ultis.MessageEvent;

public class CastSimilarFragment extends Fragment implements ICastingMovieView {

    private Movie movie;
    private int iDMovie;
    private CastingMoviePresenter mCastingMoviePresenter;
    private ArrayList<Cast> mListCast;
    private ListCastAdapter mAdapter;

    FragmentCastBinding binding;

    public CastSimilarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCastBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    private void initData() {
        mCastingMoviePresenter = new CastingMoviePresenter(this);
        mListCast = new ArrayList<>();
        mAdapter = new ListCastAdapter(mListCast);

        binding.rcvListCasting.setAdapter(mAdapter);
        binding.rcvListCasting.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void getCastingMovieSuccess(CastingRespone castingRespone) {
        mListCast.clear();
        mAdapter.updateData((ArrayList<Cast>) castingRespone.getCast());
    }

    @Override
    public void getCastingMovieError(String error) {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onGetReViewMovie(MessageEvent event) {
        movie = event.getMovie();
        iDMovie = movie.getId();
        mCastingMoviePresenter.getCasting(iDMovie);
        EventBus.getDefault().removeStickyEvent(event);
    }
}