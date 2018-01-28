package julong.utile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import cn.lovelqq.julong.opencvdome.CardOCRActivity;

/**
 * 对话框
 * Created by julong on 2018/1/26.
 */

public class Dialog {
    /**
     * 普通的dialog
     */
    public static void seekbar_ShowDialog(Context context){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("此功能不支持拖动seekbar");
        builder.setPositiveButton("确定",new DialogSureClickListener());
       // builder.setNegativeButton("取消",new DialogCanelClickListener());
        AlertDialog dialog= builder.create();
        dialog.show();

    }

}
//两个事件 : 确定监听
class DialogSureClickListener implements DialogInterface.OnClickListener{

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        CardOCRActivity.seekBarFalg="";
        Log.e("Dialog","点击了确定按钮");
    }

}
//取消监听
class DialogCanelClickListener implements DialogInterface.OnClickListener{

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // 判断 点击的是什么按钮
        dialog.dismiss();
        Log.e("Dialog","点击了取消按钮");

    }

}