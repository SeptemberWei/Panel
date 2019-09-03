package com.foton.library.base;

import android.os.Bundle;

public abstract interface BaseInterface {
    public abstract boolean coverTitle();
    /**
     * 根布局视图，若为0调用setContentView方法
     *
     * @return
     */
    public abstract int getLayoutRoot();

    /**
     * 主要布局视图，调用setContentLayout方法
     *
     * @return
     */
    public abstract int getLayoutContent();

    /**
     * 获取标题
     *
     * @return
     */
    public abstract Object getAppTitle();

    /**
     * app初始化
     *
     * @param savedInstanceState
     */
    public abstract void onBaseCreate(Bundle savedInstanceState);

    /**
     * 初始化
     */
    public abstract void init();

}
