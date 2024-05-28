package vn.phamthang.themovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;

import vn.phamthang.themovies.CustomToast.SuccessfulToast;
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
import vn.phamthang.themovies.objects.User.User;
import vn.phamthang.themovies.presenter.DetailMoviePresenter;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.ultis.KeyBoardUtils;
import vn.phamthang.themovies.ultis.MessageEvent;
import vn.phamthang.themovies.view.DetailActivity;
import vn.phamthang.themovies.view.LoginActivity;
import vn.phamthang.themovies.view.SignUpActivity;

public class HomeFragment extends Fragment implements IMovieView, IMovieDetailView, SpecialMovieAdapter.OnItemClickListener {

    private MoviePresenter mMoviePresenter;
    private DetailMoviePresenter mDetailMoviePresenter;
    private ArrayList<Result> mListMovie;
    private SpecialMovieAdapter specialMovieAdapter;
    private String edtSearch;
    private String email = "";
    private ArrayList<User> userList = new ArrayList<>();


    FragmentHomeBinding binding;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("HomeFragment", "onAttach");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("HomeFragment", "onResume: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("HomeFragment", "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("HomeFragment", "onPause: ");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("HomeFragment", "onDetach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("HomeFragment", "onDestroy: ");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
//        initDataUser();
        initUserNameCurent();
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
                KeyBoardUtils keyBoardUtils = new KeyBoardUtils(getContext());
                EventBus.getDefault().post(new MessageEvent(edtSearch));

                if (getActivity() instanceof OnFragmentInteractionListener) {
                    ((OnFragmentInteractionListener) getActivity()).onSearch();
                }

                keyBoardUtils.hideKeyboard(v);
            }
        });
        binding.imgLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(),
                    LoginActivity.class));
            Animatoo.INSTANCE.animateDiagonal(getContext());
            getActivity().finish();
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
        Animatoo.INSTANCE.animateZoom(getActivity());

    }

    @Override
    public void getDetailMovieError(String message) {
    }

    private void initDataUser() {
        getListUser();
        String userName = "Loading...";
        for (User user : userList) {
            if (email.equals(user.getEmail())) {
                userName = user.getName();
            }
        }
        binding.tvUserName.setText("Hi, " + userName);
    }

    public void getListUser() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USER");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        User user = childSnapshot.getValue(User.class);
                        userList.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initUserNameCurent(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference myRef = database.getReference("USER");
            Query query = myRef.orderByChild("idUser").equalTo(userId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.child(userId).child("name").getValue(String.class);
                        binding.tvUserName.setText("Hi, "+username);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    public interface OnFragmentInteractionListener {
        void onSearch();
    }

}