package main.java.com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Roster;
import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Organizer {
  private ArrayList<Player> mOriginalPlayers;
  private BufferedReader mReader;
  private TreeMap<Team, Roster> mTeams;
  private LinkedHashMap<String, String> mMenu;
 
  public Organizer(Player[] players) {
    mOriginalPlayers = new ArrayList<Player>(Arrays.asList(players));
    Collections.sort(mOriginalPlayers);
    mReader = new BufferedReader(new InputStreamReader(System.in));
    mTeams = new TreeMap<Team, Roster>();
    mMenu = new LinkedHashMap<String, String>();
    
    //Create menu options
    mMenu.put("create", "Create a new team of players.");
    mMenu.put("add", "Add a player to a team.");
    mMenu.put("remove", "Remove a player from a team.");
    mMenu.put("teams", "List all teams.");
    mMenu.put("list", "List all free agent players.");
    mMenu.put("print", "Print your team roster.");
    mMenu.put("report", "View team height report.");
    mMenu.put("league", "View League Balance Report.");
    mMenu.put("quit", "Exit the program.");
  }
  
  private String promptAction() throws IOException {
    for (Map.Entry<String, String> option : mMenu.entrySet()) {
      System.out.printf("%s - %s %n",
                        option.getKey(),
                        option.getValue());
    }
    System.out.print("Welcome. What do you want to do: ");
    String choice = mReader.readLine();
    return choice.trim().toLowerCase();
  }
  
  public void run() {
    String choice = "";
    do {
      try {
        choice = promptAction();
        switch(choice) {
          case "create":
            String teamName = promptForTeam();
            boolean teamCheck = teamCheck(teamName);
            if ( teamCheck == false) {
              break;
            }
            String coach = promptForCoach();
            boolean coachFree = coachCheck(coach);
            if (coachFree == false) {  
              break;
            }
            Team team = new Team(teamName, coach);
            System.out.printf("Adding team %s with coach %s... %n%n", 
                              team.getTeamName(),
                              team.getCoach());
            Roster teamPlayers = new Roster();
            mTeams.put(team, teamPlayers);
            break;
          case "add":
            if (mTeams.isEmpty()) {
              System.out.printf("There are currently no teams created.%n%n");
              break;
            } else {
              System.out.println("Here is a list of current teams: ");
              listTeams();
              System.out.println("Here is a list of players: ");
              listPlayers();
              String teamAddedTo = promptForTeam();
              Team teamToAddTo = selectTeam(teamAddedTo);
              if (teamToAddTo == null) {
                System.out.printf("Sorry %s is not a team.%n", teamAddedTo);
                break;
              } else {
              System.out.printf("Which player do you want to add to %s?", teamAddedTo);
              String playerFirstName = promptForFirstName();
              String playerLastName = promptForLastName();
              int playerToAdd = getPlayerIndex(playerFirstName, playerLastName);
              if (playerToAdd == -1) {
                System.out.printf("Sorry could not find %s, %s. %n",
                                  playerLastName, 
                                  playerFirstName);
                break;
              } else {
                Player teamPlayer = mOriginalPlayers.get(playerToAdd);
                boolean teamSize = mTeams.get(teamToAddTo).teamSizeCheck();
                if (teamSize == false) {
                  System.out.println("Sorry the max amount of players is already reached: 11");
                } else {
                  mTeams.get(teamToAddTo).addPlayer(teamPlayer);
                  mOriginalPlayers.remove(playerToAdd);
                }
              }

              }
            }
            break;
          case "remove":
            int noPlayers = 0;
            for (Map.Entry<Team, Roster> teams : mTeams.entrySet()) {
             if (teams.getValue().getPlayerCount() > 0)
               noPlayers++;
            }
            if (mTeams.isEmpty() || noPlayers == 0) {
              System.out.printf("There are currently no teams or none of the teams have players yet.%n%n");
              break;        
            } else {
              System.out.println("Here is a list of current teams: ");
              listTeams();
              String teamRemoveFrom = promptForTeam();
              Team teamToRemoveFrom = selectTeam(teamRemoveFrom);
              if (teamToRemoveFrom == null) {
                System.out.printf("Sorry %s is not a team.%n", teamRemoveFrom);
                break;
              } else {
                if (mTeams.get(teamToRemoveFrom).mPlayers.isEmpty()) {
                  System.out.printf("There are no players on team %s", teamRemoveFrom);
                  break;
                } else {
                  System.out.printf("Here are the players on %s: %n", teamRemoveFrom);
                  for (Player player : mTeams.get(teamToRemoveFrom).mPlayers) {
                    System.out.println(player); 
                  } 
                  System.out.printf("Which player do you want to remove from %s?%n", teamRemoveFrom);
                  String playerFirstName = promptForFirstName();
                  String playerLastName = promptForLastName();
                  int playerToRemove = getRosterPlayerIndex(teamToRemoveFrom, playerFirstName, playerLastName);
                  if (playerToRemove == -1) {
                    System.out.printf("Sorry could not find %s, %s. %n",
                                      playerLastName, 
                                      playerFirstName);
                  break;
                  } else {
                  Player playerRemoved = mTeams.get(teamToRemoveFrom).mPlayers.get(playerToRemove);
                  mTeams.get(teamToRemoveFrom).removePlayer(playerRemoved);
                  mOriginalPlayers.add(playerRemoved); 
                  }
                  break;
                }
              }
            }
          case "teams":
            listTeams();
            break;
          case "list":
            listPlayers();
            break;
          case "print":
            listTeams();
            String teamPrint = promptForTeam();
            Team teamToPrint = selectTeam(teamPrint);
            for (Player players : mTeams.get(teamToPrint).getPlayers())
            System.out.println(players);
            break;
          case "report":
            if (mTeams.isEmpty()) {
              System.out.printf("There are currently no teams created.%n%n");
              break;
            } else {
            listTeams();
            String teamForReport = promptForTeam();
            Team teamReport = selectTeam(teamForReport);
            mTeams.get(teamReport).getPlayersByHeight();
            }
            break;
          case "league":
            Set<Team> teamLeague = new TreeSet<Team>(mTeams.keySet());
            for (Team teamLeagues : teamLeague) {
              mTeams.get(teamLeagues).getHeightCount();
              System.out.printf("%s has %d experienced players and %d inexperienced players.%n", 
                                teamLeagues.getTeamName(), 
                                mTeams.get(teamLeagues).getNumberExperienced(),                                mTeams.get(teamLeagues).getNumberInexperienced());
            }
            break;
          case "quit":
            System.out.println("Now quiting program.");
            break;
          default:
            System.out.printf("Unkown choice:  '%s'. Try again.  %n%n%n",
                              choice);
        }
      } catch(IOException ioe) {
        System.out.println("Problem with input");
        ioe.printStackTrace();
      }
    } while (!choice.equals("quit"));
  }
  
  public void removePlayer(Player playerName) {
    mOriginalPlayers.remove(playerName);
  }
  
  public void listPlayers() {
    Collections.sort(mOriginalPlayers);
    System.out.println("Here is a list of all players: ");
    for (Player player : mOriginalPlayers)
      System.out.println(player); 
      System.out.println();
  }
  
  public void listTeams() {
    System.out.println("Loading the list of teams...");
    if (mTeams.isEmpty()) {
      System.out.printf("There are currently no teams created.%n%n");
    } else {
      Set<Team> keys = new TreeSet<Team>(mTeams.keySet());
      Collections.sort(keys);
      for (Team key : keys) {
      System.out.printf("Team name: %s, Coach: %s %n", 
                        key.getTeamName(), 
                        key.getCoach());
      }
      System.out.println();
    }
  }
  
  public boolean coachCheck(String coachName) {
    boolean coachCheck = true;
    Set<Team> keys = new TreeSet<Team>(mTeams.keySet());
    for (Team key : keys) {
      if (key.getCoach().equals(coachName)) {
        coachCheck = false;
        System.out.printf("Sorry, %s is already coaching a team.%n%n",
                          coachName);
      }   
    }
    return coachCheck;
  }
  
  public boolean teamCheck(String teamName) {
    boolean teamCheck = true;
    Set<Team> keys = new TreeSet<Team>(mTeams.keySet());
    int numTeams = keys.size();
    if (numTeams >= 33) {
      System.out.println("Sorry, can not add anymore teams. Max number reached.");
      teamCheck = false;
    } else {
      for (Team key : keys) {
        if (key.getTeamName().equals(teamName)) {
          teamCheck = false;
          System.out.printf("Sorry, %s already exists.%n%n", 
                            teamName);
        }
      }
    }
    return teamCheck;
  }
  
  public Team selectTeam(String teamName) {
   Team teamToAddTo = null;
    Set<Team> keys = new TreeSet<Team>(mTeams.keySet());
    for (Team key : keys) {
      if (key.getTeamName().equals(teamName)) {
        teamToAddTo = key;
      }   
    }
    return teamToAddTo;
  }
  
  public int getRosterPlayerIndex(Team teamName, String firstName, String lastName) {
    for (int i = 0; i < mTeams.get(teamName).mPlayers.size(); i++) {
      Player player = mTeams.get(teamName).mPlayers.get(i);
      if (firstName.equalsIgnoreCase(player.getFirstName()) && lastName.equalsIgnoreCase(player.getLastName())) {
        return i;
      }
    }
    return -1; 
  }
  
  public int getPlayerIndex(String firstName, String lastName) {
    for (Player player : mOriginalPlayers) {
      if (player.getFirstName().equalsIgnoreCase(firstName) && player.getLastName().equalsIgnoreCase(lastName))
        return mOriginalPlayers.indexOf(player);
    }
    return -1;   
  }
  
  public String promptForTeam() throws IOException {
    System.out.print("Enter team name: ");
    String teamName = mReader.readLine().trim().toLowerCase();
    return teamName;
  }
  
  public String promptForCoach() throws IOException {
    System.out.print("Enter the coach: ");
    String coach = mReader.readLine().trim().toLowerCase();
    return coach;
  }
  
  public String promptForFirstName() throws IOException {
    System.out.println("Enter player first name: ");
    String playerFirstName = mReader.readLine().trim();
    return playerFirstName;
  }
  
  public String promptForLastName() throws IOException {
    System.out.println("Enter player last name: ");
    String playerLastName = mReader.readLine().trim();
    return playerLastName;
  }
  
}
  