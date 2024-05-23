package vn.phamthang.themovies.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


import java.util.ArrayList;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.objects.Result;
import vn.phamthang.themovies.ultis.Constant;

public class SpecialMovieAdapter extends RecyclerView.Adapter<SpecialMovieAdapter.MostMovieViewHolder>{
    private ArrayList<Result> mListMovie;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public SpecialMovieAdapter(ArrayList<Result> mListMovie, OnItemClickListener listener) {
        this.mListMovie = mListMovie;
        this.onItemClickListener = listener;
    }

    public void updateData(ArrayList<Result> ListMovie) {
        this.mListMovie.clear();
        this.mListMovie.addAll(ListMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpecialMovieAdapter.MostMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_bestmovie, parent, false);
        return new MostMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialMovieAdapter.MostMovieViewHolder holder, int position) {
        Result movie = mListMovie.get(position);

        int id = movie.getId();
//        Glide.with(context)
//                .load(Constant.convertLinkImage(movie.getPosterPath()))
//                .transform(new CenterCrop(), new RoundedCorners(30)) // crop and border
//                .into(holder.imgMovie);
        Glide.with(context)
                .asGif()
                .load(R.drawable.loading)
                .into(holder.imgMovie);
        loadImage(context,Constant.convertLinkImage(movie.getPosterPath()),holder.imgMovie);
        holder.tvSTT.setText(position+1 + "");
        holder.imgMovie.setOnClickListener(v -> {
            onItemClickListener.onItemClick(id);
        });

    }

    @Override
    public int getItemCount() {
        return mListMovie != null ? mListMovie.size() : 0;
    }



    public class MostMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView tvSTT;

        public MostMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.imgMovie);
            tvSTT = itemView.findViewById(R.id.tvSTT);
        }
    }

    //tạo interface để lắng nghe sự kiện trả về từ adapter
    public interface OnItemClickListener {
        void onItemClick(int idMovie);
    }
    private void loadImage(Context context, String imageUrl,ImageView imageView){
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
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
}
