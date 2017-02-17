import javax.inject.Named;

@Named
public class TestInjectClass {
    public String callInjectMethod() {
        return "inject success";
    }

}
