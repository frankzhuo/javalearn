package t5_frame;

public class UserServiceImpl implements UserService{
    public String getName() {
        System.out.println("getName");
        return "AAA";
    }

    public String getAge() {
        this.getName();
        System.out.println("getAge");
        return "23";
    }
}
