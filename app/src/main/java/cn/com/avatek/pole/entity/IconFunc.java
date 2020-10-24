package cn.com.avatek.pole.entity;

import java.util.List;

public class IconFunc {

    /**
     * state : 1
     * content : {"news":[{"notice_id":"1","name":"新闻1","images":"32333"}],"works":[{"type_id":"1","name":"测试业务1","icon":"111","type_name":"政务","url":"xxxx"}]}
     * reason : 获取成功
     */

    private String state;
    private ContentBean content;
    private String reason;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class ContentBean {
        private List<NewsBean> news;
        private List<WorksBean> works;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<WorksBean> getWorks() {
            return works;
        }

        public void setWorks(List<WorksBean> works) {
            this.works = works;
        }

        public static class NewsBean {
            /**
             * notice_id : 1
             * name : 新闻1
             * images : 32333
             */

            private String notice_id;
            private String name;
            private String images;

            public String getNotice_id() {
                return notice_id;
            }

            public void setNotice_id(String notice_id) {
                this.notice_id = notice_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }
        }

        public static class WorksBean {
            /**
             * type_id : 1
             * name : 测试业务1
             * icon : 111
             * type_name : 政务
             * url : xxxx
             */

            private String type_id;
            private String name;
            private String icon;
            private String type_name;
            private String url;

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
