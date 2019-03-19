package Server.Model;

public class Ship {
    private String Ship1;
    private String Ship2;

    public Ship() {
    }
    public Ship(String Ship1,String Ship2) {
        this.Ship1 = Ship1;
        this.Ship2 = Ship2;
    }

    public String getShip1() {
        return Ship1;
    }

    public void setShip1(String ship1) {
        Ship1 = ship1;
    }

    public String getShip2() {
        return Ship2;
    }

    public void setShip2(String ship2) {
        Ship2 = ship2;
    }
    @Override
    public String toString(){
        return "Ship1:"+Ship1+",  Ship2:"+Ship2;
    }
}
