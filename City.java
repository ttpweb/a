import java.util.ArrayList;

class City {
    boolean isTraveled;
    public ArrayList<Integer> toCities = new ArrayList<>();

    public City(boolean isTraveled) {
        this.isTraveled = isTraveled;
    }

    public void setIsTraveled(boolean isTraveled) {
        this.isTraveled = isTraveled;
    }

    public boolean getIsTraveled() {
        return isTraveled;
    }
}