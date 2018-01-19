package main.java;


import main.java.com.teamtreehouse.Organizer;
import main.java.com.teamtreehouse.model.Player;
import main.java.com.teamtreehouse.model.Players;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    Organizer organizer = new Organizer(players);
    organizer.run();
  }
}
