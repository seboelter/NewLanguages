public class UtilitySquare extends BuyableSquare {
    protected double multiplierValue;

    UtilitySquare(int newSquareType, String newSquareName, double newRent, double newMultiplierValue)
    {
        super(newSquareType,newSquareName, newRent);
        multiplierValue = newMultiplierValue;



    }

    public double getMultiplierValue()
    {
        return multiplierValue;
    }
}
