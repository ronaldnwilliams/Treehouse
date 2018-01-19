package main.java;

import main.java.com.rnw.Organizer;
import main.java.com.rnw.model.Player;
import main.java.com.rnw.model.Players;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    Organizer organizer = new Organizer(players);
    organizer.run();
  }
}
