package cn.lovelqq.julong.opencvdome;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import Operation.Blurclass;
import Operation.EDOpenColse;
import Operation.Negation;
import julong.utile.CopyUtils;
import julong.utile.Dialog;

public class CardOCRActivity extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    //5
    private Button bt_rec,bt_MatNegation,bt_BitmapNegation,bt_gray,bt_Mat_bit;
    private Button bt_meanBlur,bt_GaussBlur,bt_biBlur;//模糊
    private Button bt_recBitmap;//恢复图片
    private Button bt_dilate,bt_erod,bt_open,bt_close;//膨胀和模糊
    private TextView tv_content,tv_SeekBar;//识别结果显示
    private SeekBar seekBar;
    private ImageView imageView;//图片显示
    private Uri fileUri;
    private Bitmap bitmap;
    private static String seekBarFalg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
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
        bt_erod=findViewById(R.id.bt_erod);
        bt_dilate=findViewById(R.id.bt_dilate);
        bt_open=findViewById(R.id.bt_open);
        bt_close=findViewById(R.id.bt_close);
        seekBar=findViewById(R.id.sk_SeekBar);
        tv_SeekBar=findViewById(R.id.tv_seekbar);


        tv_content=findViewById(R.id.tv_jieguo);
        imageView=findViewById(R.id.image_view);
        //设置监听
        seekBar.setOnSeekBarChangeListener(this);
        bt_rec.setOnClickListener(this);
        bt_BitmapNegation.setOnClickListener(this);
        bt_MatNegation.setOnClickListener(this);
        bt_gray.setOnClickListener(this);
        bt_Mat_bit.setOnClickListener(this);
        bt_GaussBlur.setOnClickListener(this);
        bt_meanBlur.setOnClickListener(this);
        bt_recBitmap.setOnClickListener(this);
        bt_biBlur.setOnClickListener(this);
        bt_erod.setOnClickListener(this);
        bt_dilate.setOnClickListener(this);
        bt_open.setOnClickListener(this);
        bt_close.setOnClickListener(this);
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
                seekBarFalg="biBlur";
                Toast.makeText(CardOCRActivity.this,"拖动seekbar调节模糊度",Toast.LENGTH_SHORT).show();
//                bitmap=Blurclass.biBlur(bitmap);
//                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_erod://膨胀
                bitmap= EDOpenColse.erodOrDilate("erod",bitmap);
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_dilate://腐蚀
                bitmap= EDOpenColse.erodOrDilate("dilate",bitmap);
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_open:
                bitmap=EDOpenColse.openOrClose("open",bitmap);
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_close:
                bitmap=EDOpenColse.openOrClose("close",bitmap);
                imageView.setImageBitmap(bitmap);
                break;

        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int Value=seekBar.getProgress();
        tv_SeekBar.setText(Value+"");
        if ("".equals(seekBarFalg)){
            Dialog.ShowDialog(this);
           // Toast.makeText(CardOCRActivity.this,"请选择功能后拖动seekbar",Toast.LENGTH_SHORT).show();
        }else if ("biBlur".equals(seekBarFalg)){
            //双边模糊加锐化
            bitmap=getBitmap();
            bitmap=Blurclass.biBlur(bitmap,Value);
            imageView.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
