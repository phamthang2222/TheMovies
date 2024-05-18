package vn.phamthang.themovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.ultis.Constant;

public class WhisListMovieAdapter extends RecyclerView.Adapter<WhisListMovieAdapter.WhisListMovieViewHolder> {
    private ArrayList<Result> mListMovie;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public WhisListMovieAdapter(ArrayList<Result> mListSearchMovie, OnItemClickListener onItemClickListener) {
        this.mListMovie = mListSearchMovie;
        this.onItemClickListener = onItemClickListener;
    }
    public void updateData(ArrayList<Result> ListMovie) {
        this.mListMovie.clear();
        this.mListMovie.addAll(ListMovie);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public WhisListMovieAdapter.WhisListMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_movie_in_list_search,parent,false);
        return new WhisListMovieViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull WhisListMovieAdapter.WhisListMovieViewHolder holder, int position) {
        Result movie = mListMovie.get(position);

        int id = movie.getId();
        int time = (int) (movie.getPopularity()/1);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvCalendar.setText(movie.getReleaseDate());
        holder.tvTime.setText( time+" Minutes");
        holder.tvRating.setText(movie.getVoteAverage()+"");
//        holder.tvGenre.setText(movie.getTitle());
        Glide.with(mContext)
                .load(Constant.convertLinkImage(movie.getPosterPath()))
                .transform(new CenterCrop(), new RoundedCorners(30)) // crop and border
                .into(holder.imgMovie);
        holder.itemSearchMovie.setOnClickListener(v -> onItemClickListener.onItemClick(id));
    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }

    public class WhisListMovieViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvCalendar, tvTime,tvRating,tvGenre;
        ImageView imgMovie;
        ConstraintLayout itemSearchMovie;
        public WhisListMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleMovie);
            tvCalendar = itemView.findViewById(R.id.tvCalenderMovie);
            tvTime = itemView.findViewById(R.id.tvTimeMovie);
            tvRating = itemView.findViewById(R.id.tvRatingMovie);
            tvGenre = itemView.findViewById(R.id.tvGenerMovie);
            imgMovie = itemView.findViewById(R.id.imgMovieInSearch);
            itemSearchMovie = itemView.findViewById(R.id.itemSearchMovie);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int idMovie);
    }
}