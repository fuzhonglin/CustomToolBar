# CustomToolBar
自定义的标题栏

常用方法如下：

* public void setDrawerToggle(DrawerLayout drawerLayout, DrawerToggle drawerToggle)；设置DrawerLayout的打开和关闭的监听，通过设置此方法可以通过点击ToolBar图标打开和关闭DrawerLayout的侧边栏，并在DrawerLayout侧边栏打开和关闭的时候，做相应的操作。

* public void setIcon(int resId)；设置ToolBar的图标。

* public void setIconLeftMargin(int margin)；设置图标的左边距。

* public void setTitle(String title, int textSize, int textColor)；设置ToolBar的标题，包括标题内容、标题大小、标题颜色。

* public void setTitleLeftMargin(int margin)； 设置标题的左边距。

* public void setContent(String content, int textSize, int textColor)；设置ToolBar的内容，包括ToolBar内容、文字大小、文字颜色。

* public void setMenuLeftPadding(int padding)；设置菜单按钮的左边距。

* public void setIsShowMenu(boolean isShowMenu)；设置是否显示菜单按钮。

* public void setMenuIcon(int[] resId)；设置菜单的按钮图标。