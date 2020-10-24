package cn.com.avatek.pole.entity;


/**
 * Created by shenxw on 2017/2/6.
 */

public class UserResult {

    /**
     * state : 1
     * content : {"user_id":"1","name":"测试人员","tel":"123456","password":"1234","dep_id":"100","dep_name":"测试部门"}
     */

    private int state;
    private ContentBean content;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * user_id : 1
         * name : 测试人员
         * tel : 123456
         * password : 1234
         * dep_id : 100
         * dep_name : 测试部门
         */

        private String user_id;
        private String name;
        private String tel;
        private String password;
        private String dep_id;
        private String dep_name;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }
    }
}
