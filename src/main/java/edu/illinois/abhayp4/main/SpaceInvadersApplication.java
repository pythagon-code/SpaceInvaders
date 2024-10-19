/**
   @author Abhay Pokhriyal
   
   Resources:
   -@link https://cs.stanford.edu/people/eroberts/jtf/javadoc/student/acm/graphics/package-summary.html
*/

package edu.illinois.abhayp4.main;

import edu.illinois.abhayp4.SpaceInvaders;

import javax.swing.JOptionPane;

/**
   Represent the program that runs the SpaceInvaders game.
*/
public class SpaceInvadersApplication {
   /**
      Start the SpaceInvaders game and ask to play again.
      @param args
   */
   public static void main(String[] args) {      
      String output = "Goal: Annihilate as many aliens while staying alive.\n\n";
      output += "The Green Aliens are worth 10 points.\n";
      output += "The Pink Aliens are worth 20 points and are more dangerous.\n\n";
      output += "You may destroy aliens by either shooting or ramming them.\n";
      JOptionPane.showMessageDialog(
         null, output, "How to Play", JOptionPane.PLAIN_MESSAGE);
      
      boolean again = false;
      do {
         SpaceInvaders.Difficulty difficulty = getChosenDifficulty();
         SpaceInvaders game = new SpaceInvaders(difficulty);
         game.start();
         
         output = "Your highest score: " + SpaceInvaders.getHighestScore();
         output += "\n\nDo you want to play gain? (y/n)";
         
         String input = JOptionPane.showInputDialog(
            null, output, "Play Again?", JOptionPane.PLAIN_MESSAGE);
            
         again = input != null && input.equalsIgnoreCase("y");
      } while (again);
   }
   
   /**
      Prompt difficulty to start the game with.
      @return the difficulty inputted.
   */
   private static SpaceInvaders.Difficulty getChosenDifficulty() {
      String output = "Select the difficulty: (1 to 5)\n\n";
      output += "[Options]\n";
      output += "<1> Easy\n";
      output += "<2> Medium\n";
      output += "<3> Hard\n";
      output += "<4> Brutal\n";
      output += "<5> Ethereal\n\n";
      
      int choice = 0;
      while (choice < 1 || choice > 5) {
         String input = JOptionPane.showInputDialog(
            null, output, "Difficulty", JOptionPane.PLAIN_MESSAGE);
         try {
            choice = Integer.parseInt(input);
         } catch (NumberFormatException ex) {
            if (input == null)
               System.exit(0);
         }
      }
      return SpaceInvaders.Difficulty.values()[choice-1];
   }
}