/**
   @author Abhay Pokhriyal
*/

package edu.illinois.abhayp4;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import acm.graphics.GOval;
import acm.graphics.GLabel;

import java.awt.Color;

import acm.program.GraphicsProgram;

/**
   Represents SpaceInvaders game window.
*/
public class SpaceInvaders extends GraphicsProgram {
   public enum Difficulty {EASY, MEDIUM, HARD, BRUTAL, ETHEREAL};
   
   private PlayerSpacecraft player;
   private Difficulty difficulty;
   private Set<RenderedElement> elements;
   private List<RenderedElement> displayList, destroyList;
   private GLabel scoreCounter;
   private int score;
   
   private static int highestScore = 0;
   
   /**
      @return highest score out of the games.
   */
   public static int getHighestScore() {
      return highestScore;
   }
   
   /**
      Constructs the SpaceInvaders game with a difficulty.
      @param difficulty determines the harshness of the game.
   */
   public SpaceInvaders(Difficulty difficulty) {
      this.difficulty = difficulty;
      elements = new HashSet<>();
      displayList = new ArrayList<>();
      destroyList = new ArrayList<>();
      
      final int SCORE_COUNTER_X = 350, SCORE_COUNTER_Y = 640;
      scoreCounter = new GLabel(
         "Click to Start", SCORE_COUNTER_X, SCORE_COUNTER_Y);
      scoreCounter.setColor(Color.WHITE);
      scoreCounter.setFont("Inconsolata-32");
      add(scoreCounter);
      score = 0;
   }
   
   /**
      Add space theme and score counter and player.
      Run the game clock and add aliens and tick each RenderedElement.
   */
   @Override
   public void run() {
      addSpaceBackground();
      add(scoreCounter);
      player = new PlayerSpacecraft(this);
      new ScatterBeamAlien(this, 100);
      addKeyListeners(player);
      waitForClick();
            
      int frame = 0;
      int finishFrame = Integer.MAX_VALUE;
      while (frame <= finishFrame) {
         final int FRAME_DELAY = 10;
         try {
            Thread.sleep(FRAME_DELAY);
         } catch (InterruptedException ex) { return; }
         
         for (RenderedElement element : elements) {
            element.tick(frame);
         }
         
         for (RenderedElement element : displayList) {
            element.sendBackward();
            element.sendBackward();
            elements.add(element);
         }
         displayList.clear();
         
         for (RenderedElement element : destroyList) {
            remove(element);
            elements.remove(element);
         }
         destroyList.clear();
         
         final int MAX_ZEROS = 5;
         int leadingZeros = Math.max(MAX_ZEROS - ("" + score).length(), 0);
         scoreCounter.setLabel("0".repeat(leadingZeros) + score);
         scoreCounter.sendToFront();
         
         spawnAlienRandomly();
         frame++;
         
         int GAME_OVER_FRAMES = 100;
         if (!player.isAlive() && finishFrame == Integer.MAX_VALUE)
            finishFrame = frame + GAME_OVER_FRAMES;
      }
      
      highestScore = Math.max(score, highestScore);
   }
   
   /**
      Make the background black and randomly add stars.
   */
   private void addSpaceBackground() {
      final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 720;
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      setBackground(Color.BLACK);
      
      final int STAR_COUNT = 400;
      final int STAR_DIAMETER = 2;
      for (int i = 0; i < STAR_COUNT; i++) {
         double x = Math.random() * WINDOW_WIDTH;
         double y = Math.random() * WINDOW_HEIGHT;
         GOval star = new GOval(x, y, STAR_DIAMETER, STAR_DIAMETER);
         star.setColor(Color.WHITE);
         star.setFillColor(Color.WHITE);
         star.setFilled(true);
         add(star);
         star.sendBackward();
         star.sendBackward();
      }
   }
   
   /**
      Maybe add aliens in random x-coordinate, either TroopAlien or ScatterBeamAlien.
   */
   private void spawnAlienRandomly() {
      double alienSpawnRarity = 0.02;
      double scatterBeamAlienOdds = 0.125;
      
      switch (difficulty) {
      case Difficulty.MEDIUM:
         alienSpawnRarity = 0.025;
         scatterBeamAlienOdds = 0.15;
         break;
      case Difficulty.HARD:
         alienSpawnRarity = 0.03;
         scatterBeamAlienOdds = 0.2;
         break;
      case Difficulty.BRUTAL:
         alienSpawnRarity = 0.035;
         scatterBeamAlienOdds = 0.3;
         break;
      case Difficulty.ETHEREAL:
         alienSpawnRarity = 0.04;
         scatterBeamAlienOdds = 0.4;
      }
            
      if (Math.random() <= alienSpawnRarity) {
         final int X_BOUND = 50;
         final double RIGHT_BOUND = getWidth() - X_BOUND - player.getWidth();
         final int x = (int)(Math.random()*(RIGHT_BOUND-X_BOUND) + X_BOUND);
         
         AlienSpacecraft alien;
         if (Math.random() <= scatterBeamAlienOdds)
            alien = new ScatterBeamAlien(this, x);
         else 
            alien = new TroopAlien(this, x);
            
         for (RenderedElement other : elements) {
            if (other instanceof Spacecraft && alien.didHit(other)) {
               alien.destroy();
               return;
            }
         }
      }
   }
   
   /**
      @return player spacecraft.
   */
   public PlayerSpacecraft getPlayer() {
      return player;
   }
   
   /**
      @return iterator to iterate through game elements.
   */
   public Iterator<RenderedElement> getRenderedElements() {
      return elements.iterator();
   }
   
   /**
      Add element to display and allow the object to be ticked.
      @param element element to be displayed.
   */
   public void displayElement(RenderedElement element) {
      add(element);
      displayList.add(element);
   }
   
   /**
      Add element to destroy list to be destroyed in the future within the game cycle.
      @param element element to be removed.
   */
   public void destroyElement(RenderedElement element) {
      destroyList.add(element);
   }
   
   /**
      Add points to the score.
      @param points to add.
   */
   public void addPoints(int points) {
      score += points;
   }
}