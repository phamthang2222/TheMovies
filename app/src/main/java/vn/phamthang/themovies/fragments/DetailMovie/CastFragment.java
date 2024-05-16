package vn.phamthang.themovies.fragments.DetailMovie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.phamthang.themovies.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastFragment extends Fragment {


    public CastFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cast, container, false);
    }
}