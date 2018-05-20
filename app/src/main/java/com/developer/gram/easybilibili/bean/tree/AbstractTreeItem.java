package com.developer.gram.easybilibili.bean.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gram on 2018/1/4.
 */

public abstract class AbstractTreeItem<T> {
    protected boolean mExpandable = false;
    protected List<T> mSubItems;

    public boolean isExpanded() {
        return mExpandable;
    }

    public void setExpanded(boolean expanded) {
        mExpandable = expanded;
    }

    public List<T> getSubItems() {
        return mSubItems;
    }

    public boolean hasSubItem() {
        return mSubItems != null && mSubItems.size() > 0;
    }

    public void setSubItems(List<T> list) {
        mSubItems = list;
    }

    public T getSubItem(int position) {
        if (hasSubItem() && position < mSubItems.size()) {
            return mSubItems.get(position);
        } else {
            return null;
        }
    }

    public int getSubItemPosition(T subItem) {
        return mSubItems != null ? mSubItems.indexOf(subItem) : -1;
    }

    public void addSubItem(T subItem) {
        if (mSubItems == null) {
            mSubItems = new ArrayList<>();
        }
        mSubItems.add(subItem);
    }

    public void addSubItem(int position, T subItem) {
        if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
            mSubItems.add(position, subItem);
        } else {
            addSubItem(subItem);
        }
    }

    public boolean contains(T subItem) {
        return mSubItems != null && mSubItems.contains(subItem);
    }

    public boolean removeSubItem(T subItem) {
        return mSubItems != null && mSubItems.remove(subItem);
    }

    public boolean removeSubItem(int position) {
        if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
            mSubItems.remove(position);
            return true;
        }
        return false;
    }
}