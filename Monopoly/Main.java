import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //Create an instance of the game board
        Monopoly newGameBoard = new Monopoly();

        //read from the squares.txt file, and store the squares in the game board instance
        try {
            String line = "";
            String split = ",";
            String squares = "squares.txt";
            BufferedReader buff = new BufferedReader(new FileReader(squares));
            int counter = 0;
            while ((line = buff.readLine()) != null) {

                // use comma as separator
                String[] piece = line.split(split);
                //System.out.println(piece[0]);
                if(Integer.parseInt(piece[0]) == 1)
                {
                    //System.out.println(piece[0]);
                    newGameBoard.addSquare(counter,Integer.parseInt(piece[0]), piece[1]);

                    //newGameBoard.printBoard();
                }
                if(Integer.parseInt(piece[0]) == 2)
                {
                    newGameBoard.addProperty(counter,Integer.parseInt(piece[0]), piece[1],piece[2],
                            Double.parseDouble(piece[3]),Double.parseDouble(piece[4]),Double.parseDouble(piece[5]),
                            Double.parseDouble(piece[6]),Double.parseDouble(piece[7]),Double.parseDouble(piece[8]));
                }
                if(Integer.parseInt(piece[0]) == 3)
                {
                    newGameBoard.addTax(counter,Integer.parseInt(piece[0]), piece[1], Double.parseDouble(piece[2]));
                }
                if(Integer.parseInt(piece[0]) == 4)
                {
                    newGameBoard.addRailroad(counter,Integer.parseInt(piece[0]), piece[1],Double.parseDouble(piece[2]));
                }
                if(Integer.parseInt(piece[0]) == 5)
                {
                    newGameBoard.addUtility(counter,Integer.parseInt(piece[0]), piece[1], Double.parseDouble(piece[2]),
                            Double.parseDouble(piece[3]));
                }
                counter = counter +1;

            }
            newGameBoard.printBoard();

            String community = "community.txt";
            BufferedReader buff1 = new BufferedReader(new FileReader(community));
            while ((line = buff1.readLine()) != null)
            {
                String[] comm = line.split(split);
                newGameBoard.addCommunity(1,comm[0]);


            }
            String chance = "chance.txt";
            BufferedReader buff2 = new BufferedReader(new FileReader(chance));
            while ((line = buff2.readLine()) != null)
            {
                String[] comm = line.split(split);
                newGameBoard.addChance(2,comm[0]);


            }
            String pieces = "pieces.txt";
            BufferedReader buff3 = new BufferedReader(new FileReader(pieces));
            while ((line = buff3.readLine()) != null)
            {
                String[] comm = line.split(split);
                newGameBoard.addGamePiece(comm[0]);


            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("file not found");
        }
        catch(IOException e)
        {
            System.out.println("IOException");
        }

        System.out.println("Welcome to Monopoly");
        System.out.println("-------------------------------------");
        System.out.println("These are the current game pieces:");
        System.out.println("");
        newGameBoard.printGamePieces();
        System.out.println("");
        Scanner reader = new Scanner(System.in);
        int numPlayers = 0;
        while (2 > numPlayers || numPlayers > 5)
        {
            System.out.println("Enter the number of players between 2 and 5: ");
            numPlayers = reader.nextInt();

        }
        //reader.close();
        System.out.println("");
        int counter = 0;
        while(counter<numPlayers)
        {
            Scanner inText = new Scanner(System.in);
            System.out.println("Enter name of a player");
            String TextIn = inText.nextLine();
            newGameBoard.addPlayer(TextIn);
            System.out.println("");
            counter = counter +1;
        }
        int turnCounter = 0;
        while(turnCounter <10)
        {
            Scanner inEnter = new Scanner(System.in);
            System.out.println("Press enter to continue on to the next turn");
            String TextIn = inEnter.nextLine();
            newGameBoard.playerTurn();

            turnCounter = turnCounter+1;
        }

        System.out.println("Ten turns are up!");
        System.out.println("");
        System.out.println("---------------------------------");
        System.out.println("");
        newGameBoard.calcWinner();
    }


}
