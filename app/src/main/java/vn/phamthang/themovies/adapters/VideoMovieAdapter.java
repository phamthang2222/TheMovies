package vn.phamthang.themovies.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.phamthang.themovies.R;
import vn.phamthang.themovies.objects.Video.Result;

public class VideoMovieAdapter extends RecyclerView.Adapter<VideoMovieAdapter.ViewHolder> {

    private ArrayList<Result> mListVideo;
    private Context mContext;

    public VideoMovieAdapter(ArrayList<Result> mListVideo) {
        this.mListVideo = mListVideo;
    }
    public void updateData(ArrayList<Result> ListMovie) {
        this.mListVideo.clear();
        this.mListVideo.addAll(ListMovie);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public VideoMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_video_trailer_movie,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result video = mListVideo.get(position);

        WebView webView = holder.webView;
        WebSettings webSettings = webView.getSettings();

        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        String videoId = video.getKey();
        String videoUrl = "https://www.youtube.com/embed/" + videoId;
        webView.loadUrl(videoUrl);
    }

    @Override
    public int getItemCount() {
        return mListVideo.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        WebView webView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.videoTrailer);
        }
    }
}
