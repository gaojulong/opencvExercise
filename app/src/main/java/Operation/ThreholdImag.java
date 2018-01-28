package Operation;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * 图形二值化
 * Created by julong on 2018/1/26.
 */

public class ThreholdImag {
    /**
     * 传入t值，来调节图形二值化
     * @param bitmap
     * @param t
     * @return
     */
    public static Bitmap threhold(Bitmap bitmap,int t){
        Mat src=new Mat();
        Mat dst=new Mat();
        Utils.bitmapToMat(bitmap,src);
        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGRA2GRAY);
        Imgproc.threshold(src,dst,t,255,Imgproc.THRESH_BINARY);
        Utils.matToBitmap(dst,bitmap);
        src.release();
        dst.release();
        return bitmap;
    }
}
