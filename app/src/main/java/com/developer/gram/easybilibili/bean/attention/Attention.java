package com.developer.gram.easybilibili.bean.attention;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gram on 2018/1/3.
 */

public class Attention {
    public List<ItemBean> item;

    public static class ItemBean {
        public int isRecent;
        public int position;//折叠的位置
        public String title;
        public String cover;
        public String uri;
        public String param;
        @SerializedName("goto")
        public String gotoX;
        public String desc;
        public int play;
        public int danmaku;
        public int reply;
        public int favorite;
        public int tid;
        public String tname;
        public TagBean tag;
        public long ctime;
        public int duration;
        public int mid;
        public String name;
        public String face;
        public int is_atten;
        public int recent_count;
        public int coin;
        public int share;
        public int count;
        public int type;
        public String index;
        public String index_title;
        public int updates;
        public List<RecentBean> recent;

        public static class TagBean {
            public int tag_id;
            public String tag_name;
            public CountBean count;

            public static class CountBean {
                public int atten;
            }
        }

        public static class RecentBean {
            public String title;
            public String cover;
            public String uri;
            public String param;
            @SerializedName("goto")
            public String gotoX;
            public String desc;
            public int play;
            public int danmaku;
            public int reply;
            public int favorite;
            public int tid;
            public String tname;
            public TagBeanX tag;
            public int ctime;
            public int duration;
            public int mid;
            public String name;
            public String face;
            public int is_atten;
            public int share;
            public int coin;

            public static class TagBeanX {
                public int tag_id;
                public String tag_name;
                public CountBeanX count;

                public static class CountBeanX {
                    public int atten;
                }
            }
        }
    }
}
