package demo.springframework.factory.surpport.earlybean;

public class Wife {
    private Husband husband;
    private IMother mother;

    public String queryHusband(){
        return "Wife.husband  Mother.callmother: " + mother.callmother();
    }

    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    public IMother getMother() {
        return mother;
    }

    public void setMother(IMother mother) {
        this.mother = mother;
    }
}
