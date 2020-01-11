package me.meet.pattern.design.creation.prototype;

import java.util.ArrayList;

public class Prototype implements Cloneable {
    private ArrayList list = new ArrayList();

    public ArrayList getList() {
        return this.list;
    }

    public Prototype clone() {
        Prototype prototype = null;
        try {
            prototype = (Prototype) super.clone();
            prototype.list = (ArrayList) this.list.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return prototype;
    }
}
