
package org.doublequestionmark.gooseapp;

import java.util.Scanner;
import java.util.HashMap;

public class GooseApp {
    
  private Scanner input;
  
  private String movePattern;
  private String playerPattern;
  
  private boolean startGame;
  
  private HashMap<String, Integer> players = null;
  private PlayGoose game = null;
  
  public GooseApp() {
      
    input = new Scanner(System.in); //read from standard input
    
    movePattern = "move ";
    playerPattern = "add player ";
    
    players = new HashMap<String, Integer>(); //each element of the map -> (playerName, currentScore)
    
    startGame = false;
    
    System.out.println("Hello, have fun with Goose game!!");
  
  }
  
  public void play() {
      
    String command;
    String output;
    
    while (input.hasNextLine()) {
        
      command = input.nextLine().trim();
      
      if (command.equalsIgnoreCase("exit")) {
          
        System.out.println("bye");
        
        break;
      
      }
      
      else if (command.equalsIgnoreCase("start")) {
          
        if (startGame) {
            
          System.out.println("the game has already started");
        
        }
        
        else {
            
          if (players.size() > 1) {
              
            game = new PlayGoose(players);
            
            startGame = true;
            
            System.out.println("the game starts now!");
          
          }
          
          else {
              
            System.out.println("the game needs at least 2 players");
          
          }
        
        }
      
      }
      
      else if ((command.startsWith(movePattern)) && (command.compareTo(movePattern) > 0)) {
          
        if (startGame) {
            
          output = game.parseMove(command);
          
          System.out.println(output);
          
          if (output.contains("Wins")) {
              
            break;
          
          }
        
        }
        
        else {
            
          System.out.println("the game has not started yet");
        
        }
      
      }
      
      else if ((command.startsWith(playerPattern)) && (command.compareTo(playerPattern) > 0)) {
          
        if (startGame) {
            
          System.out.println("the game has already started, registrations closed");
        
        }
        
        else {
            
          String playerName = command.replace(playerPattern,"").trim();
          
          if (!(players.containsKey(playerName))) {
              
            players.put(playerName, 0);
            
            System.out.println("players: " + players.keySet());
          
          }
          
          else {
              
            System.out.println(playerName + ": already existing player");
          
          }
        
        }
      
      }
      
      else {
          
        System.out.println("incomplete or wrong command, please try again");
      
      }
    
    }
    
    input.close();
  
  }
  
  public static void main(String[] args) {
      
    new GooseApp().play();
  
  }

}