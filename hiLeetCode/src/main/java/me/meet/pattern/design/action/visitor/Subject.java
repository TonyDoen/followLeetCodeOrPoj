package me.meet.pattern.design.action.visitor;

public interface Subject {
    void accept(Visitor visitor);

    String getSubject();
} 