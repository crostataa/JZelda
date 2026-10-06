package Model;

public class Rupee extends Entity{

    private int value; //1 la rupia verde; 5 la rupia blu

    public Rupee(double x, double y, int value) {
        super(x, y, 16, 24, 0); //how ?? *
        this.value = value;
    }

    @Override
    public void update() {

    }

    public int getValue() { return value; }
}
