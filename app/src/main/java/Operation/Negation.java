package Operation;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by julong on 2018/1/7.
 * 像素取反操作
 */

public class Negation {


    /**
     * 图形灰度处理
     * @param bitmap
     * @return
     */
    public static Bitmap Gray(Bitmap bitmap){
        Mat src=new Mat();
        Utils.bitmapToMat(bitmap,src);

        Mat gray=new Mat();
        Imgproc.cvtColor(src,gray,Imgproc.COLOR_BGRA2GRAY);

        Utils.matToBitmap(gray,bitmap);
        src.release();
        gray.release();
        return bitmap;
    }

    //强力推荐使用
    /**
     * 使用OpenCV提供的方法实现像素取反
     * @param bitmap
     * @return
     */
    public static Bitmap bitNedation(Bitmap bitmap,Context context){
        Mat src=new Mat();
        Utils.bitmapToMat(bitmap,src);
        //opencv提供像素取反
        long starttime=System.currentTimeMillis();//开始时间
        Core.bitwise_not(src,src);
        //结束时间
        long time =System.currentTimeMillis()-starttime;
        Toast.makeText(context,"耗时:"+time+"毫秒",Toast.LENGTH_SHORT).show();
        Utils.matToBitmap(src,bitmap);
        src.release();
        return bitmap;
    }
    /**
     * 对bitmap直接进行取反
     * @param bitmap
     * @return
     */
    public static Bitmap bitmapNegation(Bitmap bitmap,Context context){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        int[] pixels=new int[width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
        int indx=0;
        int a=0,r=0,g=0,b=0;
        //计算耗时
        long starttime=System.currentTimeMillis();
        for(int row=0;row<height;row++){
            indx=row*width;
            for (int col=0;col<width;col++){
                int pixel=pixels[indx];
                a=(pixel>>24)&0xff;
                r=(pixel>>16)&0xff;
                g=(pixel>>8)&0xff;
                b=pixel&0xff;
                //像素取反
                r=255-r;
                g=255-g;
                b=255-b;
                //重新赋给像素
                pixel=((a&0xff)<<24)|((r&0xff)<<16)|((g&0xff)<<8)|(b&0xff);
                pixels[indx]=pixel;
                indx++;
            }
        }
        long time =System.currentTimeMillis()-starttime;
        Toast.makeText(context,"耗时:"+time+"毫秒",Toast.LENGTH_SHORT).show();
        bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels,0,width,0,0,width,height);
        return bitmap;
    }


    /**
     * 把bitmap转换成Mat类型src
     * src进行灰度处理后赋给gray
     * gray进行像素取反
     * @param bitmap
     * @return
     */
    public static Bitmap matGrayNegation(Bitmap bitmap){
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGRA2GRAY);
        int width = gray.cols();
        int height = gray.rows();
        byte[] data = new byte[width*height];
        gray.get(0, 0, data);
        for(int row=0; row<height; row++) {
            for(int col=0; col<width; col++) {
                data[row*width + col] = (byte)(~data[row*width+col]);
            }
        }
        gray.put(0, 0, data);
        Utils.matToBitmap(gray, bitmap);
        gray.release();
        src.release();
        return bitmap;
    }

    /**不推荐使用过多使用jni速度慢
     * 把bitmap转换成Mat类型src像素进行取反操作
     * @param bitmap
     * @return
     */
    public static Bitmap matNegation(Bitmap bitmap, Context context){
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);
        int width = src.cols();
        int height = src.rows();
        //cnum色彩通道，一般只有argb四个通道；
        int cnum=src.channels();
        byte[] bgra=new byte[cnum];
        //计算用时
        long starttime=System.currentTimeMillis();
        for(int row=0; row<height; row++) {
            for(int col=0; col<width; col++) {
                //获取每个像素点和他的通道
                src.get(row,col,bgra);
                //每个通道进行取反
                for (int i=0;i<cnum;i++){
                    bgra[i]=(byte)((255-bgra[i]&0xff));
                }
                src.put(row, col, bgra);
            }
        }
        long time=System.currentTimeMillis()-starttime;
        Toast.makeText(context,"耗时:"+time+"毫秒",Toast.LENGTH_SHORT).show();
        Utils.matToBitmap(src, bitmap);
        src.release();
        return bitmap;
    }


}
