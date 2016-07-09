package com.willhua.imageloader.Resize;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageResizer {

    private static final String TAG = "ImageResizer";
    
    private static final int TOO_BIG = 4;
    
    public ImageResizer(){
        
    }
    
    public Bitmap decodeSampleBitmapFromResource(Resources res, int resourceId, int reqWidth, int reqHeight){
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resourceId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resourceId, options);
    }
    
    public Bitmap decodeSampleBitmapFromStream(FileInputStream inputStream, int reqWidth, int reqHeight){
        FileDescriptor fd;
        try {
            fd = inputStream.getFD();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return decodeSampleBitmapFromFileDescriptor(fd, reqWidth, reqHeight);
    }
    
    private Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight){
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }
    
    
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        if(reqHeight == 0 || reqWidth == 0){
            return 1;
        }
        
        final int outWidth = options.outWidth;
        final int outHeight = options.outHeight;
        
        //如果小于两倍，那么压缩之后就可能不清晰，因为至少压缩2，所以不压缩。
        if(outHeight < 2 * reqHeight && outWidth < 2 * reqWidth){
            return 1;
        }
        
        int inSample = 1;
        
        while(outHeight / inSample > reqHeight && outWidth / inSample > reqWidth){
            inSample *= 2;
        }
        
        //这里考虑到一些可能长宽比悬殊的图片，那么综合内存与显示效果考虑，对压缩之后仍然超过某个比例的，继续压缩一半
        //这里能够保证，真实像素大小起码也有需要的一半
        if(outHeight / inSample > TOO_BIG * reqHeight && outWidth / inSample > reqWidth){
            inSample *= 2;
        } else if(outWidth / inSample > TOO_BIG * reqWidth && outHeight / inSample > reqHeight){
            inSample *= 2;
        }
        
        return inSample;
    }
}
