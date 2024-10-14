/**
   @author Abhay Pokhriyal
*/

import acm.graphics.GOval;

import java.awt.Color;

/**
   Represents an alien that moves and attacks.
*/
public abstract class AlienSpacecraft extends Spacecraft {
   private static final int INITIAL_Y = -50, ALIEN_SPEED = 2;
   
   private static final int SCALE = 15;
   
   private int points;
   
   /**
      Construct and render an AlienSpacecraft.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
      @param maximum health of alien.
      @param color color of the alien.
      @param points number of points received from destroying this alien.
   */
   protected AlienSpacecraft(
      SpaceInvaders game, int x, int health, Color color, int points
   ) {
      super(game, x, INITIAL_Y, health);
      this.points = points;
      
      final int WIDTH = 5*SCALE, HEIGHT = 3*SCALE;
      GOval oval = new GOval(WIDTH, HEIGHT);
      oval.setFilled(true);
      add(oval);
      setColor(color);
      markAsComplete();
   }
   
   /**
      Explode the alien and give points to the player. 
   */
   @Override
   public void destroy() {
      super.destroy();
      getGame().addPoints(points);
   }
   
   /**
      Move the alien forward, and do damage to player and self if colliding.
      @param frame number of passed frames.
   */
   @Override
   public void tick(int frame) {
      super.tick(frame);
      if (isAlive())
         moveBounded(0, ALIEN_SPEED);
      if (didHit(getGame().getPlayer())) {
         final int HIT_DAMAGE = 2;
         takeDamage(HIT_DAMAGE);
         getGame().getPlayer().takeDamage(HIT_DAMAGE);
      }
   }
}