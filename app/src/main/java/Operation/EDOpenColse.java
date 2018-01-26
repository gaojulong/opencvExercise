package Operation;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by julong on 2018/1/24.
 */

public class EDOpenColse {
    /**
     *
     * @param command erod膨胀操作，dilate腐蚀操作
     * @param bitmap  处理后bitmap
     * @return
     */
    public static Bitmap erodOrDilate(String command ,Bitmap bitmap){
        Mat src=new Mat();
        Mat dst=new Mat();
        Utils.bitmapToMat(bitmap,src);
        //参数一代表结构元素矩形，二 3*3的矩形，三 中心点
        Mat strElement= Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(3,3),new Point(-1,-1));
        //膨胀操作
        if ("erod".equals(command)){
        Imgproc.erode(src,dst,strElement,new Point(-1,-1),1);
        }
        //腐蚀操作
        if ("dilate".equals(command)){
            Imgproc.dilate(src,dst,strElement,new Point(-1,-1),1);
        }
        Utils.matToBitmap(dst,bitmap);
        src.release();
        dst.release();
        return bitmap;
    }
    /**
     * 开操作 先腐蚀再膨胀
     * 可以去掉小的对象
     *
     * 闭操作 先膨胀在腐蚀
     */
    public static  Bitmap openOrClose(String command ,Bitmap bitmap){

        Mat src=new Mat();
        Mat dst=new Mat();
        Utils.bitmapToMat(bitmap,src);
        //灰度处理
        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGRA2GRAY);
        //二值化处理，自动查找阈值
        Imgproc.threshold(src,src,0,255,Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);

        //参数一代表结构元素矩形，二 3*3的矩形，三 中心点
        Mat strElement= Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(3,3),new Point(-1,-1));
        //开操作
        if ("open".equals(command)){
            Imgproc.morphologyEx(src,dst,Imgproc.MORPH_OPEN,strElement);
        }
        //闭操作
        if ("close".equals(command)){
            Imgproc.morphologyEx(src,dst,Imgproc.MORPH_CLOSE,strElement);
        }
        Utils.matToBitmap(dst,bitmap);
        src.release();
        dst.release();
        return bitmap;
    }
}
