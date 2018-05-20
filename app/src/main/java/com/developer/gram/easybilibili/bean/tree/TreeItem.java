package com.developer.gram.easybilibili.bean.tree;

import com.developer.gram.easybilibili.bean.attention.Attention;
import com.developer.gram.easybilibili.util.EmptyUtils;

import java.util.List;

/**
 * Created by Gram on 2018/1/4.
 */

public class TreeItem extends AbstractTreeItem<TreeItem> {
    public static final int TYPE_LV0 = 0;
    public static final int TYPE_LV1 = 1;

    private int itemType;
    private Attention.ItemBean group;
    private boolean flag;
    private Attention.ItemBean.RecentBean recent;
    private List<TreeItem> child;
    private int level;

    public int getItemType() {
        return itemType;
    }

    public TreeItem setItemType(int itemType) {
        this.itemType = itemType;
        return this;
    }

    public Attention.ItemBean getGroup() {
        return group;
    }

    public TreeItem setGroup(Attention.ItemBean group) {
        this.group = group;
        return this;
    }

    public boolean isFlag() {
        return flag;
    }

    public TreeItem setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public Attention.ItemBean.RecentBean getRecent() {
        return recent;
    }

    public TreeItem setRecent(Attention.ItemBean.RecentBean recent) {
        this.recent = recent;
        return this;
    }

    public List<TreeItem> getChild() {
        return child;
    }

    public TreeItem setChild(List<TreeItem> child) {
        this.child = child;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public TreeItem setLevel(int level) {
        this.level = level;
        return this;
    }

    public TreeItem addSubItem(List<TreeItem> child) {
        this.child = child;
        if (EmptyUtils.isNotEmpty(child)) {
            for (TreeItem treeItem : child)
                addSubItem(treeItem);
        }
        return this;
    }
}
