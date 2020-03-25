package me.meet.pattern.design.action.state;

public class StateCase1 {

    static class State {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void method1() {
            System.out.println("execute the first opt!");
        }

        public void method2() {
            System.out.println("execute the second opt!");
        }
    }

    static class Context {

        private State state;

        public Context(State state) {
            this.state = state;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public void method() {
            if (state.getValue().equals("state1")) {
                state.method1();
            } else if (state.getValue().equals("state2")) {
                state.method2();
            }
        }
    }

    static void check1() {
        State state = new State();
        Context context = new Context(state);

        //设置第一种状态
        state.setValue("state1");
        context.method();

        //设置第二种状态
        state.setValue("state2");
        context.method();
    }
}
