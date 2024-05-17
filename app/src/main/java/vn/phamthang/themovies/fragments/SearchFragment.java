package vn.phamthang.themovies.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.databinding.FragmentSreachBinding;

public class SearchFragment extends Fragment {

    FragmentSreachBinding binding;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSreachBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}