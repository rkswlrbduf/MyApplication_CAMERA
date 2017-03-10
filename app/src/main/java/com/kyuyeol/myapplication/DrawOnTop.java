package com.kyuyeol.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by kyuyeol on 2017-03-10.
 */

public class DrawOnTop extends View {

    Resources r;
    Bitmap image;
    Bitmap copyImageL;
    Bitmap copyImageR;

    DisplayMetrics dm;
    int width;
    int height;

    public DrawOnTop(Context context) {
        super(context);

        r = context.getResources();

        dm = r.getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;

        Matrix matrix = new Matrix();
        matrix.postRotate(180);

        image = BitmapFactory.decodeResource(r, R.mipmap.rarroww);
        copyImageR = image.copy(Bitmap.Config.ARGB_8888,true);
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[width][height];

        for(int i=0;i<image.getHeight();i++) {
            for(int j=0;j<image.getWidth();j++) {
                pixels[i][j] = image.getPixel(j, i);

                int argb = 0;
                int A = (pixels[i][j] >> 24) & 0xff;
                int R = (pixels[i][j] >> 16) & 0xff;
                int G = (pixels[i][j] >> 8) & 0xff;
                int B = pixels[i][j]  & 0xff;

                //Log.d("TAG",A +" "+ R + " "+G +" "+ B);

                if(A < 200 || R >= 35 && G >= 35 && B >= 35) {
                    copyImageR.setPixel(j, i, Color.TRANSPARENT);
                    //Log.d("TAG", "TRANSPARENT");
                }
                else {
                    copyImageR.setPixel(j, i, pixels[i][j]);
                    //Log.d("TAG", "Non TRANSPARENT");
                }

            }
        }
        copyImageL = Bitmap.createBitmap(copyImageR,0,0,copyImageR.getWidth(),copyImageR.getHeight(),matrix,true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Rect srcL = new Rect(0,0,copyImageL.getWidth(),copyImageL.getHeight());
        Rect dstL = new Rect(width/16,height/2-200,width/8,height/2+200);

        Rect srcR = new Rect(0,0,copyImageR.getWidth(),copyImageR.getHeight());
        Rect dstR = new Rect(width - width/8,height/2-200,width - width/16,height/2+200);

        canvas.drawBitmap(copyImageR, srcR, dstR, null);
        canvas.drawBitmap(copyImageL, srcL, dstL, null);
    }
}
