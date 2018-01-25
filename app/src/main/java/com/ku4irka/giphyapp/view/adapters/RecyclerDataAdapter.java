package com.ku4irka.giphyapp.view.adapters;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.giphy.sdk.core.models.Media;
import com.ku4irka.giphyapp.databinding.RecyclerDataItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.ku4irka.giphyapp.util.Utils.showGifImage;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onClick(Media media);
    }

    private static final String TAG = RecyclerDataAdapter.class.getSimpleName();

    private OnItemClickListener itemClickListener;

    private List<Media> mediaList;

    public RecyclerDataAdapter() {
        mediaList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder;

        RecyclerDataItemBinding binding = RecyclerDataItemBinding
                .inflate(inflater, parent, false);
        holder = new MainViewHolder(binding.getRoot());

        return holder;
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Media media = getItem(position);

        MainViewHolder mainViewHolder = (MainViewHolder) holder;
        RecyclerDataItemBinding binding = mainViewHolder.binding;

        String imageSrc = media.getImages().getFixedWidthDownsampled().getGifUrl();
        binding.setImage(imageSrc);

        binding.ivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(media);
                }
            }
        });
    }

    private Media getItem(int position) {
        return mediaList.get(position);
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String src) {
        if (src != null) {
            showGifImage(imageView, src);
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        RecyclerDataItemBinding binding;

        MainViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public void setData(List<Media> mediaList) {
        this.mediaList = mediaList;
        notifyDataSetChanged();
    }

    public void addItemsToList(List<Media> mediaList) {
        mediaList.addAll(mediaList);
        notifyItemRangeInserted(this.mediaList.size() - mediaList.size(), this.mediaList.size());
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void clearAdapter() {
        if (mediaList != null) {
            mediaList.clear();
        }
        notifyDataSetChanged();
    }
}