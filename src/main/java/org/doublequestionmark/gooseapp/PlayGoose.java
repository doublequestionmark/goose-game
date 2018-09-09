
package org.doublequestionmark.gooseapp;

import java.util.Set;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PlayGoose {
    
  private HashMap<String, Integer> players;
  
  private String shortRegex;
  private String longRegex;
  
  private Pattern shortPattern;
  private Pattern longPattern;
  
  private Matcher shortMatcher;
  private Matcher longMatcher;
  
  private Random randomRoll;
  
  private int minValueRoll;
  private int maxValueRoll;
  
  private int winningScore;
  
  private ArrayList<Integer> theBridge;
  private ArrayList<Integer> theGoose;
  
  public PlayGoose(HashMap<String, Integer> players) {
      
    this.players = players;
    
    shortRegex = "(move) (\\S+)$";
    longRegex = "(move) (\\S+) ([1-6]), ([1-6])$";
    
    shortPattern = Pattern.compile(shortRegex);
    longPattern = Pattern.compile(longRegex);
    
    randomRoll = new Random();
    
    minValueRoll = 1;
    maxValueRoll = 6;
    
    winningScore = 63;
    
    theBridge = new ArrayList<Integer>(Arrays.asList(new Integer[]{6}));
    theGoose = new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 14, 18, 23, 27}));
  
  }
  
  public String parseMove(String command) {
      
    String player;
    String sentence;
    
    int firstRoll;
    int secondRoll;
    
    shortMatcher = shortPattern.matcher(command);
    longMatcher = longPattern.matcher(command);
    
    if (shortMatcher.find()) { //"move Bob" found
        
      player = shortMatcher.group(2);
      
      if (this.players.containsKey(player)) {
          
        //automatic dice roll
        firstRoll = randomRoll.nextInt((maxValueRoll - minValueRoll) + 1) + minValueRoll;
        secondRoll = randomRoll.nextInt((maxValueRoll - minValueRoll) + 1) + minValueRoll;
        
        sentence = getNextScore(player, firstRoll, secondRoll);
      
      }
      
      else {
          
        sentence = "player not present";
      
      }
    
    }
    
    else if (longMatcher.find()) { //"move Bob x, y" found
        
      player = longMatcher.group(2);
      
      if (this.players.containsKey(player)) {
          
        firstRoll = Integer.parseInt(longMatcher.group(3));
        secondRoll = Integer.parseInt(longMatcher.group(4));
        
        sentence = getNextScore(player, firstRoll, secondRoll);
      
      }
      
      else {
          
        sentence = "player not present";
      
      }
    
    }
    
    else {
        
      sentence = "incomplete or wrong command, please try again";
    
    }
    
    return sentence;
  
  }
  
  private String getNextScore(String player, int firstRoll, int secondRoll) {
      
    String sentence;
    String currentPosition;
    
    int currentScore = this.players.get(player);
    
    if (currentScore != 0) {
        
      currentPosition = Integer.toString(currentScore);
    
    }
    
    else {
        
      currentPosition = "Start";
    
    }
    
    int nextScore = currentScore + firstRoll + secondRoll;
    
    if (theBridge.contains(nextScore)) {
        
      nextScore = nextScore + 6;
      
      sentence = player + " rolls " + firstRoll + ", " + secondRoll + ". " + player + " moves from " + currentPosition + " to The Bridge. " + player + " jumps to 12";
    
    }
    
    else if (theGoose.contains(nextScore)) {
        
      sentence = player + " rolls " + firstRoll + ", " + secondRoll + ". " + player + " moves from " + currentPosition + " to " + nextScore;
      
      do {
          
        nextScore = nextScore + firstRoll + secondRoll;
        
        sentence = sentence + ", The Goose. " + player + " moves again and goes to " + nextScore;
      
      } while (theGoose.contains(nextScore));
    
    }
    
    else if (nextScore > winningScore) {
        
      nextScore = winningScore - (nextScore - winningScore);
      
      sentence = player + " rolls " + firstRoll + ", " + secondRoll + ". " + player + " moves from " + currentPosition + " to " + winningScore + ". " + player + " bounces! " + player + " returns to " + nextScore;
    
    }
    
    else if (nextScore == winningScore) {
        
      sentence = player + " rolls " + firstRoll + ", " + secondRoll + ". " + player + " moves from " + currentPosition + " to " + nextScore + ". " + player + " Wins!!";
    
    }
    
    else {
        
      sentence = player + " rolls " + firstRoll + ", " + secondRoll + ". " + player + " moves from " + currentPosition + " to " + nextScore;
    
    }
    
    this.players.put(player, nextScore); //score update

    //prank
    
    Set<Map.Entry<String, Integer>> set = this.players.entrySet();
    
    for (Map.Entry<String, Integer> entry : set) {
        
      if ((entry.getValue() == nextScore) && (!(entry.getKey().equals(player)))) {
          
        this.players.put(entry.getKey(), currentScore);
        
        sentence = sentence + ". On " + nextScore + " there is " + entry.getKey() + ", who returns to " + currentScore;
      
      }
    
    }
    
    return sentence;
  
  }

}