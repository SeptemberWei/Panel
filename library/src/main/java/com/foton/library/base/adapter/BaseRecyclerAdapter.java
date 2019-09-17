package com.foton.library.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foton.library.annotation.ContentView;

import java.util.Collection;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;

    protected Fragment mFragment;

    protected List<T> mDataSet;

    protected LayoutInflater mLayoutInflater;

    protected final int VIEW_EMPTY = 0;
    protected final int VIEW_ITEM = 1;

    private OnItemClickListener onItemClickLitener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnNoneViewClickListener onNoneViewClickListener;

    public BaseRecyclerAdapter(Context context, List<T> dataSet) {
        this.mContext = context;
        this.mDataSet = dataSet;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public BaseRecyclerAdapter(Fragment fragment, List<T> dataSet) {
        this.mFragment = fragment;
        this.mContext = fragment.getActivity();
        this.mDataSet = dataSet;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public T getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.size() == 0) {
            return VIEW_EMPTY;
        }
        return VIEW_ITEM;
    }

    @Override
    public int getItemCount() {
        return (null == mDataSet || mDataSet.size() == 0) ? 1 : mDataSet.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnNoneViewClickListener(OnNoneViewClickListener onNoneViewClickListener) {
        this.onNoneViewClickListener = onNoneViewClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_EMPTY) {
            View viewEmpty = LayoutInflater.from(mContext).inflate(getLayoutResource(viewType), viewGroup, false);
            return new BaseViewHolder(viewType, viewEmpty, viewGroup);
        }
        View convertView = mLayoutInflater.inflate(getLayoutResource(viewType), viewGroup, false);
        return new BaseViewHolder(viewType, convertView, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder baseViewHolder, final int i) {
        if (baseViewHolder.getViewType() == VIEW_ITEM) {
            if (onItemClickLitener != null) {
                baseViewHolder.itemView.setOnClickListener(v -> onItemClickLitener.onItemClick(baseViewHolder.itemView, i));
            }

            if (onItemLongClickListener != null) {
                baseViewHolder.itemView.setOnLongClickListener(v -> {
                    onItemLongClickListener.onItemLongClick(baseViewHolder.itemView, i);
                    return false;
                });
            }
            onBaseBindViewHolder(baseViewHolder, i);
        } else {
            if (onNoneViewClickListener != null) {
                baseViewHolder.itemView.setOnClickListener(v -> onNoneViewClickListener.onNoneViewClick(baseViewHolder.itemView));
            }
        }
    }

    public abstract void onBaseBindViewHolder(BaseViewHolder baseViewHolder, int position);

    /**
     * 返回AdapterItem中资源布局
     *
     * @param viewType
     * @return
     */
    protected int getLayoutResource(int viewType) {
        Class<? extends RecyclerView.Adapter> aClass = getClass();
        ContentView contentView = aClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId;
            if (viewType == VIEW_EMPTY) {
                layoutId = contentView.noneValue();
            } else {
                layoutId = contentView.value();
            }
            return layoutId;
        }
        return -1;
    }
//
//    /**
//     * 创建ViewHolder
//     *
//     * @param viewType
//     * @param convertView
//     * @param parent
//     * @return
//     */
//    protected abstract BaseViewHolder onCreateViewHolder(int viewType, View convertView, ViewGroup parent);


    /**
     * 替换List中指定位置对象
     *
     * @param position
     * @param obj
     */
    public void setItem(int position, T obj) {
        if (position < mDataSet.size()) {
            this.mDataSet.set(position, obj);
        }
    }

    /**
     * 在List中添加对象
     *
     * @param data
     */
    public void addItem(T data) {
        this.mDataSet.add(data);
    }

    /**
     * 在指定位置添加单个对象
     *
     * @param position
     * @param obj
     */
    public void addItem(int position, T obj) {
        if (position < mDataSet.size()) {
            this.mDataSet.add(position, obj);
        }
    }

    /**
     * 在List的末尾添加对象集合
     *
     * @param data
     */
    public void addItems(Collection<T> data) {
        this.mDataSet.addAll(data);
    }

    /**
     * 在position位置添加对象集合
     *
     * @param position
     * @param data
     */
    public void addItems(int position, Collection<T> data) {
        if (position < mDataSet.size()) {
            this.mDataSet.addAll(position, data);
        }
    }

    /**
     * 移除对象data
     *
     * @param data
     */
    public void remove(T data) {
        if (mDataSet.contains(data)) {
            this.mDataSet.remove(data);
        }
    }

    /**
     * 移除position位置的对象
     *
     * @param position
     */
    public void remove(int position) {
        if (position < mDataSet.size()) {
            this.mDataSet.remove(position);
        }
    }

    /**
     * 清空list对象
     */
    public void removeAll() {
        this.mDataSet.clear();
    }


    /**
     * item点击回调接口
     *
     * @author weizhaosheng@aliyun.com
     * @date 2019年03月15日
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    /**
     * item长按回调接口
     *
     * @author weizhaosheng@aliyun.com
     * @date 2019年03月15日
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    /**
     * 空视图点击回调接口
     */
    public interface OnNoneViewClickListener {
        void onNoneViewClick(View view);
    }
}
