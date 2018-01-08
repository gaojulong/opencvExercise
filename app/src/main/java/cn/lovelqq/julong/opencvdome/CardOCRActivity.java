package cn.lovelqq.julong.opencvdome;

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

import Operation.Negation;

public class CardOCRActivity extends AppCompatActivity implements View.OnClickListener{

    //
    private Button bt_rec,bt_MatNegation,bt_BitmapNegation,bt_gray;
    private TextView tv_content;//识别结果显示
    private ImageView imageView;//图片显示
    private Uri fileUri;
    private Bitmap mainbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_ocr);

        init();

        fileUri = (Uri)this.getIntent().getParcelableExtra("PICTURE-URL");
        if(fileUri != null) {
            mainbitmap=displaySelectedImage();
        }
    }

    /**
     * 加载控件
     */
    private void init(){
        bt_rec=findViewById(R.id.bt_reco);
        bt_BitmapNegation=findViewById(R.id.bt_bitmapNegation);
        bt_MatNegation=findViewById(R.id.bt_matNegation);
        bt_gray=findViewById(R.id.bt_gray);


        tv_content=findViewById(R.id.tv_jieguo);
        imageView=findViewById(R.id.image_view);
        //设置监听
        bt_rec.setOnClickListener(this);
        bt_BitmapNegation.setOnClickListener(this);
        bt_MatNegation.setOnClickListener(this);
        bt_gray.setOnClickListener(this);

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
        Bitmap bitmap;
        switch (view.getId()){
            case R.id.bt_reco:
                Toast.makeText(this.getApplication(),"识别按钮被点击",Toast.LENGTH_SHORT).show();

                break;
            case R.id.bt_gray:
                bitmap=Negation.Gray(mainbitmap);
                imageView.setImageBitmap(bitmap);
                break;
            case  R.id.bt_bitmapNegation:
                bitmap= Negation.bitmapNegation(mainbitmap,getApplication());
                imageView.setImageBitmap(bitmap);
                break;
            case R.id.bt_matNegation:
                bitmap= Negation.matNegation(mainbitmap,getApplication());
                imageView.setImageBitmap(bitmap);
                break;

        }

    }
}
