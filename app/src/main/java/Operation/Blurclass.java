package Operation;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by julong on 2018/1/10.
 */

public class Blurclass {
    /**
     * 均值模糊处理
     * @param bitmap
     * @return
     */
    public  static Bitmap meanBlur(Bitmap bitmap){
        Mat src = new Mat();
        Mat dst = new Mat();
        //bitmap转换成Mat类型
        Utils.bitmapToMat(bitmap,src);
        //均值模糊Size(必须为奇数)，Point中心点，borderType边缘处理方法第4个对应
        Imgproc.blur(src,dst,new Size(5,5),new Point(-1,-1),4);
        Utils.matToBitmap(dst,bitmap);
        src.release();
        dst.release();
        return bitmap;
    }

    /**
     * 高斯模糊
     * @param bitmap
     * @return
     */
    public static Bitmap gaussBlur(Bitmap bitmap){
        Mat src=new Mat();
        Utils.bitmapToMat(bitmap,src);
        Mat dst=new Mat();

        Imgproc.GaussianBlur(src,dst,new Size(5,5),0,0,4);
        Utils.matToBitmap(dst,bitmap);
        src.release();
        dst.release();
        return bitmap;
    }

    /**
     * 双边模糊
     * @param bitmap
     * @return
     */
    public static Bitmap biBlur(Bitmap bitmap,int i){
        Mat src=new Mat();
        Utils.bitmapToMat(bitmap,src);
        //转为三通道的，否则程序将会报错
        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGRA2BGR);
        Mat dst = new Mat();
        //双边模糊
        Imgproc.bilateralFilter(src,dst,15,i,15,4);
        Utils.matToBitmap(dst,bitmap);
        //锐化
        Mat kernel=new Mat(5,5, CvType.CV_16S);
        kernel.put(0,0,0,-1,0,-1,5,-1,0,-1,0);
        Imgproc.filter2D(dst,dst,-1,kernel,new Point(-1,-1),0.0,4);
        //清除
        kernel.release();
        src.release();
        dst.release();
        return bitmap;
    }
}
