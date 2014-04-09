package com.teze.test;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener{

	protected static final String TAG = "MainActivity";
	private ListView listView  ;
	private ListAdapter mAdapter  ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	
	private void initView(){
		listView=(ListView) findViewById(R.id.listView);
	}
	
	
	protected void initData(){
		mAdapter=new ListAdapter(getApplicationContext());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
		List<ListItem> items=ClassLoad.getListItems();
		mAdapter.setItems(items);
		mAdapter.notifyDataSetChanged();
	}
	
	
	
	class ListAdapter extends CommonAdapter<ListItem>{

		public ListAdapter(Context context) {
			super(context);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder=null;
			ListItem info=getItem(position);
			if (convertView==null) {
				viewHolder=new ViewHolder();
				convertView=View.inflate(getApplicationContext(), R.layout.item_list, null);
				viewHolder.title=(TextView) convertView.findViewById(R.id.title);
				viewHolder.description=(TextView) convertView.findViewById(R.id.description);
				convertView.setTag(viewHolder);
			}else {
				viewHolder=(ViewHolder) convertView.getTag();
			}
			viewHolder.title.setText(info.title);
			viewHolder.description.setText(info.description);
			return convertView;
		}
		
		class ViewHolder{
			TextView title;
			TextView description;
		}
	}
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			ListItem currentItem=mAdapter.getItem(position);
			Class<?> cls= (Class<?>) currentItem.classObj;
			Intent intent=new Intent(this, cls);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
