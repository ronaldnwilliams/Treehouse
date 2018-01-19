package main.java.com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Roster {
 public List<Player> mPlayers;
 final int MAX_PLAYERS = 11;
 public int mHeightOne = 0;
 public int mHeightTwo = 0;
 public int mHeightThree = 0;
 
  
 public Roster() {
   mPlayers = new ArrayList<Player>();
 }
  
  public List<Player> getPlayers() {
    Collections.sort(mPlayers);
    return mPlayers;
  }
  
 public void addPlayer(Player player) {
    if (player.getHeightInInches() >= 35 && player.getHeightInInches() <= 40) {
      mHeightOne++;
    }
    if (player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46) {
      mHeightTwo++;
    }
    if (player.getHeightInInches() >= 47 && player.getHeightInInches() <= 50) {
      mHeightThree++;
    }  
   mPlayers.add(player);
 }
  
 public boolean teamSizeCheck() {
   boolean teamCheck = true;
   if (mPlayers.size() >= 11) {
     teamCheck = false;
     return teamCheck;
   } else {
     return teamCheck;
   }
 }
  
 public double getAverageExperience() {
   double sum = getNumberExperienced() + getNumberInexperienced();
   double averageExp = getNumberExperienced()/sum;
   return averageExp;
 }
  
 public void removePlayer(Player player) {
   if (player.getHeightInInches() >= 35 && player.getHeightInInches() <= 40) {
      mHeightOne--;
    }
    if (player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46) {
      mHeightTwo--;
    }
    if (player.getHeightInInches() >= 47 && player.getHeightInInches() <= 50) {
      mHeightThree--;
    }
   mPlayers.remove(player);
 }
  
 public int getPlayerCount() {
   return mPlayers.size();
 }
  
 public List<Player> getPlayersByHeight() {
   if (mPlayers.isEmpty()) {
     System.out.println("There are no players on this team.");
      return mPlayers;
   }
   System.out.printf("Average player experience = %.2f%n",
                     getAverageExperience()); 
   List<Player> aPlayers = new ArrayList<Player>();
   List<Player> bPlayers = new ArrayList<Player>();
   List<Player> cPlayers = new ArrayList<Player>();
   for (Player player : mPlayers) {
    if (player.getHeightInInches() >= 35 && player.getHeightInInches() <= 40) {
      aPlayers.add(player);
    }
    if (player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46) {
      bPlayers.add(player);
    }
    if (player.getHeightInInches() >= 47 && player.getHeightInInches() <= 50) {
      cPlayers.add(player);
    }
   }   
   System.out.println("35\"-40\": ");
   for (Player aPlayer : aPlayers)
     System.out.println(aPlayer);
   System.out.println("41\"-46\": ");
   for (Player bPlayer : bPlayers)
     System.out.println(bPlayer);    
   System.out.println("47\"-50\": ");
   for (Player cPlayer : cPlayers)
     System.out.println(cPlayer);
   return mPlayers;
 }
  
 public int getNumberExperienced() {
  int count = 0;
   for (Player expPlayer : mPlayers) {
    if (expPlayer.isPreviousExperience() == true) {
      count++;
    }
   }
   return count;
 }
 
 public int getNumberInexperienced() {
  int count = 0;
   for (Player expPlayer : mPlayers) {
    if (expPlayer.isPreviousExperience() == false) {
      count++;
    }
   }
   return count;
 }
  
 public void getHeightCount() {  
  System.out.printf("Player Heights: 35\"-40\": %d 41\"-46\": %d 47\"-50\": %d%n", mHeightOne, mHeightTwo, mHeightThree);
 }
}