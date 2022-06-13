package demo.springframework.factory.surpport.earlybean;

public class Husband {
    private Wife wife;
    public String queryWife(){
        return "Husband.wife";
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }
}
