package demo.springframework.beans;

public class PropertyValue {
    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

}
