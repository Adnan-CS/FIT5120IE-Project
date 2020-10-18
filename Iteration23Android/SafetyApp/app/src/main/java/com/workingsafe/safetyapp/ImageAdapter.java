package com.workingsafe.safetyapp;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageAdapter extends PagerAdapter {

    Context ctx;
    private int[] sliderImage = new int[]{
            R.drawable.physical, R.drawable.verbal, R.drawable.hostile, R.drawable.visual, R.drawable.quid
    };


    ImageAdapter(Context context){
        this.ctx = context;

    }


    @Override
    public int getCount() {
        return sliderImage.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==((ImageView)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderImage[position]);
        ((ViewPager) container).addView(imageView,0);


        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView)object);
    }
}
