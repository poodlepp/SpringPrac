package demo.springframework.factory.config;

public class BeanDenition {
    private Class clazz;

    public BeanDenition(Class clazz){
        this.clazz = clazz;
    }

    public Class getClazz() {
        return this.clazz;
    }
}
