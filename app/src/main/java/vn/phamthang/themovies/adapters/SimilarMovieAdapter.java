package vn.phamthang.themovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.ultis.Constant;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieViewHolder> {
    private ArrayList<Result> mListSimilarMovie;
    private Context mContext;

    public SimilarMovieAdapter(ArrayList<Result> mListSimilarMovie) {
        this.mListSimilarMovie = mListSimilarMovie;
    }
    public void updateData(ArrayList<Result> mListSimilarMovie){
        this.mListSimilarMovie.clear();
        this.mListSimilarMovie.addAll(mListSimilarMovie);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SimilarMovieAdapter.SimilarMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_similar_movie,parent,false);
        return new SimilarMovieViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMovieAdapter.SimilarMovieViewHolder holder, int position) {
        Result similarMovie = mListSimilarMovie.get(position);
        String url = similarMovie.getPosterPath();
        if(url.isEmpty()){
            holder.imgBgSimilarMovie.setImageResource(R.drawable.empty_image);
        }else{
            Glide.with(mContext)
                    .load(Constant.convertLinkImage(url))
                    .transform(new CenterCrop(),new RoundedCorners(10))
                    .into(holder.imgBgSimilarMovie);
        }
        holder.tvTitleSimilarMovie.setText(similarMovie.getTitle());
    }

    @Override
    public int getItemCount() {
        return mListSimilarMovie.size();
    }

    public class SimilarMovieViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBgSimilarMovie;
        TextView tvTitleSimilarMovie;
        public SimilarMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBgSimilarMovie = itemView.findViewById(R.id.imgBgSimilarMovie);
            tvTitleSimilarMovie = itemView.findViewById(R.id.tvTitleSimilarMovie);
        }
    }
}
