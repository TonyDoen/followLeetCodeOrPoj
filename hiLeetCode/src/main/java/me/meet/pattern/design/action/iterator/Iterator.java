package me.meet.pattern.design.action.iterator;

public interface Iterator {
    //前移  
    Object previous();
      
    //后移  
    Object next();
      
    //取得第一个元素  
    Object first();

    boolean hasNext();

} 