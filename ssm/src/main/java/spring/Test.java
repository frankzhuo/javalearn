package spring;

public class Test {
    int i;
    Foo foo;

    public Test(int i, Foo foo) {
        this.i = i;
        this.foo = foo;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }
}
