public class TaxSquare extends Square {

    protected double tax;


    TaxSquare(int newSquareType, String newSquareName,double newTax)
    {
            super(newSquareType,newSquareName);
        tax = newTax;
    }
    public double getTax()
    {
        return tax;
    }
}
