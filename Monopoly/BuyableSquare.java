public class BuyableSquare extends Square {

    protected double rent;
    private int squareOwner = -1;


    BuyableSquare(int newSquareType, String newSquareName,double newRent)
    {
        super(newSquareType,newSquareName);
        rent = newRent;

    }

    public int getSquareOwner()
    {
        return squareOwner;
    }
    public double getRent()
    {
        return rent;
    }
    public void setRent(int r){
        rent = r;
    }
    public void setSquareOwner(int sqOwner)
    {
        squareOwner = sqOwner;
    }

}

