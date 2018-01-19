package main.java.com.rnw.model;

import java.io.Serializable;
  
public class Team implements Comparable<Team>, Serializable {
 private static final long serialVersionUID = 1L;
 
 private String mTeamName;
 private String mCoach;
  
 public Team(String teamName, String coach) {
  mTeamName = teamName;
  mCoach = coach;
 }
  
  @Override
  public int compareTo(Team other) {
    int teamName = this.mTeamName.compareTo(other.mTeamName);
    return teamName;
  }
  
  public String getTeamName() {
    return mTeamName;
  }
  
  public String getCoach() {
    return mCoach;
  }
}