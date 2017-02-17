import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TestBean {
    private TestInjectClass bean;   

    @Inject 
    TestBean(TestInjectClass bean) {
        this.bean = bean;
    }
    
    public String testMethod() {
        return bean.callInjectMethod();
    }

}
