package demo.springframework.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    private String[] configLocations;

    public ClassPathXmlApplicationContext(){

    }

    public ClassPathXmlApplicationContext(String configLocations) {
        this.configLocations = new String[]{configLocations};
        refresh();
    }

    @Override
    protected String[] getConfiglocations() {
        return configLocations;
    }
}
