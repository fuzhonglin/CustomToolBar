package com.example.toolbar;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomToolBar extends RelativeLayout implements OnMenuItemClickListener, DrawerListener{

	private ImageView mIcon;//ToolBar的图标
	private int mIconLeftMargin = dip2px(10);//图标的左边距
	
	private TextView mTitle;//ToolBar标题
	private int mTitleLeftMargin = dip2px(5);//标题的左边距
	
	private TextView mContent;//ToolBar的内容
	
	private LinearLayout mLinearLayout;//菜单按钮
	private int mLinearLayoutLeftPadding = dip2px(30);//菜单的右边距
	private OnMenuItemClickListener mListener;//菜单按钮的点击事件
	
	private DrawerLayout mDrawerLayout;//和ToolBar关联的DrawerLayout
	private DrawerToggle mDrawerToggle;//DrawerLayout的打开和关闭的监听
	
	//设置菜单按钮的点击监听事件
	public void setOnMenuItemClickListener(OnMenuItemClickListener listener){
		mListener = listener;
	}
	
	//DrawerLayout的打开和关闭的监听类
	public interface DrawerToggle{
		public void onDrawerClosed(View view);
		public void onDrawerOpened(View view);
	}
	
	/**
	 * 设置DrawerLayout的打开和关闭的监听，通过设置此方法可以通过点击ToolBar图标打开和关闭DrawerLayout的侧边栏
	 * 并在DrawerLayout侧边栏打开和关闭的时候，做相应的操作
	 * @param drawerLayout
	 * 		DrawerLayout布局
	 * @param drawerToggle
	 * 		DrawerLayout打开和关闭的监听类
	 */
	public void setDrawerToggle(DrawerLayout drawerLayout, DrawerToggle drawerToggle){
		mDrawerLayout = drawerLayout;
		mDrawerToggle = drawerToggle;
		mDrawerLayout.setDrawerListener(this);
	}
	
	/**
	 * 设置ToolBar的图标
	 * @param resId
	 * 		图标的资源id
	 */
	public void setIcon(int resId){
		mIcon.setImageResource(resId);
	}
	
	/**
	 * 设置图标的左边距
	 * @param margin
	 * 		图标的左边距值，单位为dp
	 */
	public void setIconLeftMargin(int margin){
		mIconLeftMargin = dip2px(margin);
	}

	/**设置ToolBar的标题
	 * @param title
	 * 		标题内容
	 * @param textSize
	 * 		标题大小，单位dp
	 * @param textColor
	 * 		标题颜色
	 */
	public void setTitle(String title, int textSize, int textColor){
		mTitle.setText(title);
		mTitle.setTextSize(dip2px(textSize));
		mTitle.setTextColor(textColor);
	}
	
	/**
	 * 设置标题的左边距
	 * @param margin
	 * 		标题的左边距值，单位为dp
	 */
	public void setTitleLeftMargin(int margin){
		mTitleLeftMargin = dip2px(margin);
	}
	
	/**设置ToolBar的内容
	 * @param content
	 * 		ToolBar内容
	 * @param textSize
	 * 		文字大小，单位dp
	 * @param textColor
	 * 		文字颜色
	 */
	public void setContent(String content, int textSize, int textColor){
		mContent.setText(content);
		mContent.setTextSize(sp2px(textSize));
		mContent.setTextColor(textColor);
	}
	
	/**
	 * 设置菜单按钮的左边距
	 * @param padding
	 * 		左边距值，单位为dp
	 */
	public void setMenuLeftPadding(int padding){
		mLinearLayoutLeftPadding = dip2px(padding);
	}
	
	/**
	 * 设置是否显示菜单按钮
	 * @param isShowMenu
	 * 		true 显示 ，false 不显示
	 */
	public void setIsShowMenu(boolean isShowMenu){
		if(!isShowMenu){
			mLinearLayout.removeAllViews();
		}
	}
	
	/**
	 * 设置菜单的按钮图标
	 * @param resId
	 * 		图标的id值的数组
	 */
	public void setMenuIcon(int[] resId){
		mLinearLayout.removeAllViews();
		ImageView imageView;
		for(int i=0; i<resId.length; i++){
			imageView = new ImageView(getContext());
			imageView.setImageResource(resId[i]);
			LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(0, 
										LinearLayout.LayoutParams.WRAP_CONTENT, 1);
			mLinearLayout.addView(imageView, menuParams);
		}
	}
	
	public CustomToolBar(Context context) {
		this(context, null);
	}

	public CustomToolBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomToolBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		//初始化图标
		initIconView();	
		//初始化标题
		initTitle();
		//初始化内容
		initContent();
		//初始化菜单图标
		initMenuIcon();
		//初始化菜单
		initMenu();
	}

	private void initIconView() {
		mIcon = new ImageView(getContext());
		mIcon.setId(generateId()+1);
		LayoutParams iconParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
																  LayoutParams.WRAP_CONTENT);
		iconParams.addRule(CENTER_VERTICAL);
		iconParams.leftMargin = mIconLeftMargin;
		
		mIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDrawerLayout!=null){
					//当和DrawerLayout关联时，通过点击图标控制DrawerLayout的打开和关闭
					if(mDrawerLayout.isDrawerOpen(Gravity.START)){
						mDrawerLayout.closeDrawer(Gravity.START);
					}else{
						mDrawerLayout.openDrawer(Gravity.START);
					}
				}	
			}
		});
		
		addView(mIcon, iconParams);
	}
	
	private void initTitle() {
		mTitle = new TextView(getContext());
		LayoutParams titleParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
																   LayoutParams.WRAP_CONTENT);
		titleParams.addRule(CENTER_VERTICAL);
		titleParams.addRule(RIGHT_OF, mIcon.getId());
		titleParams.leftMargin = mTitleLeftMargin;
		addView(mTitle, titleParams);
	}
	
	private void initContent() {
		mContent = new TextView(getContext());
		mContent.setId(generateId());
		LayoutParams contentParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
				   													 LayoutParams.WRAP_CONTENT);
		contentParams.addRule(CENTER_IN_PARENT);
		addView(mContent, contentParams);
	}
	
	private void initMenuIcon() {
		mLinearLayout = new LinearLayout(getContext());
		mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		mLinearLayout.setPadding(mLinearLayoutLeftPadding, 0, 0, 0);
		LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
															  LayoutParams.WRAP_CONTENT);
		params.addRule(CENTER_VERTICAL);
		params.addRule(RIGHT_OF, mContent.getId());
		addView(mLinearLayout, params);
		
		//添加菜单按钮图标
		addMenuIcon();
	}
	
	private void addMenuIcon() {
		mLinearLayout.removeAllViews();
		ImageView imageView;
		PopupMenu popupMenu = new PopupMenu(getContext(), this);
		popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
		Menu menu = popupMenu.getMenu();
		int size = menu.size();
		if(size>=2){
			//添加第一个图标
			imageView = new ImageView(getContext());
			imageView.setImageDrawable(menu.getItem(0).getIcon());
			LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, 
											LinearLayout.LayoutParams.WRAP_CONTENT, 1);
			mLinearLayout.addView(imageView, params1);
				
			//添加第二个图标
			imageView = new ImageView(getContext());
			imageView.setImageDrawable(menu.getItem(1).getIcon());
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, 
											LinearLayout.LayoutParams.WRAP_CONTENT, 1);
			mLinearLayout.addView(imageView, params2);
		}else{
			imageView = new ImageView(getContext());
			imageView.setImageDrawable(menu.getItem(0).getIcon());
			LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, 
											LinearLayout.LayoutParams.WRAP_CONTENT, 1);
			mLinearLayout.addView(imageView, params3);
		}
		//自定义图标
		LinearLayout menuView = new LinearLayout(getContext());
		menuView.setGravity(Gravity.CENTER_HORIZONTAL);
		menuView.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(0, 
									  LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		mLinearLayout.addView(menuView, params4);
			
		//生成圆点
		GradientDrawable point = new GradientDrawable();
		point.setShape(GradientDrawable.OVAL);
		point.setColor(0xffffffff);
		point.setSize(dip2px(6), dip2px(6));
			
		for(int i=0; i<3; i++){
			ImageView pointView = new ImageView(getContext());
			pointView.setImageDrawable(point);
			LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 
									android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			pointParams.bottomMargin = dip2px(2);
			pointParams.topMargin = dip2px(2);
				
			menuView.addView(pointView, pointParams);
		}
	}

	private void initMenu() {
		final View menuView = mLinearLayout.getChildAt(mLinearLayout.getChildCount()-1);
		if(menuView!=null){
			menuView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//当点击菜单按钮时弹出菜单
					PopupMenu popupMenu = new PopupMenu(getContext(), menuView);
					popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
					popupMenu.setOnMenuItemClickListener(CustomToolBar.this);
					popupMenu.show();
				}
			});
		}
	}
	
	//该方法用于将dp转换为px
	private int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}
	
	//该方法用于将sp转换为px
	private int sp2px(float sp){
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		int applyDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics);
		return applyDimension;
	}
	
	//该方法用于生成view的id
	private int generateId() { 
		AtomicInteger sNextGeneratedId = new AtomicInteger(1); 
	    for (;;) {  
	        final int result = sNextGeneratedId.get();  
	        int newValue = result + 1;  
	        if (newValue > 0x00FFFFFF) newValue = 1;  
	        if (sNextGeneratedId.compareAndSet(result, newValue)) {
	            return result;  
	        }  
	    }  
	}  

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		if(mListener!=null){
			mListener.onMenuItemClick(item);
		}
		return false;
	}

	@Override
	public void onDrawerClosed(View view) {
		if(mDrawerToggle!=null){
			mDrawerToggle.onDrawerClosed(view);
		}
	}

	@Override
	public void onDrawerOpened(View view) {
		if(mDrawerToggle!=null){
			mDrawerToggle.onDrawerOpened(view);
		}
	}

	@Override
	public void onDrawerSlide(View arg0, float arg1){}

	@Override
	public void onDrawerStateChanged(int arg0){}
								
}
