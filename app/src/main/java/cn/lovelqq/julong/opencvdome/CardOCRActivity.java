package cn.lovelqq.julong.opencvdome;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Operation.Blurclass;
import Operation.Negation;
import julong.utile.CopyUtils;

public class CardOCRActivity extends AppCompatActivity implements View.OnClickListener{

    //5
    private Button bt_rec,bt_MatNegation,bt_BitmapNegation,bt_gray,bt_Mat_bit;
    private Button bt_meanBlur,bt_GaussBlur,bt_biBlur;//模糊
    private Button bt_recBitmap;//恢复图片
    private TextView tv_content;//识别结果显示
    private ImageView imageView;//图片显示
    private Uri fileUri;
    private  Bitmap copyBitmap;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_card_ocr);
        init();
        bitmap=getBitmap();
    }

    private Bitmap getBitmap(){
        Bitmap bitmap=null;
        fileUri = (Uri)this.getIntent().getParcelableExtra("PICTURE-URL");
        if(fileUri != null) {
            bitmap=displaySelectedImage();
        }
        return bitmap;
    }
    /**
     * 加载控件
     */
    private void init(){
        bt_rec=findViewById(R.id.bt_reco);
        bt_BitmapNegation=findViewById(R.id.bt_bitmapNegation);
        bt_MatNegation=findViewById(R.id.bt_matNegation);
        bt_gray=findViewById(R.id.bt_gray);
        bt_Mat_bit=findViewById(R.id.bt_mat_bitNegation);
        bt_GaussBlur=findViewById(R.id.bt_GaussBlur);
        bt_meanBlur=findViewById(R.id.bt_meanBlur);
        bt_recBitmap=findViewById(R.id.bt_resBitmap);
        bt_biBlur=findViewById(R.id.bt_biBlur);


        tv_content=findViewById(R.id.tv_jieguo);
        imageView=findViewById(R.id.image_view);
        //设置监听
        bt_rec.setOnClickListener(this);
        bt_BitmapNegation.setOnClickListener(this);
        bt_MatNegation.setOnClickListener(this);
        bt_gray.setOnClickListener(this);
        bt_Mat_bit.setOnClickListener(this);
        bt_GaussBlur.setOnClickListener(this);
        bt_meanBlur.setOnClickListener(this);
        bt_recBitmap.setOnClickListener(this);
        bt_biBlur.setOnClickListener(this);

    }

    /**
     * 获取到图片转换成bitmap
     * @return
     */
    private Bitmap displaySelectedImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileUri.getPath(), options);
        int w = options.outWidth;
        int h = options.outHeight;
        int inSample = 1;
        if(w > 1000 || h > 1000) {
            while(Math.max(w/inSample, h/inSample) > 1000) {
                inSample *=2;
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSample;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bm = BitmapFactory.decodeFile(fileUri.getPath(), options);
        imageView.setImageBitmap(bm);
        return bm;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_reco:
                Toast.makeText(this.getApplication(),"识别按钮被点击",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_resBitmap://复位像素
                bitmap=getBitmap();
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_gray://灰度处理
                bitmap=Negation.Gray(bitmap);
                imageView.setImageBitmap(bitmap);
                break;
            case  R.id.bt_bitmapNegation://对bitmap像素取反
                bitmap= Negation.bitmapNegation(bitmap,getApplication());
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_matNegation://对src像素点取反，速度最慢
                bitmap= Negation.matNegation(bitmap,getApplication());
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_mat_bitNegation://使用OpenCV提供的速度最快
                bitmap=Negation.bitNedation(bitmap,getApplication());
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_meanBlur://均值模糊
                bitmap= Blurclass.meanBlur(bitmap);
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_GaussBlur://高斯模糊
                bitmap=Blurclass.gaussBlur(bitmap);
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_biBlur://双边模糊and锐化
                bitmap=Blurclass.biBlur(bitmap);
                imageView.setImageBitmap(bitmap);
                break;

        }

    }
}
