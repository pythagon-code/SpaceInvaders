/**
   @author Abhay Pokhriyal
*/

package edu.illinois.abhayp4;

/**
   Represents a laser that moves and can do damage.
*/
public abstract class Projectile extends RenderedElement {
   private double speed, angle;
   private int damage;
   
   /**
      Constructs and render an EnemyProjectile.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
      @param y initial y-coordinate.
      @param speed speed of laser.
      @param angle angle of direction at which the projectile moves.
      @param damage damage done to a spacecraft after collision.
   */
   public Projectile(
      SpaceInvaders game, int x, int y, int speed, double angle, int damage
   ) {
      super(game, x, y);
      this.speed = speed;
      this.angle = Math.toRadians(angle);
      this.damage = damage;
   }
   
   /**
      Do damage to a spacecraft and remove the laser.
      @param spacecraft spacecraft to do damage to.
   */
   protected void doDamage(Spacecraft spacecraft) {
      spacecraft.takeDamage(damage);
      destroy();
   }
   
   /**
      Move the laser in the direction.
      @param number of passed frames.
   */
   @Override
   public void tick(int frame) {
      moveBounded(speed*Math.sin(angle), -speed*Math.cos(angle));
   }
}