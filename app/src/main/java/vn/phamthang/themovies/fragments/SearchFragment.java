package vn.phamthang.themovies.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

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
import vn.phamthang.themovies.ultis.KeyBoardUtils;
import vn.phamthang.themovies.ultis.MessageEvent;
import vn.phamthang.themovies.view.DetailActivity;

public class SearchFragment extends Fragment implements IMovieView, SearchMovieAdapter.OnItemClickListener, vn.phamthang.themovies.Interface.MostMovie.DetailMovie.IMovieDetailView {

    private static final String TAG = "SearchFragment";
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
        Log.d(TAG, "OnCreate");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "OnDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "OnDetach");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        if (savedInstanceState != null) {
            // Khôi phục chuỗi tìm kiếm
            stringSearch = savedInstanceState.getString("stringSearch", "");
            binding.edtFind.setText(stringSearch);
            // Khôi phục danh sách phim
            listMovie = (ArrayList<Result>) savedInstanceState.getSerializable("listMovie");
            if (listMovie != null && !listMovie.isEmpty()) {
                binding.imgNoneSearch.setVisibility(View.INVISIBLE); // ẩn ảnh nếu có kết quả
                mAdapter.updateData(listMovie);
            }
        }

    }

    private void initView() {
        binding.imgSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils keyBoardUtils = new KeyBoardUtils(getContext());
                stringSearch = binding.edtFind.getText().toString();

                if (!binding.edtFind.getText().toString().isEmpty()) {
                    getMovie(stringSearch);
                }
                keyBoardUtils.hideKeyboard(v);
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            if (getActivity() instanceof onBack) {
                ((onBack) getActivity()).onBack();
            }
        });
        binding.popup.setOnClickListener(v -> {
            showPopup("Tìm kiếm film mà bạn muốn");
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
        if (listMovie == null) {
            listMovie = new ArrayList<>();
        }
        mAdapter = new SearchMovieAdapter(listMovie, this);
        binding.imgNoneSearch.setVisibility(View.VISIBLE);

        binding.rcvSearchMovie.setAdapter(mAdapter);
        binding.rcvSearchMovie.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void getMovie(String input) {
        moviePresenter.getSearchMovie(input);
    }

    @Override
    public void getMovieSuccess(BestMovieRespone response) {
        listMovie.clear();
        binding.imgNoneSearch.setVisibility(View.INVISIBLE);// ẩn ảnh cho đẹp thôi :))) make color
        mAdapter.updateData((ArrayList<Result>) response.getResults());

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation);
        binding.rcvSearchMovie.setLayoutAnimation(layoutAnimationController);

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
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("stringSearch", stringSearch);
        outState.putSerializable("listMovie", listMovie);
    }
    private void showPopup(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.layout_popup, null);
        TextView textView = popupView.findViewById(R.id.textView5);
        textView.setText(text);
        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
        builder.setView(popupView);

        AlertDialog alertDialog = builder.create();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
        layoutParams.gravity = Gravity.TOP ;  // Change this to the desired position
        layoutParams.y = 30;  // Y position
        alertDialog.getWindow().setAttributes(layoutParams);
        alertDialog.show();
        // Apply the custom layout parameters
    }
    public interface onBack {
        void onBack();
    }
}