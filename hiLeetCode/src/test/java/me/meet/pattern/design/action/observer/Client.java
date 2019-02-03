package me.meet.pattern.design.action.observer;

public class Client {
  /**
   * 观察者模式定义
   * 观察者模式又叫发布-订阅模式，它定义了一种一对多的依赖关系，多个观察者对象可同时监听某一主题对象，当该主题对象状态发生变化时，相应的所有观察者对象都可收到通知。
   *
   * 观察者模式角色划分
   * 主题，抽象类或接口， 如上面类图中的 AbstractHR
   * 具体主题，          如上面类图中的 HeadHunter
   * 观察者，            如上面类图中的 ITalent
   * 具体观察者，        如上面类图中的 Architect，JuniorEngineer，SeniorEngineer
   *
   *
   * 观察者模式优点
   * 抽象主题只依赖于抽象观察者
   * 观察者模式支持广播通信
   * 观察者模式使信息产生层和响应层分离
   *
   * 观察者模式缺点
   * 如一个主题被大量观察者注册，则通知所有观察者会花费较高代价
   * 如果某些观察者的响应方法被阻塞，整个通知过程即被阻塞，其它观察者不能及时被通知
   *
   *
   * 已遵循的原则
   * 依赖倒置原则（主题类依赖于抽象观察者而非具体观察者）
   * 迪米特法则
   * 里氏替换原则
   * 接口隔离原则
   * 单一职责原则
   * 开闭原则
   *
   *
   */
  public static void main(String[] args) {

        ITalent juniorEngineer = new JuniorEngineer();
        ITalent seniorEngineer = new SeniorEngineer();
        ITalent architect = new Architect();

        AbstractHR subject = new HeadHunter();
        subject.addTalent(juniorEngineer);
        subject.addTalent(seniorEngineer);
        subject.addTalent(architect);

        subject.publishJob("Top 500 big data position");
    }
}