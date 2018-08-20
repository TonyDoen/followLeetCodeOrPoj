package me.meet.pattern.design.structure.facade;

public class ModuleA {
    //示意方法, 提供给子系统外部使用的方法
    public void testA() {
        System.out.println("调用ModuleA中的testA方法");
    }

    /**
     * 子系统内部模块之间相互调用时使用的方法
     */
    private void a2() {
    }

    private void a3() {
    }
}
