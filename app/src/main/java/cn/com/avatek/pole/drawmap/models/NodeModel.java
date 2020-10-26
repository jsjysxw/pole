package cn.com.avatek.pole.drawmap.models;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by owant on 09/02/2017.
 */
public class NodeModel<T> implements Serializable {



    /**
     * the parent node,if root node parent node=null;
     */
    public NodeModel<T> parentNode;

    /**
     * the data value
     */
    public T value;

    /**
     * have the child nodes
     */
    public LinkedList<NodeModel<T>> childNodes;

    /**
     * focus tag for the tree add nodes
     */
    public transient boolean focus = false;

    /**
     * index of the tree floor
     */
    public int floor;
    public int coorl;
    public int coort;
    public int coorr;
    public int coorb;

    public boolean hidden = false;

    public NodeModel(T value) {
        this.value = value;
        this.childNodes = new LinkedList<>();

        this.focus = false;
        this.parentNode = null;
    }

    public NodeModel<T> getParentNode() {
        return parentNode;
    }

    public void setParentNode(NodeModel<T> parentNode) {
        this.parentNode = parentNode;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkedList<NodeModel<T>> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(LinkedList<NodeModel<T>> childNodes) {
        this.childNodes = childNodes;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getCoorl() {
        return coorl;
    }

    public void setCoorl(int coorl) {
        this.coorl = coorl;
    }

    public int getCoort() {
        return coort;
    }

    public void setCoort(int coort) {
        this.coort = coort;
    }

    public int getCoorr() {
        return coorr;
    }

    public void setCoorr(int coorr) {
        this.coorr = coorr;
    }

    public int getCoorb() {
        return coorb;
    }

    public void setCoorb(int coorb) {
        this.coorb = coorb;
    }
}
