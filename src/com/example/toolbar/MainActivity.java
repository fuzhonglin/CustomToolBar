package com.example.toolbar;

import com.example.toolbar.CustomToolBar.DrawerToggle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomToolBar toolBar = (CustomToolBar) findViewById(R.id.tool);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
    	
        toolBar.setBackgroundColor(0xffff0000);
    	toolBar.setIcon(R.drawable.img_menu);
    	toolBar.setIconLeftMargin(5);
    	toolBar.setTitle("标题", 10, 0xffffffff);
    	toolBar.setTitleLeftMargin(0);
    	toolBar.setContent("条目内容", 10, 0xffffffff);
    	toolBar.setIsShowMenu(true);
    	
		toolBar.setDrawerToggle(drawer, new DrawerToggle() {
			@Override
			public void onDrawerOpened(View view) {
				Toast.makeText(getApplicationContext(), "菜单打开了", Toast.LENGTH_SHORT).show();	
			}
			
			@Override
			public void onDrawerClosed(View view) {
				Toast.makeText(getApplicationContext(), "菜单关闭了", Toast.LENGTH_SHORT).show();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
