package cn.com.avatek.pole.entity;

import java.util.List;

public class CrmMenuEntity {

    /**
     * state : 1
     * content : [{"crm_id":"1","name":"集团快速建档及登录","count":"1","icon":"","type":"0","url":"","m_id":""},{"crm_id":"2","name":"集团快速建档","count":"2","icon":"http://www.dayo.net.cn/cmm/img/daily.png","type":"1","url":"http://117.187.6.80:5614//jtksjd417000001072.jsp?Uticket=","m_id":"417000001509"}]
     */
    private String state;
    private String text;
    private List<ContentEntity> content;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setContent(List<ContentEntity> content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public List<ContentEntity> getContent() {
        return content;
    }

    public class ContentEntity {
        /**
         * crm_id : 1
         * name : 集团快速建档及登录
         * count : 1
         * icon :
         * type : 0
         * url :
         * m_id :
         */
        private String crm_id;
        private String name;
        private String count;
        private String icon;
        private String type;
        private String url;
        private String m_id;

        public void setCrm_id(String crm_id) {
            this.crm_id = crm_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public String getCrm_id() {
            return crm_id;
        }

        public String getName() {
            return name;
        }

        public String getCount() {
            return count;
        }

        public String getIcon() {
            return icon;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public String getM_id() {
            return m_id;
        }
    }
}
