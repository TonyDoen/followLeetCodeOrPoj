package me.meet.pattern.design.structure.facade;

public class ModuleB {
    //示意方法, 提供给子系统外部使用的方法
    public void testB() {
        System.out.println("调用ModuleB中的testB方法");
    }

    /**
     * 子系统内部模块之间相互调用时使用的方法
     */
    private void b2() {
    }

    private void b3() {
    }
}