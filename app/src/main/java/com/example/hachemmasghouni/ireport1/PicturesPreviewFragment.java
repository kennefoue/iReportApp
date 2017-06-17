package com.example.hachemmasghouni.ireport1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class PicturesPreviewFragment extends Fragment {

    ArrayList<byte[]> imagesToPreviewList = new ArrayList<>();
    ImageView previewImageView;
    private int imageIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imagesToPreviewList.add(getArguments().getByteArray("img1"));
        imagesToPreviewList.add(getArguments().getByteArray("img2"));
        imagesToPreviewList.add(getArguments().getByteArray("img3"));
        View v =  inflater.inflate(R.layout.fragment_pictures_preview, container, false);

        previewImageView = (ImageView) v.findViewById(R.id.iv_pic_preview);
        previewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewImageView.setImageBitmap(getBitmapFromArrayByte(imagesToPreviewList.get(imageIndex)));
                if (imageIndex == 2) {
                    imageIndex = 0;
                } else {
                    imageIndex++;
                }
            }
        });

        return v;
    }

    public Bitmap getBitmapFromArrayByte(byte[] data) {
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bmp;
    }


}
