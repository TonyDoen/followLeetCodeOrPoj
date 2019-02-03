package me.meet.pattern.design.structure.composite;

import java.util.ArrayList;
import java.util.List;

public abstract class Organization {

    private List<Organization> childOrg = new ArrayList<Organization>();
    private String             name;

    public Organization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addOrg(Organization org) {
        childOrg.add(org);
    }

    public void removeOrg(Organization org) {
        childOrg.remove(org);
    }

    public List<Organization> getAllOrgs() {
        return childOrg;
    }

    public abstract void inform(String info);

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object org) {
        if (!(org instanceof Organization)) {
            return false;
        }
        return this.name.equals(((Organization) org).name);
    }
}