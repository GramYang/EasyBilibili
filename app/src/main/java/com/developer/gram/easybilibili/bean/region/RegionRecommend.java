package com.developer.gram.easybilibili.bean.region;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gram on 2018/1/2.
 */

public class RegionRecommend {
    private int code;
    private List<DataBean> data;
    private String message;
    private int ttl;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public static class DataBean {
        private String param;
        private String type;
        private String style;
        private String title;
        private List<BodyBean> body;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<BodyBean> getBody() {
            return body;
        }

        public void setBody(List<BodyBean> body) {
            this.body = body;
        }

        public static class BodyBean{
            private String title;
            private String cover;
            private String uri;
            private String param;
            @SerializedName("goto")
            private String gotoX;
            private int play;
            private int finish;
            private String index;
            private String total_count;
            private String mtime;
            private int status;
            private int favourite;
            private boolean is_ad;
            private int cm_mark;
            private int danmaku;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getGotoX() {
                return gotoX;
            }

            public void setGotoX(String gotoX) {
                this.gotoX = gotoX;
            }

            public int getPlay() {
                return play;
            }

            public void setPlay(int play) {
                this.play = play;
            }

            public int getFinish() {
                return finish;
            }

            public void setFinish(int finish) {
                this.finish = finish;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public String getTotal_count() {
                return total_count;
            }

            public void setTotal_count(String total_count) {
                this.total_count = total_count;
            }

            public String getMtime() {
                return mtime;
            }

            public void setMtime(String mtime) {
                this.mtime = mtime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getFavourite() {
                return favourite;
            }

            public void setFavourite(int favourite) {
                this.favourite = favourite;
            }

            public boolean isIs_ad() {
                return is_ad;
            }

            public void setIs_ad(boolean is_ad) {
                this.is_ad = is_ad;
            }

            public int getCm_mark() {
                return cm_mark;
            }

            public void setCm_mark(int cm_mark) {
                this.cm_mark = cm_mark;
            }

            public int getDanmaku() {
                return danmaku;
            }

            public void setDanmaku(int danmaku) {
                this.danmaku = danmaku;
            }
        }
    }
}
