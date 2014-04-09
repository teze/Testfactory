package com.teze.test.activity;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.teze.test.AnimatedGifEncoder;
import com.teze.test.Loger;
import com.teze.test.R;

public class TestGifActivity extends Activity {

	private static final String TAG = "TestGJsonActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_gif);
		testGenerateGif();
	}

	public byte[] generateGIF() {
		Bitmap bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		Bitmap bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.a);
		ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		bitmaps.add(bitmap1);
		bitmaps.add(bitmap2);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		AnimatedGifEncoder encoder = new AnimatedGifEncoder();
		encoder.start(bos);
		for (Bitmap bitmap : bitmaps) {
			encoder.addFrame(bitmap);
		}
		encoder.finish();
		return bos.toByteArray();
	}


	public void testGenerateGif(){
		long time =System.currentTimeMillis();
		FileOutputStream outStream = null;
		try{
			outStream = new FileOutputStream("/sdcard/test.gif");
			outStream.write(generateGIF());
			outStream.close();
			Loger.i(TAG, "testGenerateGif successful time >> "+(System.currentTimeMillis()-time));
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
