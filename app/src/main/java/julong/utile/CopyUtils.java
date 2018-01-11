package julong.utile;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

/**
 * Created by julong on 2018/1/10.
 */

public class CopyUtils {
    /**
     * 不适合mat操作，还未找到原因
     * copy Bitmap
     */
    public static Bitmap copyBitmap(Bitmap bitmap){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        int[] pixels=new int[width*height];
        //获取像素点
        bitmap.getPixels(pixels,0,width,0,0,width,height);
        //设置的bp类型
        Bitmap bp=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        //赋值给新的bitmap对象
        bp.setPixels(pixels,0,width,0,0,width,height);
        return bp;
    }

    /**
     *
     * @param bitmap
     * @return
     */
    public static Bitmap copyMatBitmap(Bitmap bitmap){
        Mat src=new Mat();
        Utils.bitmapToMat(bitmap,src);
        Utils.matToBitmap(src,bitmap);
        return bitmap;
    }

}
