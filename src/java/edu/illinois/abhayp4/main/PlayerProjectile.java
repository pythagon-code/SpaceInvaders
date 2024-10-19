/**
   @author Abhay Pokhriyal
*/

package edu.illinois.abhayp4;

import acm.graphics.GPolygon;

import java.awt.Color;

import java.util.Iterator;

/**
   Represents a laser that moves and can damage aliens.
*/
public class PlayerProjectile extends Projectile {
   private static final int SPEED = 5, DAMAGE = 10;
   
   private static final int SCALE = 15;
   
   /**
      Constructs an EnemyProjectile.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
      @param y initial y-coordinate.
      @param angle angle of direction at which the laser moves.
      @param color color of the laser.
   */
   public PlayerProjectile(
      SpaceInvaders game, int x, int y, double angle, Color color
   ) {
      super(game, x, y, SPEED, angle, DAMAGE);
      
      final double BOTTOM_LEFT_X = -0.5*SCALE, BOTTOM_RIGHT_X = 0.5*SCALE;
      final double BOTTOM_Y = 0;
      final double TOP_X = 0, TOP_Y = -0.7*SCALE;
      final double MIDDLE_X = 0, MIDDLE_Y = -0.2*SCALE;
      GPolygon arrow = new GPolygon();
      arrow.addVertex(MIDDLE_X, MIDDLE_Y);
      arrow.addVertex(BOTTOM_LEFT_X, BOTTOM_Y);
      arrow.addVertex(TOP_X, TOP_Y);
      arrow.addVertex(BOTTOM_RIGHT_X, BOTTOM_Y);
      arrow.setFilled(true);
               
      arrow.rotate(-angle);
      add(arrow);
      setColor(color);
      markAsComplete();
   }
   
   /**
      Move and do damage to aliens if hit.
      @param frame number of passed frames.
   */
   @Override
   public void tick(int frame) {
      super.tick(frame);
      Iterator<RenderedElement> iter = getGame().getRenderedElements();
      while (iter.hasNext()) {
         RenderedElement element = iter.next();
         if (element instanceof AlienSpacecraft && didHit(element)) {
            Spacecraft alien = (Spacecraft) element;
            doDamage(alien);
            return;
         }
      }
   }
}