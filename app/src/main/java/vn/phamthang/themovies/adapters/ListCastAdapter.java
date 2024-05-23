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
import vn.phamthang.themovies.objects.Cast.Cast;
import vn.phamthang.themovies.ultis.Constant;

public class ListCastAdapter extends RecyclerView.Adapter<ListCastAdapter.ListCastViewHolder> {

    private ArrayList<Cast> mListCast;
    private Context mContext;

    public ListCastAdapter(ArrayList<Cast> mListCast) {
        this.mListCast = mListCast;
    }

    public void updateData(ArrayList<Cast> listCast) {
        this.mListCast.clear();
        this.mListCast.addAll(listCast);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListCastAdapter.ListCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cast, parent, false);
        return new ListCastViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCastAdapter.ListCastViewHolder holder, int position) {
        Cast cast = mListCast.get(position);
        if (cast.getProfilePath() != null) {
            Glide.with(mContext)
                    .load(Constant.convertLinkImage(cast.getProfilePath()))
                    .transform(new CenterCrop(), new RoundedCorners(500))
                    .into(holder.imgCasting);
        } else {
            holder.imgCasting.setImageResource(R.drawable.ic_casting);
        }

        holder.tvCastingName.setText(cast.getName());
        holder.tvCharacter.setText("Role: " + cast.getCharacter());
    }

    @Override
    public int getItemCount() {
        return mListCast.size();
    }

    public class ListCastViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCasting;
        TextView tvCastingName, tvCharacter;

        public ListCastViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCasting = itemView.findViewById(R.id.imgCasting);
            tvCastingName = itemView.findViewById(R.id.tvCastingName);
            tvCharacter = itemView.findViewById(R.id.tvCharacter);
        }
    }
}
