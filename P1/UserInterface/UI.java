package UserInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Database.DB;
import Database.club;
import Database.country;
import Database.player;

public class UI {
    private static final String FILE_NAME = "players.txt";

    int state;
    DB data;
    Scanner sc;

    public UI()
    {
        state = -2025;
        this.data = new DB();
        sc = new Scanner(System.in);
    }

    public void program()throws Exception
    {
        input();
        while(state!=4)
        {
            System.out.println("Main menu:");
            System.out.println("(1) Search Players");
            System.out.println("(2) Search Clubs");
            System.out.println("(3) Add Player");
            System.out.println("(4) Exit System");
            
            while(true)
            {
                System.out.print("\nCommand: ");
                Integer buffer = userIntegerInput();
                if(buffer!=null)
                {
                    state = buffer;
                    break;
                }
            }
            
            System.out.println();

            switch(state)
            {
                case 1:
                    searchPlayersMenu();
                    break;
                case 2:
                    searchClubsMenu();
                    break;
                case 3:
                    addPlayerMenu();
                    break;
                case 4:
                    data.writeToFile(FILE_NAME);
                    sc.close();
                    break;
                default:
                    System.out.println("--- Invalid input (Valid options: 1-4) ---");
                    break;
            }
        }
    }


    void searchPlayersMenu()
    {
        while(state!=6)
        {
            System.out.println("Player Searching Options:");
            System.out.println("(1) By Player Name");
            System.out.println("(2) By Club and Country");
            System.out.println("(3) By Position");
            System.out.println("(4) By Salary Range");
            System.out.println("(5) Country-wise player count");
            System.out.println("(6) Back to Main Menu");

            while(true)
            {
                System.out.print("\nCommand: ");
                Integer buffer = userIntegerInput();
                if(buffer!=null)
                {
                    state = buffer;
                    break;
                }
            }
            System.out.println();

            switch(state)
            {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    data.playerSearch(name);
                    break;
                case 2:
                    System.out.print("Enter Club Name: ");
                    String clubName = sc.nextLine();
                    System.out.print("Enter Country: ");
                    String countryName = sc.nextLine();
                    data.searchClubCountry(clubName, countryName);
                    break;
                case 3:
                    String[] positions = {"batsman", "bowler", "wicketkeeper", "allrounder"};
                    String positionName;
                    while(true)
                    {
                        System.out.print("Enter Position (Batsman, Bowler, Wicketkeeper, Allrounder): ");
                        positionName = sc.nextLine();
                        boolean found = false;
                        for(String temp: positions)
                        {
                            if(positionName.equalsIgnoreCase(temp))
                            {
                                found = true;
                                break;
                            }
                        }
                        if(found) break;
                        System.out.println("--- Not a valid position ---");
                    }
                    data.searchByPosition(positionName);
                    break;
                case 4:
                    System.out.println("Enter Salary Range:");
                    int lsalary;
                    while(true)
                    {
                        System.out.print("\tLower Limit: ");
                        Integer buffer = userIntegerInput();
                        if(buffer!=null)
                        {
                            if(buffer>=0)
                            {
                                lsalary = buffer;
                                break;
                            }
                            else
                            {
                                System.out.println("--- Invalid salary ---");
                            }

                        }
                    }
                    int rsalary;
                    while(true)
                    {
                        System.out.print("\tUpper Limit: ");
                        Integer buffer = userIntegerInput();
                        if(buffer!=null)
                        {
                            if(buffer>=0)
                            {
                                rsalary = buffer;
                                break;
                            }
                            else
                            {
                                System.out.println("--- Invalid salary ---");
                            }
                        }
                    }
                    data.searchBySalary(lsalary, rsalary);
                    break;
                case 5:
                    data.countryPlayerCount();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("--- Invalid Input (Valid Options: 1-6) ---");
                    break;
                
            }
        }
    }

    void searchClubsMenu()
    {
        while(state!=5)
        {
            System.out.println("Club Searching Options:");
            System.out.println("(1) Player(s) with maximum salary of a club");
            System.out.println("(2) Player(s) with maximum age of a club");
            System.out.println("(3) Player(s) with maximum height of a club");
            System.out.println("(4) Total yearly salary of a club");
            System.out.println("(5) Back to Main Menu");

            while(true)
            {
                System.out.print("\nCommand: ");
                Integer buffer = userIntegerInput();
                if(buffer!=null)
                {
                    state = buffer;
                    break;
                }
            }
            System.out.println();

            switch(state)
            {
                case 1:
                    System.out.print("Enter Club Name: ");
                    String clubName = sc.nextLine();
                    data.maxSalaryClub(clubName);
                    break;
                case 2:
                    System.out.print("Enter Club Name: ");
                    clubName = sc.nextLine();
                    data.maxAgeClub(clubName);
                    break;
                case 3:
                    System.out.print("Enter Club Name: ");
                    clubName = sc.nextLine();
                    data.maxHeightClub(clubName);
                    break;
                case 4:
                    System.out.print("Enter Club Name: ");
                    clubName = sc.nextLine();
                    data.totalYearlyClubSalary(clubName);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("--- Invalid Input (Valid Options 1-5)---");
                    break;
            }
        }
    }

    void addPlayerMenu()
    {
        System.out.println("\n---- Add Player Menu ----\n");
        System.out.print("Enter Name: ");
        String playerName = sc.nextLine();
        if(data.findPlayer(playerName)!=null)
        {
            System.out.println("\n--- Player with this name already exists ---\n");
            return;
        }
        System.out.print("Enter Country: ");
        String countryName = sc.nextLine();
        if(data.findCountry(countryName)==null)
        {
            country newCountry = new country(countryName);
            data.addCountry(newCountry);
        }
        int playerAge;
        while(true)
        {
            System.out.print("Enter Age: ");
            Integer buffer = userIntegerInput();
            if(buffer!=null)
            {
                if(buffer>0)
                {
                    playerAge = buffer;
                    break;
                }
                else
                {
                    System.out.println("---Invalid age ---");
                }
            }
        }
        
        Double playerHeight;
        while(true)
        {
            System.out.print("Enter Height: ");
            Double buffer = userDoubleInput();
            if(buffer!=null)
            {
                if(buffer>0)
                {
                    playerHeight = buffer;
                    break;
                }
                else
                {
                    System.out.println("--- Invalid Height ---");
                }
            }
        }
        
        System.out.print("Enter Club Name: ");
        String clubName = sc.nextLine();
        if(data.findClub(clubName)==null)
        {
            club newClub = new club(clubName);
            data.addClub(newClub);
        }
        String positionName;
        String[] positions = {"batsman", "bowler", "wicketkeeper", "allrounder"};
        while(true)
        {
            System.out.print("Enter Position (Batsman, Bowler, Wicketkeeper, Allrounder): ");
            positionName = sc.nextLine();
            boolean found = false;
            for(String temp: positions)
            {
                if(positionName.equalsIgnoreCase(temp))
                {
                    found = true;
                    break;
                }
            }
            if(found) break;
            System.out.println("--- Not a valid position ---");
        }
        int jerseyNumber;
        while(true)
        {
            System.out.print("Enter Jersey Number (-1 if N/A): ");
            Integer buffer = userIntegerInput();
            if(buffer!=null)
            {
                if(buffer>0 || buffer == -1)
                {
                    jerseyNumber = buffer;
                    break;
                }
                else
                {
                    System.out.println("--- Invalid Jersey Number ---");
                }
            }
        }
        int salary;
        while(true)
        {
            System.out.print("Enter Salary: ");
            Integer buffer = userIntegerInput();
            if(buffer!=null)
            {
                if(buffer>=0)
                {
                    salary = buffer;
                    break;
                }
                else
                {
                    System.out.println("Invalid salary");
                }
            }
        }
        player newPlayer = new player(playerName, countryName, playerAge, playerHeight, clubName, positionName, jerseyNumber, salary);
        data.addPlayer(newPlayer);
        data.findClub(clubName.toLowerCase()).addPlayer(newPlayer);
        data.findCountry(countryName.toLowerCase()).addPlayer(newPlayer);
        System.out.println("\n"+playerName+ " successfully added to database\n");
    }

    void input(){
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            while(true)
            {
                String line = br.readLine();
                if(line == null) break;
                String[] tokens = line.split(",", 8);
                
                int jerseyNumber;
                try
                {
                    jerseyNumber = Integer.parseInt(tokens[6]);
                }
                catch(NumberFormatException e)
                {
                    jerseyNumber = -1;
                }

                if(data.findPlayer(tokens[0].toLowerCase())==null)
                {
                    player newPlayer = new player(tokens[0], tokens[1], Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]), tokens[4], tokens[5], jerseyNumber, Integer.parseInt(tokens[7]));
                    data.addPlayer(newPlayer);

                    country countryObject = data.findCountry(tokens[1].toLowerCase());

                    if(countryObject==null)
                    {
                        countryObject = new country(tokens[1]);
                        countryObject.addPlayer(newPlayer);
                        data.addCountry(countryObject);
                    }
                    else
                    {
                        countryObject.addPlayer(newPlayer);
                    }

                    club clubObject = data.findClub(tokens[4].toLowerCase());

                    if(clubObject==null)
                    {
                        clubObject = new club(tokens[4]);
                        clubObject.addPlayer(newPlayer);
                        data.addClub(clubObject);
                    }
                    else
                    {
                        clubObject.addPlayer(newPlayer);
                    }
                
                }
                else
                {
                    System.out.println("--- Ignoring duplicate data for "+tokens[0]+" ---");
                }
            }
            br.close();
        }
        catch(IOException e)
        {  
            System.out.println("IOException at ");
            e.printStackTrace();
            return;
        }
        
    }
    
    Integer userIntegerInput()
    {
        try
        {
            return Integer.parseInt(sc.nextLine());
        }
        catch(NumberFormatException e)
        {
            System.out.println("--- Invalid Input (Not a valid Integer) ---");
            return null;
        }
    }

    Double userDoubleInput()
    {
        try
        {
            return Double.parseDouble(sc.nextLine());
        }
        catch(NumberFormatException e)
        {
            System.out.println("--- Invalid Input ---");
            return null;
        }
    }
    
}


