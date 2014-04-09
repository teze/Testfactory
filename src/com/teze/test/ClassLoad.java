package com.teze.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.teze.test.activity.TestGJsonActivity;
import com.teze.test.activity.TestGifActivity;

import android.app.Activity;
import android.content.Context;
import dalvik.system.DexFile;

public class ClassLoad {



	/** 功能：
	 * @return  
	 * @author: by Fooyou 2014年4月9日  下午5:06:33
	 */
	@Deprecated // dont work  for Android 
	public static Set<Class<? extends Object>> getClassName() {
		ConfigurationBuilder builder=new ConfigurationBuilder();
		builder.addUrls(ClasspathHelper.forPackage("com.teze.test"));
		builder.setScanners(new SubTypesScanner(true));
		Reflections reflections = new Reflections(builder);
		Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
		return allClasses;
	}

	public static void getPackageCode(Context context){
		try {
			DexFile dexFile = new DexFile(context.getPackageCodePath());
			Enumeration<String> enumeration = dexFile.entries();
			enumeration.hasMoreElements();
			while (enumeration.hasMoreElements()) {
				String string = (String) enumeration.nextElement();
				Loger.i(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public static List<ListItem> getListItems(){
		List<ListItem> items=new Vector<ListItem>();
		Set<Class<? extends Object>> allClasses= getTestActivityList();
		for (Class<? extends Object> classTemp : allClasses) {

			Class<? extends Object> activity=classTemp; 
			ListItem item=new ListItem();
			item.title=classTemp.getSimpleName();
			item.classObj=activity;
			try {
				Activity activityObj;
				activityObj = (Activity) activity.newInstance();
				Field field=activity.getDeclaredField("TAG");
				field.setAccessible(true);
				item.description=(String) field.get(activityObj);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
			items.add(item);
		}
		return items;
	}
	
	public static Set<Class<? extends Object>>  getTestActivityList(){
		Set<Class<? extends Object>> list=new HashSet<Class<? extends Object>>();
		list.add(TestGifActivity.class);
		list.add(TestGJsonActivity.class);
		return list;
	}
}
