/**
   @author Abhay Pokhriyal
*/

package edu.illinois.abhayp4;

import acm.graphics.GPolygon;

import java.awt.Color;

/**
   Represents a laser that moves and damage the player.
*/
public class EnemyProjectile extends Projectile {
   private static final int SPEED = 4, DAMAGE = 25;
   
   private static final int SCALE = 15;
   private static final int R = 237, G = 40, B = 57;
   private static final Color red = new Color(R, G, B);
   
   /**
      Constructs and render an EnemyProjectile.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
      @param y initial y-coordinate.
      @param angle angle of direction at which the projectile moves.
   */
   public EnemyProjectile(
      SpaceInvaders game, int x, int y, double angle
   ) {
      super(game, x, y, SPEED, angle, DAMAGE);
      
      final double TOP_X = 0, TOP_Y = -0.7*SCALE;
      final double MIDDLE_LEFT_X = -0.5*SCALE;
      final double MIDDLE_RIGHT_X = 0.5*SCALE;
      final double MIDDLE_Y = 0;
      final double BOTTOM_X = 0, BOTTOM_Y = 0.5*SCALE;
      GPolygon diamond = new GPolygon();
      diamond.addVertex(BOTTOM_X, BOTTOM_Y);
      diamond.addVertex(MIDDLE_RIGHT_X, MIDDLE_Y);
      diamond.addVertex(TOP_X, TOP_Y);
      diamond.addVertex(MIDDLE_LEFT_X, MIDDLE_Y);
      diamond.setFilled(true);
      
      diamond.rotate(-angle);
      add(diamond);
      setColor(red);
      markAsComplete();
   }
   
   /**
      Move and do damage to player if hit.
      @param frame number of passed frames.
   */
   @Override
   public void tick(int frame) {
      super.tick(frame);
      if (didHit(getGame().getPlayer()))
         doDamage(getGame().getPlayer());
   }
}