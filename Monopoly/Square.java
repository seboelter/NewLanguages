public class Square {

    protected int squareType;
    protected String squareName;
    protected String ownedBy = "";
    {

    }

    Square(int newSquareType, String newSquareName)
    {
        squareType = newSquareType;
        squareName = newSquareName;

    }
    public int getType()
    {
        return squareType;
    }

    public String getName()
    {
        return squareName;
    }

    public void printName()
    {
        System.out.println(squareName);
    }




}
