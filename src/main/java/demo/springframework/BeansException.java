package demo.springframework;

public class BeansException extends RuntimeException {
    public BeansException(){
        super();
    }

    public BeansException(String msg){
        super(msg);
    }

    public BeansException(String s, Throwable e) {
        super(s,e);
    }
}
