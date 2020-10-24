package cn.com.avatek.pole.entity;

import java.util.List;

public class HomeFunc {


    /**
     * state : 1
     * content : {"num":{"all_num":"1","now_num":"0","end_num":"0"},"lines":[{"line_id":"1","num":"123","name":"xxx","user_id":"1","create_time":"2020-10-23 10:00:06","update_time":"2020-10-23 10:00:06","status":"0"}]}
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
         * num : {"all_num":"1","now_num":"0","end_num":"0"}
         * lines : [{"line_id":"1","num":"123","name":"xxx","user_id":"1","create_time":"2020-10-23 10:00:06","update_time":"2020-10-23 10:00:06","status":"0"}]
         */

        private NumBean num;
        private List<LinesBean> lines;

        public NumBean getNum() {
            return num;
        }

        public void setNum(NumBean num) {
            this.num = num;
        }

        public List<LinesBean> getLines() {
            return lines;
        }

        public void setLines(List<LinesBean> lines) {
            this.lines = lines;
        }

        public static class NumBean {
            /**
             * all_num : 1
             * now_num : 0
             * end_num : 0
             */

            private String all_num;
            private String now_num;
            private String end_num;

            public String getAll_num() {
                return all_num;
            }

            public void setAll_num(String all_num) {
                this.all_num = all_num;
            }

            public String getNow_num() {
                return now_num;
            }

            public void setNow_num(String now_num) {
                this.now_num = now_num;
            }

            public String getEnd_num() {
                return end_num;
            }

            public void setEnd_num(String end_num) {
                this.end_num = end_num;
            }
        }

        public static class LinesBean {
            /**
             * line_id : 1
             * num : 123
             * name : xxx
             * user_id : 1
             * create_time : 2020-10-23 10:00:06
             * update_time : 2020-10-23 10:00:06
             * status : 0
             */

            private String line_id;
            private String num;
            private String name;
            private String user_id;
            private String create_time;
            private String update_time;
            private String status;

            public String getLine_id() {
                return line_id;
            }

            public void setLine_id(String line_id) {
                this.line_id = line_id;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
