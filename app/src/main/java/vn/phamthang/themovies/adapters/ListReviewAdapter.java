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
import vn.phamthang.themovies.objects.Review.Result;
import vn.phamthang.themovies.ultis.Constant;

public class ListReviewAdapter extends RecyclerView.Adapter<ListReviewAdapter.ListReviewViewHolder> {

    private ArrayList<Result> mListReview;
    private Context mContext;

    public ListReviewAdapter(ArrayList<Result> mListReview) {
        this.mListReview = mListReview;
    }
    public void updateData(ArrayList<Result> listReview){
        this.mListReview.clear();
        this.mListReview.addAll(listReview);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListReviewAdapter.ListReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_reviews, parent, false);
        return new ListReviewViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListReviewAdapter.ListReviewViewHolder holder, int position) {
        Result result = mListReview.get(position);

        if (result.getAuthorDetails().getAvatarPath() != null) {
            Glide.with(mContext)
                    .load(Constant.convertLinkImage(result.getAuthorDetails().getAvatarPath()))
                    .transform(new CenterCrop(), new RoundedCorners(50)) // crop and border
                    .into(holder.imgUserReview);
        } else {
            holder.imgUserReview.setImageResource(R.drawable.ic_user);
        }
        if(result.getAuthorDetails().getRating() != null){
            holder.tvRating.setText(result.getAuthorDetails().getRating()+"");
        }else{
            holder.tvRating.setText("Not\nRating");
        }
        holder.tvUserReview.setText(result.getAuthorDetails().getUsername()+"");
        holder.tvComment.setText(result.getContent());

    }

    @Override
    public int getItemCount() {
        return mListReview.size();
    }

    public class ListReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUserReview;
        TextView tvRating, tvUserReview, tvComment;

        public ListReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUserReview = itemView.findViewById(R.id.imgUserReview);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvUserReview = itemView.findViewById(R.id.tvUserReview);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
