package vn.phamthang.themovies.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.ultis.Constant;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder> {

    private ArrayList<Result> mListSearchMovie;
    private Context mContext;
    private OnItemClickListener onItemClickListener;


    public SearchMovieAdapter(ArrayList<Result> mListSearchMovie, OnItemClickListener onItemClickListener) {
        this.mListSearchMovie = mListSearchMovie;
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<Result> ListMovie) {
        this.mListSearchMovie.clear();
        this.mListSearchMovie.addAll(ListMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchMovieAdapter.SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_movie_in_list_search, parent, false);
        return new SearchMovieViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.SearchMovieViewHolder holder, int position) {
        Result movie = mListSearchMovie.get(position);

        int id = movie.getId();
        int time = (int) (movie.getPopularity() / 1);
        String url = movie.getPosterPath();

        holder.tvTitle.setText(movie.getTitle());
        holder.tvCalendar.setText(movie.getReleaseDate());
        holder.tvTime.setText(time + " Minutes");
        holder.tvRating.setText(movie.getVoteAverage() + "");
//        holder.tvGenre.setText(movie.getTitle());
        Glide.with(mContext)
                .asGif()
                .load(R.drawable.loading)
                .into(holder.imgMovie);
        if (url != null) {
            loadGifPlaceholderAndImage(mContext, Constant.URL_LOADING_GIF, Constant.convertLinkImage(movie.getPosterPath()), holder.imgMovie);
        } else {
//            Glide.with(mContext)
//                    .load(R.drawable.empty_image)
//                    .transform(new CenterCrop(), new RoundedCorners(30)) // crop and border
//                    .into(holder.imgMovie);
            loadGifPlaceholderAndImage(mContext, Constant.URL_LOADING_GIF, String.valueOf(R.drawable.empty_image), holder.imgMovie);

        }

        holder.itemSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListSearchMovie.size();
    }

    public class SearchMovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCalendar, tvTime, tvRating, tvGenre;
        ImageView imgMovie;
        ConstraintLayout itemSearchMovie;

        public SearchMovieViewHolder(@NonNull View itemView) {
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

    private void loadGifPlaceholderAndImage(Context context, String placeholderUrl, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .asGif()
                .transform(new CenterCrop(), new RoundedCorners(30)) // crop and border
                .load(placeholderUrl)
                .into(new CustomTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        Glide.with(context)
                                .load(imageUrl)
                                .apply(new RequestOptions()
                                        .placeholder(resource)
                                        .transform(new CenterCrop(), new RoundedCorners(30)) // crop and border
                                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        // Xử lý khi load ảnh thất bại (nếu cần)
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        // Ảnh thực đã tải xong và được hiển thị
                                        return false;
                                    }
                                })
                                .into(imageView);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
