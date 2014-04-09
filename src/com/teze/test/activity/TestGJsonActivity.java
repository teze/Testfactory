package com.teze.test.activity;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.teze.test.JsonInfo;
import com.teze.test.R;

public class TestGJsonActivity extends Activity {

	protected static final String TAG = "TestGJsonActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_gjson);
		testTojson();
	}


	private void testTojson(){

		List<JsonInfo> infos=new Vector<JsonInfo>();
		for (int i = 0; i < 10; i++) {
			JsonInfo info=new JsonInfo();
			info.name="name"+i;
			info.title="title"+i;
			info.book=new JsonInfo.Book();
			info.book.bookName="bookName";
			infos.add(info);
		}
		Gson gson=new GsonBuilder().create();
		String jsonString=gson.toJson(infos);
		Log.i("TAG", jsonString);

	}


	public void testGJson() throws JSONException {
		try {
			String aaString = "{info: {extra: {thumb_size: \"_250x250.jpg\"},data: [{id: \"2\",pic_url: \"xxx\",cid: \"3\"}]},response_status: \"success\",msg: \"test aa\"}";

			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(aaString);
			JsonObject jsonObject = element.getAsJsonObject();
			InputStream inputStream;

			inputStream = getAssets().open("text.txt");
			Reader reader = new InputStreamReader(inputStream);
			JsonReader jsonReader = new JsonReader(reader);

			try {
				jsonReader.beginObject();
				while (jsonReader.hasNext()) {
					String nameTag = jsonReader.nextName();
					if (nameTag.equals("info")) {
						String infoTag = jsonReader.nextName();
						if (infoTag.equals("extra")) {
							String thumb_size = jsonReader.nextString();
						} else if (infoTag.equals("data")) {
							jsonReader.beginArray();
							while (jsonReader.hasNext()) {
								jsonReader.nextName();
							}
							jsonReader.endArray();
						}
					} else if (nameTag.equals("response_status")) {
					} else if (nameTag.equals("msg")) {
					}
				}
				jsonReader.endObject();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public void testJson() throws IOException{
		InputStream inputStream = getAssets().open("text.txt");
		DataInputStream data=new DataInputStream(inputStream);
		StringBuffer buffer=new StringBuffer();
		String aa;
		byte [] bytebuffer=new byte[1024];
		while (data.read(bytebuffer)>0) {
			buffer.append(new String(bytebuffer));
		}
		String jsonContent =buffer.toString();
		JSONTokener tokener = null;
		tokener=new JSONTokener(jsonContent);

		try {
			Object nextValue=tokener.nextValue();
			if (!(nextValue instanceof JSONObject)) {
				//error 
			}
			JSONObject jsonObject =(JSONObject) nextValue;
			if (jsonObject!=null) {
				if (jsonObject.has("info")) {
					JSONObject infoObject=jsonObject.getJSONObject("info");
					if (infoObject.has("extra")){
						JSONObject extraObject=infoObject.getJSONObject("extra");
						String thumbSize=extraObject.has("thumb_size")?extraObject.getString("thumb_size"):"";
						Log.i(TAG, "thumbSize >> "+thumbSize);
					}
					if (infoObject.has("data")) {
						JSONArray dataArray=infoObject.getJSONArray("data");
						int len=dataArray.length();
						for (int i = 0; i < len; i++) {
							JSONObject dataElement=(JSONObject) dataArray.get(i);
							String id=dataElement.has("id")?dataElement.getString("id"):"";
							String title=dataElement.has("title")?dataElement.getString("title"):"";
							String price=dataElement.has("price")?dataElement.getString("price"):"";
							String pic_url=dataElement.has("pic_url")?dataElement.getString("pic_url"):"";
							String volume=dataElement.has("volume")?dataElement.getString("volume"):"";
							String wap_detail_url=dataElement.has("wap_detail_url")?dataElement.getString("wap_detail_url"):"";
							String fav_create_time=dataElement.has("fav_create_time")?dataElement.getString("fav_create_time"):"";
							String cid=dataElement.has("cid")?dataElement.getString("cid"):"";
						}
					}
				}
				if (jsonObject.has("response_status")) {
					String response_status=jsonObject.has("response_status")?jsonObject.getString("response_status"):"";
				}
				if (jsonObject.has("msg")) {
					String msg=jsonObject.has("msg")?jsonObject.getString("msg"):"";
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
