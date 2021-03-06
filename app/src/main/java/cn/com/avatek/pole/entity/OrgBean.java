package cn.com.avatek.pole.entity;

import java.util.ArrayList;
import java.util.List;

public class OrgBean {


    /**
     * orgname : 中国石油总公司
     * cid : 0
     * cuuid : 1
     * childs : [{"orgname":"京油二十九公司","cid":1,"cuuid":11,"childs":[{"orgname":"冶炼公司","cid":12,"cuuid":21,"childs":[{"orgname":"冶炼第一公司haha","cid":21,"cuuid":211,"childs":[]},{"orgname":"冶炼第二公司","cid":21,"cuuid":212,"childs":[]},{"orgname":"冶炼第三公司","cid":21,"cuuid":213,"childs":[]},{"orgname":"第四公司","cid":21,"cuuid":214,"childs":[]}]},{"orgname":"销售公司","cid":11,"cuuid":22,"childs":[{"orgname":"销售第一公司","cid":22,"cuuid":221,"childs":[{"orgname":"销售第一分公司","cid":221,"cuuid":2211,"childs":[]},{"orgname":"销售第一分公司晓得","cid":221,"cuuid":2212,"childs":[]},{"orgname":"销售第一分公司","cid":221,"cuuid":2213,"childs":[]},{"orgname":"一分公司","cid":221,"cuuid":2214,"childs":[]}]},{"orgname":"销售第二公司","cid":22,"cuuid":222,"childs":[]},{"orgname":"销售第三公司","cid":22,"cuuid":223,"childs":[{"orgname":"销售第三分公司","cid":223,"cuuid":2231,"childs":[]},{"orgname":"销售第三分公司","cid":223,"cuuid":2232,"childs":[]},{"orgname":"销售第三分公司","cid":223,"cuuid":2233,"childs":[]},{"orgname":"销售第三分公司","cid":223,"cuuid":2234,"childs":[]}]},{"orgname":"销售第四公司","cid":22,"cuuid":224,"childs":[]}]},{"orgname":"施工公司","cid":11,"cuuid":23,"childs":[{"orgname":"施工1公司","cid":23,"cuuid":231,"childs":[{"orgname":"11公司","cid":231,"cuuid":2311,"childs":[]},{"orgname":"施工12公司","cid":231,"cuuid":2312,"childs":[]},{"orgname":"施工13公司","cid":231,"cuuid":2313,"childs":[]},{"orgname":"施工14公司20191029","cid":231,"cuuid":2314,"childs":[]}]},{"orgname":"施工2公司","cid":23,"cuuid":232,"childs":[]},{"orgname":"施工3公司aaaaaaa","cid":23,"cuuid":233,"childs":[]},{"orgname":"施工4公司","cid":23,"cuuid":234,"childs":[]}]},{"orgname":"后勤保障运输公司","cid":11,"cuuid":24,"childs":[]}]},{"orgname":"京油二十八公司你懂得","cid":1,"cuuid":12,"childs":[]},{"orgname":"京油三十公司","cid":1,"cuuid":13,"childs":[]},{"orgname":"京油三十1公司","cid":1,"cuuid":14,"childs":[]},{"orgname":"京油三十2公司","cid":1,"cuuid":15,"childs":[]},{"orgname":"京油三十3公司","cid":1,"cuuid":16,"childs":[]},{"orgname":"京油三十4公司","cid":1,"cuuid":17,"childs":[]},{"orgname":"京油三十5公司","cid":1,"cuuid":18,"childs":[]},{"orgname":"三十6司","cid":1,"cuuid":19,"childs":[]}]
     */

    private String orgname;
    private int cid;
    private int cuuid;
    private String left;
    private String top;
    private List<OrgBean> childs = new ArrayList<>();

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCuuid() {
        return cuuid;
    }

    public void setCuuid(int cuuid) {
        this.cuuid = cuuid;
    }

    public List<OrgBean> getChilds() {
        return childs;
    }

    public void setChilds(List<OrgBean> childs) {
        this.childs = childs;
    }
}
