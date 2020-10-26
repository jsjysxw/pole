package cn.com.avatek.pole.entity;

import java.util.List;

public class PointResult {

    /**
     * state : 1
     * content : [{"point_id":"1","num":"aaa","dist":"100","x":"1","y":"9","cross":"cross","voltage":"220v","point_type":"ak","remarks":"aaaaa","pid":"-1","status":"0","line_id":"1"}]
     */

    private int state;
    private List<ContentBean> content;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * point_id : 1
         * num : aaa
         * dist : 100
         * x : 1
         * y : 9
         * cross : cross
         * voltage : 220v
         * point_type : ak
         * remarks : aaaaa
         * pid : -1
         * status : 0
         * line_id : 1
         */

        private String point_id;
        private String num;
        private String dist;
        private String x;
        private String y;
        private String cross;
        private String voltage;
        private String point_type;
        private String remarks;
        private String pid;
        private String status;
        private String line_id;
        private int left;
        private int top;

        public String getPoint_id() {
            return point_id;
        }

        public void setPoint_id(String point_id) {
            this.point_id = point_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDist() {
            return dist;
        }

        public void setDist(String dist) {
            this.dist = dist;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getCross() {
            return cross;
        }

        public void setCross(String cross) {
            this.cross = cross;
        }

        public String getVoltage() {
            return voltage;
        }

        public void setVoltage(String voltage) {
            this.voltage = voltage;
        }

        public String getPoint_type() {
            return point_type;
        }

        public void setPoint_type(String point_type) {
            this.point_type = point_type;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }
    }
}
