/**
   @author Abhay Pokhriyal
*/

import acm.graphics.GOval;

import java.awt.Color;

/**
   Represents an explosion effect that occurs after spacecrafts are destroyed.
*/
public class Explosion extends RenderedElement {
   private static double MAX_GROWTH_FACTOR = 3, EXPLOSION_FRAMES = 15;
   
   private double initialDiameter, finalDiameter, growthFactor;
   
   /**
      Construct and render an Explosion.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
      @param y initial y-coordinate.
      @param diameter diameter of circle after fully expanding.
   */
   public Explosion(SpaceInvaders game, int x, int y, double diameter) {
      super(game, x, y);
      initialDiameter = diameter / MAX_GROWTH_FACTOR;
      finalDiameter = diameter;
      growthFactor = Math.pow(MAX_GROWTH_FACTOR, 1/EXPLOSION_FRAMES);
      
      final double radius = initialDiameter/2;
      GOval explosionDisk = new GOval(
         -radius, -radius, initialDiameter, initialDiameter);
      explosionDisk.setFilled(true);
      add(explosionDisk);
      
      final int R = 100, G = 10, B = 10, OPACITY = 100;
      Color orange = new Color(R, G, B, OPACITY);
      setColor(orange);
      markAsComplete();
   }
   
   /**
      Expand the explosion.
      @param frame number of passed frames.
   */
   @Override
   public void tick(int frame) {
      scale(growthFactor);
      if (getWidth() > finalDiameter)
         destroy();
   }
}