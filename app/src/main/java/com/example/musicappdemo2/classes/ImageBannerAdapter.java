package com.example.musicappdemo2.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.musicappdemo2.R;

import java.util.List;

public class ImageBannerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageBanner> mListImgBanner;

    public ImageBannerAdapter(Context mContext, List<ImageBanner> mListImgBanner) {
        this.mContext = mContext;
        this.mListImgBanner = mListImgBanner;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_layout_img_banner, container, false);
        ImageView imageViewBanner = view.findViewById(R.id.img_banner);

        ImageBanner imageBanner = mListImgBanner.get(position);
        if(imageBanner != null){
            Glide.with(mContext).load(imageBanner.getResourceId()).into(imageViewBanner);
        }

        //Add view to ViewGroup
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {

        if(mListImgBanner != null){
            return mListImgBanner.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        //remove view
        container.removeView((View) object);
    }
}
