public class PropertySquare extends BuyableSquare{
    //public int squareType;
    //public String squareName;
    protected String squareColor;
    protected double price;
    protected double rent;
    protected double priceOneHouse;
    protected double priceTwoHouse;
    protected double priceThreeHouse;
    protected double priceFourHouse;
    protected int houseNum;


    PropertySquare(int newSquareType, String newSquareName, String newSquareColor, double newPrice,
            double newRent, double newPriceOneHouse, double newPriceTwoHouse, double newPriceThreeHouse,
                   double newPriceFourHouse)
    {
        super(newSquareType,newSquareName,newRent);
        squareColor = newSquareColor;
        price = newPrice;
        //rent = newRent;
        priceOneHouse = newPriceOneHouse;
        priceTwoHouse = newPriceTwoHouse;
        priceThreeHouse = newPriceThreeHouse;
        priceFourHouse = newPriceFourHouse;
        houseNum =0;

    }
    public String getSquareColor()
    {
        return squareColor;
    }
    public double getPrice()
    {
        return price;
    }
    public double getPriceOneHouse()
    {
        return priceOneHouse;
    }
    public double getPriceTwoHouse()
    {
        return priceTwoHouse;
    }
    public double getPriceThreeHouse()
    {
        return priceThreeHouse;
    }
    public double getPriceFourHouse()
    {
        return priceFourHouse;
    }
    public int getHouseNum()
    {
        return houseNum;

    }
}
