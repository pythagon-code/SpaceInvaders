/**
   @author Abhay Pokhriyal
*/

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import acm.graphics.GOval;
import acm.graphics.GPolygon;
import acm.graphics.GLabel;

import java.awt.Color;

/**
   Represents a spacecraft that the player can control and shoots lasers.
*/
public class PlayerSpacecraft extends Spacecraft implements KeyListener {
   private static final int INITIAL_X = 400, INITIAL_Y = 525;
   private static final int MAX_HEALTH = 100;
   private static final double PLAYER_SPEED = 8.5;
   private static final int INITIAL_HEALTH_X = 50, INITIAL_HEALTH_Y = 50;
   
   private static final int R = 45, G = 164, B = 185;
   private static final Color blue = new Color(R, G, B);
   private static final int SCALE = 15;
   
   private double velocity;
   private GLabel healthDisplay;
   
   /**
      Construct and render a PlayerSpacecraft and display instructions for player.
      @param game SpaceInvaders program game.
   */
   public PlayerSpacecraft(SpaceInvaders game) {
      super(game, INITIAL_X, INITIAL_Y, MAX_HEALTH);
      velocity = 0;
      healthDisplay = new GLabel(
         "Use LEFT and RIGHT keys to navigate.",
         INITIAL_HEALTH_X, INITIAL_HEALTH_Y);
      healthDisplay.setColor(Color.WHITE);
      healthDisplay.setFont("Unispace-32");
       getGame().add(healthDisplay);
      
      final double BOTTOM_LEFT_X = 0, BOTTOM_RIGHT_X = 4*SCALE;
      final double MIDDLE_LEFT_X = 0, MIDDLE_RIGHT_X = 4*SCALE;
      final double TOP_X = 2*SCALE, TOP_Y = 0;
      final double BOTTOM_Y = 4*SCALE;
      final double MIDDLE_Y = 2.8*SCALE;
      
      GPolygon base = new GPolygon();
      base.addVertex(BOTTOM_LEFT_X, BOTTOM_Y);
      base.addVertex(MIDDLE_LEFT_X, MIDDLE_Y);
      base.addVertex(TOP_X, TOP_Y);
      base.addVertex(MIDDLE_RIGHT_X, MIDDLE_Y);
      base.addVertex(BOTTOM_RIGHT_X, BOTTOM_Y);
      base.setFilled(true);
      add(base);
      
      final double POD_WIDTH = 0.4*SCALE;
      final double POD_HEIGHT = 2.5*SCALE;
      final double LEFT_POD_X = -0.3*SCALE, RIGHT_POD_X = 4.3*SCALE-POD_WIDTH;
      final double POD_Y = 2.2*SCALE;
      
      GOval leftPod = new GOval(LEFT_POD_X, POD_Y, POD_WIDTH, POD_HEIGHT);
      leftPod.setFilled(true);
      add(leftPod);
      GOval rightPod = new GOval(RIGHT_POD_X, POD_Y, POD_WIDTH, POD_HEIGHT);
      rightPod.setFilled(true);
      add(rightPod);
      
      setColor(blue);
      markAsComplete();    
   }
   
   /**
      Update the health display, and move the player in the direction of input.
      Shoot a middle laser and side lasers every select frames.
      @param frame number of passed frames.
   */
   @Override
   public void tick(int frame) {
      super.tick(frame);
      final int X_BOUND = 50;
      if (
         getX() + velocity > X_BOUND
         && getX() + getWidth() + velocity < getGame().getWidth() - X_BOUND
      )
         move(velocity, 0);
      
      if (isAlive())
         healthDisplay.setLabel("Health: " + getHealth());
      else
         healthDisplay.setLabel("Game Over! You may close this window.");
      healthDisplay.sendToFront();
                  
      final int ATTACK_FRAME_DELAY = 9;
      final int SIDE_ATTACK_DELAY_IN_ATTACKS = 4;
      final int REGEN_DELAY_IN_ATTACKS = 2;
      if (frame % ATTACK_FRAME_DELAY == 0) {
         final double Y_ADJUSTMENT = -2.8*SCALE;
         final int x = (int)(getBounds().getX() + getWidth()/2);
         final int y = (int)(getY() + getHeight()/2 + Y_ADJUSTMENT);
         
         if (frame % (SIDE_ATTACK_DELAY_IN_ATTACKS * ATTACK_FRAME_DELAY) == 0) {
            final int X_OFFSET = 1*SCALE;
            final double SPREAD = 4;
            final int Y_OFFSET = (int)(0.5*SCALE);
            
            final int leftX = x - X_OFFSET;
            final int rightX = x + X_OFFSET;
            final int newY = y + Y_OFFSET;

            new PlayerProjectile(
               getGame(), leftX, newY, -SPREAD, blue.darker());
            new PlayerProjectile(
               getGame(), rightX, newY, SPREAD, blue.darker());
         }
         new PlayerProjectile(
            getGame(), x, y, 0, blue.darker());
         
         if (
            frame % (REGEN_DELAY_IN_ATTACKS * ATTACK_FRAME_DELAY) == 0
            && getHealth() < MAX_HEALTH
         )
            regenerateHealth();
      }
   }
   
   /**
      Set the spaceship to move left or right.
      @param keyEvent input event.
   */
   @Override
   public void keyPressed(KeyEvent keyEvent) {
      switch (keyEvent.getKeyCode()) {
      case KeyEvent.VK_LEFT:
         velocity = -PLAYER_SPEED;
         break;
      case KeyEvent.VK_RIGHT:
         velocity = PLAYER_SPEED;
      }
   }
   
   /**
      Set the spaceship to stop moving if it is moving in release key direction.
      @param keyEvent input event.
   */
   @Override
   public void keyReleased(KeyEvent keyEvent) {
      switch (keyEvent.getKeyCode()) {
      case KeyEvent.VK_LEFT:
         if (velocity == -PLAYER_SPEED)
            velocity = 0;
         break;
      case KeyEvent.VK_RIGHT:
         velocity = PLAYER_SPEED;
         if (velocity == PLAYER_SPEED)
            velocity = 0;
      }
   }
   
   /**
      Does nothing.
      @param keyEvent input event.
   */
   @Override
   public void keyTyped(KeyEvent keyEvent) {  }
}