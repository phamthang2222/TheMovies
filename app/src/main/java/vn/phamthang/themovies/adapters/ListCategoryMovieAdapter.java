package vn.phamthang.themovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.presenter.MoviePresenter;
import vn.phamthang.themovies.ultis.Constant;

public class ListCategoryMovieAdapter extends RecyclerView.Adapter<ListCategoryMovieAdapter.NowPlayingMovieViewHolder> {
    private MoviePresenter mMoviePresenter;

    private ArrayList<Result> mListMovie;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ListCategoryMovieAdapter(ArrayList<Result> mListMovie, OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
        this.mListMovie = mListMovie;
    }
    public void updateData(ArrayList<Result> ListMovie){
        this.mListMovie.clear();
        this.mListMovie.addAll(ListMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListCategoryMovieAdapter.NowPlayingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_nowplayingmovie,parent,false);
        return new NowPlayingMovieViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoryMovieAdapter.NowPlayingMovieViewHolder holder, int position) {
        Result movie = mListMovie.get(position);
        int id = movie.getId();
        String url = movie.getPosterPath();
        if(url!=null){
            Glide.with(context)
                    .load(Constant.convertLinkImage(url))
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.imgNowPlayingMovie);
        }else{
            Glide.with(context)
                    .load(R.drawable.empty_image)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.imgNowPlayingMovie);
        }

        holder.imgNowPlayingMovie.setOnClickListener(v -> {
            onItemClickListener.onItemClick(id);
        });
    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }

    public class NowPlayingMovieViewHolder extends RecyclerView.ViewHolder{
        ImageView imgNowPlayingMovie;
        public NowPlayingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNowPlayingMovie = itemView.findViewById(R.id.imgNowPlayingMovie);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int idMovie);
    }
}
