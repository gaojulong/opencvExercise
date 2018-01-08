# OpenCV for Android 

说明：OpenCV平时练习 

### 环境
	安卓环境：	android stud 3.1  
	OpenCV SDK版本：OpenCV3.4
###  遇到的问题  
一、bitmap.setPixels 出现异常IllegalStateException
		解决办法：添加
	        bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
