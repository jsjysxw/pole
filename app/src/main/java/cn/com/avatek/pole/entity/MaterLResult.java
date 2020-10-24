package cn.com.avatek.pole.entity;

import java.util.List;

public class MaterLResult {

    /**
     * state : 1
     * content : {"material":[{"material_id":"1","name":"测试材料","level":"0","type_id":"1","pid":"-1"},{"material_id":"2","name":"测试材料2","level":"0","type_id":"1","pid":"-1"}],"group":[{"group_id":"G1","name":"测试组1"}]}
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
        private List<MaterialBean> material;
        private List<GroupBean> group;

        public List<MaterialBean> getMaterial() {
            return material;
        }

        public void setMaterial(List<MaterialBean> material) {
            this.material = material;
        }

        public List<GroupBean> getGroup() {
            return group;
        }

        public void setGroup(List<GroupBean> group) {
            this.group = group;
        }

        public static class MaterialBean {
            /**
             * material_id : 1
             * name : 测试材料
             * level : 0
             * type_id : 1
             * pid : -1
             */

            private String material_id;
            private String name;
            private String level;
            private String type_id;
            private String pid;

            public String getMaterial_id() {
                return material_id;
            }

            public void setMaterial_id(String material_id) {
                this.material_id = material_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }
        }

        public static class GroupBean {
            /**
             * group_id : G1
             * name : 测试组1
             */

            private String group_id;
            private String name;

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
