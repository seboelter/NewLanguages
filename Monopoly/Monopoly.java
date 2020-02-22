import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Monopoly {
    protected ArrayList<Square> gameBoard = new ArrayList<Square>();
    protected List<Player> players = new ArrayList<>();
    protected List<String> pieces = new ArrayList<>();
    protected List<Card> CommunityChest = new ArrayList<>();
    protected List<Card> Chance = new ArrayList<>();
    protected int playerTurn = 0;

    Monopoly() {

    }

    public void calcWinner()
    {
        int len = players.size();
        int c = 0;
        double winner = 0;
        Player win = new Player();
        while(c < len)
        {
            System.out.println(players.get(c).getPlayerName() + " has " + players.get(c).getMoney()+ " dollars!");
            if(players.get(c).getMoney() > winner)
            {
               win = players.get(c);
               winner = players.get(c).getMoney();
            }

            c = c+1;
        }
        System.out.println(win.getPlayerName() + " is the winner!");
    }

    public void addSquare(int counter, int newSquareType, String newSquareName)
    //public void addSquare(Square sq)
    {
        Square newSquare = new Square(newSquareType, newSquareName);
        gameBoard.add(counter, newSquare);
    }

    public void addUtility(int counter, int newSquareType, String newSquareName, double newRent, double newMultiplierValue) {
        UtilitySquare newU = new UtilitySquare(newSquareType, newSquareName, newRent, newMultiplierValue);
        gameBoard.add(counter, newU);
    }

    public void addProperty(int counter, int newSquareType, String newSquareName, String newSquareColor, double newPrice,
                            double newRent, double newPriceOneHouse, double newPriceTwoHouse, double newPriceThreeHouse,
                            double newPriceFourHouse) {
        PropertySquare newP = new PropertySquare(newSquareType, newSquareName, newSquareColor, newPrice, newRent,
                newPriceOneHouse, newPriceTwoHouse, newPriceThreeHouse, newPriceFourHouse);
        gameBoard.add(counter, newP);

    }

    public void addTax(int counter, int newSquareType, String newSquareName, double newTax) {
        Square newT = new TaxSquare(newSquareType, newSquareName, newTax);
        gameBoard.add(counter, newT);
    }

    public void addRailroad(int counter, int newSquareType, String newSquareName, double newRent) {
        RailroadSquare newR = new RailroadSquare(newSquareType, newSquareName, newRent);
        gameBoard.add(counter, newR);
    }

    public void addCommunity(int type, String message) {
        Card c = new Card(type, message);
        CommunityChest.add(c);
    }

    public void addChance(int type, String message) {
        Card c = new Card(type, message);
        Chance.add(c);
    }

    public void addGamePiece(String gamePiece) {
        pieces.add(gamePiece);

    }

    public void printGamePieces() {
        System.out.println(pieces.toString());

    }

    public void addPlayer(String name) {
        Player player = new Player(name, pieces.get(0));
        players.add(player);
        System.out.println(name + " is the " + pieces.get(0) + "!");
        pieces.remove(0);

    }

    public int rollDice() {
        Random rand = new Random();
        int diceRoll = rand.nextInt(5);
        int diceRoll1 = rand.nextInt(5);
        return (diceRoll + diceRoll1 + 2);
    }
    public void printBoard ()
    {


        //System.out.println(gameBoard.size());
        int i = 0;
        while (i < gameBoard.size()) {
            System.out.println(gameBoard.get(i).getName());
            i = i + 1;
        }
        //System.out.println(gameBoard.size());
    }

    public void playerTurn() {
        if (playerTurn > (players.size() - 1)) {
            playerTurn = 0;
        }
        if (players.get(playerTurn).areYouInJail() == 0) {

            System.out.println("It's " + players.get(playerTurn).getPlayerName() + "'s turn!");
            System.out.println("The " + players.get(playerTurn).getPlayerPiece() + " is currently on " +
                    gameBoard.get(players.get(playerTurn).getBoardPosition()).getName());
            System.out.println(players.get(playerTurn).getPlayerName() + " has " + players.get(playerTurn).getMoney()
                    + " dollars.");
            //roll dice

            int numMoves = rollDice();
            System.out.println(players.get(playerTurn).getPlayerName() + " rolled a " + numMoves + "!");
            int currPos = players.get(playerTurn).getBoardPosition();
            int newPos = players.get(playerTurn).getBoardPosition() + numMoves;
            //moving position

            if (newPos > (gameBoard.size() - 1)) {
                int difference = (gameBoard.size() - 1) - currPos;
                numMoves = numMoves - difference;
                players.get(playerTurn).setBoardPosition(numMoves - 1);
                System.out.println("You passed GO, collect 200 dollars!");
                newPos = numMoves-1;

                players.get(playerTurn).addMoney(200);
            } else {
                players.get(playerTurn).setBoardPosition(newPos);

            }

            System.out.println("The " + players.get(playerTurn).getPlayerPiece() + " moved to " +
                    gameBoard.get(players.get(playerTurn).getBoardPosition()).getName() + "!");

            //Property
            if (gameBoard.get(players.get(playerTurn).getBoardPosition()).getType() == 2) {
                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {
                    System.out.println("Would you like to buy a house on your property?");
                    System.out.println("Press 1 for yes, 2 for no.");
                    Scanner inEnter = new Scanner(System.in);
                    int TextIn = inEnter.nextInt();
                    if (TextIn == 1) {
                        int prop = ((PropertySquare)(gameBoard.get(newPos))).getHouseNum();
                        if (prop == 0)
                        {
                            int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceOneHouse();
                            int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                            ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);
                        }
                        if(prop ==1)
                        {
                            int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceTwoHouse();
                            int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                            ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);
                        }
                        if(prop == 2)
                        {
                            int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceThreeHouse();
                            int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                            ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);

                        }
                        if(prop == 3)
                        {
                            int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceFourHouse();
                            int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                            ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);

                        }


                    }


                }
                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                    System.out.println("Would you like to buy this property?");
                    System.out.println("Enter 1 for yes, 2 for no.");
                    Scanner inEnter = new Scanner(System.in);
                    int TextIn = inEnter.nextInt();
                    if (TextIn == 1) {
                        if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(newPos))).getRent()) {
                            //String NewOwner = players.get(playerTurn).getPlayerName();
                            ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                            int price = (int)((PropertySquare)gameBoard.get(newPos)).getPrice();
                            players.get(playerTurn).subMoney(price);

                            System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                    + gameBoard.get(newPos).getName());

                        } else {
                            System.out.println("You don't have enough money to buy this property");
                        }
                    } else {

                    }
                }
                 else {
                    System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                            + " owns this square " +
                            "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                    int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                    int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                    //int index = players.get(owner);
                    players.get(playerTurn).subMoney(rent);
                    players.get(owner).addMoney(rent);
                }


            }
            //Utility
            if (gameBoard.get(players.get(playerTurn).getBoardPosition()).getType() == 5) {
                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                    System.out.println("Would you like to buy this Utility?");
                    System.out.println("Press 1 for yes, press 2 for no.");
                    Scanner inEnter = new Scanner(System.in);
                    int TextIn = inEnter.nextInt();
                    if (TextIn == 1) {
                        if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                            ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                            int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                            players.get(playerTurn).subMoney(price);

                            System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                    + gameBoard.get(newPos).getName());
                        } else {
                            System.out.println("You don't have enough money to buy this Utility");
                        }
                    } else {

                    }
                }
                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                    //Nothing


                } else {
                    System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                            + " owns this square " +
                            "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                    int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                    int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                    //int index = players.get(owner);
                    players.get(playerTurn).subMoney(rent);
                    players.get(owner).addMoney(rent);

                }


            }
            //Railroad
            if (gameBoard.get(players.get(playerTurn).getBoardPosition()).getType() == 4) {

                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                    System.out.println("Would you like to buy this Railroad?");
                    System.out.println("Press 1 for yes, press 2 for no.");
                    Scanner inEnter = new Scanner(System.in);
                    int TextIn = inEnter.nextInt();
                    if (TextIn == 1) {
                        if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                            ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                            int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                            players.get(playerTurn).subMoney(price);

                            System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                    + gameBoard.get(newPos).getName());
                        } else {
                            System.out.println("You don't have enough money to buy this Utility");
                        }
                    } else {

                    }
                }
                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                    //Nothing


                } else {
                    System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                            + " owns this square " +
                            "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                    int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                    int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                    //int index = players.get(owner);
                    players.get(playerTurn).subMoney(rent);
                    players.get(owner).addMoney(rent);

                }

            }
            //Taxes
            if (gameBoard.get(players.get(playerTurn).getBoardPosition()).getType() == 3) {
                if (players.get(playerTurn).getBoardPosition() == 4) {
                    System.out.println(players.get(playerTurn).getPlayerName() +
                            " has to pay 200 dollars in Income Tax.");
                    players.get(playerTurn).subMoney(200);

                }
                if (players.get(playerTurn).getBoardPosition() == 38) {
                    System.out.println(players.get(playerTurn).getPlayerName() +
                            " has to pay 75 dollars in Luxury Tax.");
                    players.get(playerTurn).subMoney(75);
                }

            }

            //Chance, Go, and Community Chest
            if ((gameBoard.get(players.get(playerTurn).getBoardPosition()).getType()) == 1) {
                Random cardPicker = new Random();

                if (newPos == 0) {

                }
                if (newPos == 10)
                {
                    System.out.println("Just Visiting");
                }
                if(newPos == 20)
                {
                    System.out.println("Free Parking");
                }
                if(newPos == 30)
                {
                    System.out.println("Go to Jail, lose next turn");
                    newPos = 10;
                    players.get(playerTurn).setBoardPosition(10);
                    players.get(playerTurn).jailTime(1);
                    return;
                }

                if ((newPos) == 2 ||(newPos) == 17 || newPos == 33) {

                        System.out.println("Drawing a card from the " +
                                gameBoard.get(players.get(playerTurn).getBoardPosition()).getName());
                        Random rand = new Random();
                        int diceRoll = rand.nextInt(4);
                        //System.out.println(diceRoll);
                        //Take a Walk on the BoardWalk
                        if (diceRoll == 0) {
                            System.out.println("Take a Walk on the BoardWalk");
                            newPos =39;
                            players.get(playerTurn).setBoardPosition(newPos);
                            if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {
                                System.out.println("Would you like to buy a house on your property?");
                                System.out.println("Press 1 for yes, 2 for no.");
                                Scanner inEnter = new Scanner(System.in);
                                int TextIn = inEnter.nextInt();
                                if (TextIn == 1) {
                                    int prop = ((PropertySquare)(gameBoard.get(newPos))).getHouseNum();
                                    if (prop == 0)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceOneHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);
                                    }
                                    if(prop ==1)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceTwoHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);
                                    }
                                    if(prop == 2)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceThreeHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);

                                    }
                                    if(prop == 3)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceFourHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);

                                    }


                                }


                            }
                            if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                System.out.println("Would you like to buy this property?");
                                System.out.println("Enter 1 for yes, 2 for no.");
                                Scanner inEnter = new Scanner(System.in);
                                int TextIn = inEnter.nextInt();
                                if (TextIn == 1) {
                                    if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(newPos))).getRent()) {
                                        //String NewOwner = players.get(playerTurn).getPlayerName();
                                        ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                        int price = (int)((PropertySquare)gameBoard.get(newPos)).getPrice();
                                        players.get(playerTurn).subMoney(price);

                                        System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                + gameBoard.get(newPos).getName());

                                    } else {
                                        System.out.println("You don't have enough money to buy this property");
                                    }
                                } else {

                                }
                            }
                            else {
                                System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                        + " owns this square " +
                                        "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                //int index = players.get(owner);
                                players.get(playerTurn).subMoney(rent);
                                players.get(owner).addMoney(rent);
                            }

                        }
                        //Advance to Illinois Avenue
                        if (diceRoll == 1) {
                            System.out.println("Advance to Illinois Ave");
                            newPos =24;
                            players.get(playerTurn).setBoardPosition(newPos);
                            if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {
                                System.out.println("Would you like to buy a house on your property?");
                                System.out.println("Press 1 for yes, 2 for no.");
                                Scanner inEnter = new Scanner(System.in);
                                int TextIn = inEnter.nextInt();
                                if (TextIn == 1) {
                                    int prop = ((PropertySquare)(gameBoard.get(newPos))).getHouseNum();
                                    if (prop == 0)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceOneHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);
                                    }
                                    if(prop ==1)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceTwoHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);
                                    }
                                    if(prop == 2)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceThreeHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);

                                    }
                                    if(prop == 3)
                                    {
                                        int price =(int)((PropertySquare)(gameBoard.get(newPos))).getPriceFourHouse();
                                        int currRent =(int)((PropertySquare)(gameBoard.get(newPos))).getRent();
                                        ((PropertySquare)(gameBoard.get(newPos))).setRent(price+currRent);

                                    }


                                }


                            }
                            if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                System.out.println("Would you like to buy this property?");
                                System.out.println("Enter 1 for yes, 2 for no.");
                                Scanner inEnter = new Scanner(System.in);
                                int TextIn = inEnter.nextInt();
                                if (TextIn == 1) {
                                    if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(newPos))).getRent()) {
                                        //String NewOwner = players.get(playerTurn).getPlayerName();
                                        ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                        int price = (int)((PropertySquare)gameBoard.get(newPos)).getPrice();
                                        players.get(playerTurn).subMoney(price);

                                        System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                + gameBoard.get(newPos).getName());

                                    } else {
                                        System.out.println("You don't have enough money to buy this property");
                                    }
                                } else {

                                }
                            }
                            else {
                                System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                        + " owns this square " +
                                        "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                //int index = players.get(owner);
                                players.get(playerTurn).subMoney(rent);
                                players.get(owner).addMoney(rent);
                            }

                        }

                        //Advance to Nearest Utility
                        if (diceRoll == 2) {
                            if (newPos == 7) {
                                System.out.println("Advance to nearest Utility");
                                newPos = 12;
                                players.get(playerTurn).setBoardPosition(12);
                                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                    System.out.println("Would you like to buy this Utility?");
                                    System.out.println("Press 1 for yes, press 2 for no.");
                                    Scanner inEnter = new Scanner(System.in);
                                    int TextIn = inEnter.nextInt();
                                    if (TextIn == 1) {
                                        if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                                            ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                            int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                            players.get(playerTurn).subMoney(price);

                                            System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                    + gameBoard.get(newPos).getName());
                                        } else {
                                            System.out.println("You don't have enough money to buy this Utility");
                                        }
                                    } else {

                                    }
                                }
                                if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                                    //Nothing


                                } else {
                                    System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                            + " owns this square " +
                                            "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                    int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                    int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                    //int index = players.get(owner);
                                    players.get(playerTurn).subMoney(rent);
                                    players.get(owner).addMoney(rent);

                                }
                                }
                                if (newPos == 22) {
                                    newPos = 28;
                                    players.get(playerTurn).setBoardPosition(28);
                                    System.out.println("Advance to nearest Utility");

                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                        System.out.println("Would you like to buy this Utility?");
                                        System.out.println("Press 1 for yes, press 2 for no.");
                                        Scanner inEnter = new Scanner(System.in);
                                        int TextIn = inEnter.nextInt();
                                        if (TextIn == 1) {
                                            if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                                                ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                                int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                                players.get(playerTurn).subMoney(price);

                                                System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                        + gameBoard.get(newPos).getName());
                                            } else {
                                                System.out.println("You don't have enough money to buy this Utility");
                                            }
                                        } else {

                                        }
                                    }
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                                        //Nothing


                                    } else {
                                        System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                                + " owns this square " +
                                                "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                        int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                        int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                        //int index = players.get(owner);
                                        players.get(playerTurn).subMoney(rent);
                                        players.get(owner).addMoney(rent);

                                    }
                                }
                                if (newPos == 36) {
                                    newPos = 12;
                                    players.get(playerTurn).setBoardPosition(12);
                                    System.out.println("Advance to nearest Utility");
                                    System.out.println("You passed GO! Collect 200 dollars!");
                                    players.get(playerTurn).addMoney(200);
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                        System.out.println("Would you like to buy this Utility?");
                                        System.out.println("Press 1 for yes, press 2 for no.");
                                        Scanner inEnter = new Scanner(System.in);
                                        int TextIn = inEnter.nextInt();
                                        if (TextIn == 1) {
                                            if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                                                ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                                int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                                players.get(playerTurn).subMoney(price);

                                                System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                        + gameBoard.get(newPos).getName());
                                            } else {
                                                System.out.println("You don't have enough money to buy this Utility");
                                            }
                                        } else {

                                        }
                                    }
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                                        //Nothing


                                    } else {
                                        System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                                + " owns this square " +
                                                "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                        int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                        int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                        //int index = players.get(owner);
                                        players.get(playerTurn).subMoney(rent);
                                        players.get(owner).addMoney(rent);

                                    }

                                }


                            //Advance to Nearest Railroad
                            if (diceRoll == 3) {
                                if (newPos == 7) {
                                    players.get(playerTurn).setBoardPosition(15);
                                    System.out.println("Advance to nearest Railroad");
                                    newPos = 15;

                                    players.get(playerTurn).addMoney(200);
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                        System.out.println("Would you like to buy this Railroad?");
                                        System.out.println("Press 1 for yes, press 2 for no.");
                                        Scanner inEnter = new Scanner(System.in);
                                        int TextIn = inEnter.nextInt();
                                        if (TextIn == 1) {
                                            if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                                                ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                                int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                                players.get(playerTurn).subMoney(price);

                                                System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                        + gameBoard.get(newPos).getName());
                                            } else {
                                                System.out.println("You don't have enough money to buy this Utility");
                                            }
                                        } else {

                                        }
                                    }
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                                        //Nothing


                                    } else {
                                        System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                                + " owns this square " +
                                                "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                        int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                        int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                        //int index = players.get(owner);
                                        players.get(playerTurn).subMoney(rent);
                                        players.get(owner).addMoney(rent);

                                    }

                                }
                                if (newPos == 22) {
                                    players.get(playerTurn).setBoardPosition(25);
                                    System.out.println("Advance to nearest Railroad");
                                    newPos = 25;

                                    players.get(playerTurn).addMoney(200);
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                        System.out.println("Would you like to buy this Railroad?");
                                        System.out.println("Press 1 for yes, press 2 for no.");
                                        Scanner inEnter = new Scanner(System.in);
                                        int TextIn = inEnter.nextInt();
                                        if (TextIn == 1) {
                                            if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                                                ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                                int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                                players.get(playerTurn).subMoney(price);

                                                System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                        + gameBoard.get(newPos).getName());
                                            } else {
                                                System.out.println("You don't have enough money to buy this Utility");
                                            }
                                        } else {

                                        }
                                    }
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                                        //Nothing


                                    } else {
                                        System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                                + " owns this square " +
                                                "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                        int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                        int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                        //int index = players.get(owner);
                                        players.get(playerTurn).subMoney(rent);
                                        players.get(owner).addMoney(rent);

                                    }

                                }
                                if (newPos == 36) {
                                    players.get(playerTurn).setBoardPosition(5);
                                    System.out.println("Advance to nearest Railroad");
                                    System.out.println("You passed GO! Collect 200 dollars!");
                                    players.get(playerTurn).addMoney(200);
                                    newPos = 5;

                                    players.get(playerTurn).addMoney(200);
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == -1) {
                                        System.out.println("Would you like to buy this Railroad?");
                                        System.out.println("Press 1 for yes, press 2 for no.");
                                        Scanner inEnter = new Scanner(System.in);
                                        int TextIn = inEnter.nextInt();
                                        if (TextIn == 1) {
                                            if (players.get(playerTurn).getMoney() >= ((BuyableSquare) (gameBoard.get(1))).getRent()) {
                                                ((BuyableSquare)gameBoard.get(newPos)).setSquareOwner(playerTurn);
                                                int price = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                                players.get(playerTurn).subMoney(price);

                                                System.out.println(players.get(playerTurn).getPlayerName() + " now owns "
                                                        + gameBoard.get(newPos).getName());
                                            } else {
                                                System.out.println("You don't have enough money to buy this Utility");
                                            }
                                        } else {

                                        }
                                    }
                                    if (((BuyableSquare) (gameBoard.get(newPos))).getSquareOwner() == playerTurn) {

                                        //Nothing


                                    } else {
                                        System.out.println(players.get(((BuyableSquare)gameBoard.get(newPos)).getSquareOwner()).getPlayerName()
                                                + " owns this square " +
                                                "please pay them " +((BuyableSquare)gameBoard.get(newPos)).getRent());
                                        int rent = (int)((BuyableSquare)gameBoard.get(newPos)).getRent();
                                        int owner = ((BuyableSquare)gameBoard.get(newPos)).getSquareOwner();
                                        //int index = players.get(owner);
                                        players.get(playerTurn).subMoney(rent);
                                        players.get(owner).addMoney(rent);

                                    }


                                }

                            }
                            //You won the Lottery
                            if (diceRoll == 4) {
                                System.out.println("You won the lottery, collect 1000 dollars");
                                players.get(playerTurn).addMoney(1000);
                            }

                        }}
                        if((newPos) == 7 ||(newPos) == 22 || newPos == 36)
                            {
                            System.out.println("Drawing a card from the " +
                                    gameBoard.get(players.get(playerTurn).getBoardPosition()).getName());
                            Random rand1 = new Random();
                            int diceRoll1 = rand1.nextInt(4);
                            //Advance to Go
                            if (diceRoll1 == 0) {
                                System.out.println("Advance to Go, Collect 200 dollars!");
                                players.get(playerTurn).setBoardPosition(0);
                                players.get(playerTurn).addMoney(200);
                            }
                            if (diceRoll1 == 1) {
                                System.out.println("Doctors Fees, Pay 100 dollars");
                                players.get(playerTurn).subMoney(100);

                            }
                            if (diceRoll1 == 2) {
                                System.out.println("Go to Jail, skip next turn");
                                players.get(playerTurn).jailTime(1);
                            }
                            if (diceRoll1 == 3) {
                                System.out.println("School Fees, Pay 200 dollars");
                                players.get(playerTurn).subMoney(200);

                            }
                            if (diceRoll1 == 4) {
                                System.out.println("PFD! Collect 350 dollars");
                                players.get(playerTurn).addMoney(350);

                            }
                        }

                    }
            }


        else {
            System.out.println("You are in Jail, Skip Turn.");
            players.get(playerTurn).jailTime(0);
        }
        playerTurn = playerTurn + 1;
        System.out.println("");
        System.out.println("------------------------------");

    }

}