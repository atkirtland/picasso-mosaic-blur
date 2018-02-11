package com.aaronkirtland.mosaicblur;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
             int space = 20;
            int x = 130, y = 70, w = 150, h = 150;
            w = w + space - w % space;
            h = h + space - h % space;

            Bitmap bitmap2 = bitmap.copy(bitmap.getConfig(), true);
            for (int i = 0; i < w/space; ++i){
                for (int j = 0; j < h/space; ++j) {
                    int n = space*space;
                    int r = 0, g = 0, b = 0;
                    int[] pixels = new int[space*space];
                    bitmap.getPixels(pixels, 0, space, x+i*space, y+j*space, space, space);
                    for (int pixel : pixels) {
                        r += Color.red(pixel);
                        g += Color.green(pixel);
                        b += Color.blue(pixel);
                    }
                    Arrays.fill(pixels, Color.rgb(r/n, g/n, b/n));
                    bitmap2.setPixels(pixels, 0, space, x+i*space, y+j*space, space, space);
                }
            }
            ImageView iv = findViewById(R.id.lion);
            iv.setImageBitmap(bitmap2);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Picasso.with(this).load(R.drawable.lionimage).into(target);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onDestroy() {
        Picasso.with(this).cancelRequest(target);
        super.onDestroy();
    }
}
