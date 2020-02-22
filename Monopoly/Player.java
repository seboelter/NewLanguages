public class Player
{
    protected String playerName;
    protected String playerPiece;
    protected double money;
    protected int boardPosition;
    private int jailTime;
    Player()
    {

    }
    Player(String newPlayerName, String newPlayerPiece)
    {
        playerName = newPlayerName;
        playerPiece = newPlayerPiece;
        money = 1500;
        boardPosition = 0;
        jailTime = 0;

    }
    public String getPlayerName()
    {
      return playerName;
    }
    public int getBoardPosition()
    {
        return boardPosition;
    }
    public void setBoardPosition(int newPos)
    {
        boardPosition = newPos;

    }

    public void addMoney(int moolah)
    {
        money = money + moolah;
    }
    public void subMoney(int moolah)
    {
        money = money- moolah;
    }
    public void jailTime(int jail)
    {
        jailTime = jail;
    }
    public int areYouInJail()
    {
        return jailTime;
    }
    public String getPlayerPiece()
    {
        return playerPiece;
    }
    public double getMoney()
    {
        return money;
    }

}
