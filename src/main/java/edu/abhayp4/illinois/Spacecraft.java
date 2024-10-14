/**
   @author Abhay Pokhriyal
*/

package edu.illinois.abhayp4;

/**
   Represent a spacecraft that can has a health and can explode when destroyed.
*/
public abstract class Spacecraft extends RenderedElement {
   private int health;
   
   /**
      Construct and render a PlayerSpacecraft and display instructions for player.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
      @param y initial y-coordinate.
      @param health maximum health.
   */
   protected Spacecraft(SpaceInvaders game, int x, int y, int health) {
      super(game, x, y);
      this.health = health;
   }
   
   /**
      @return health of spacecraft.
   */
   protected int getHealth() {
      return health;
   }
   
   /**
      Increase health.
   */
   protected void regenerateHealth() {
      final int REGEN = 1;
      health += REGEN;
   }
   
   /**
      Reduce health by an amount.
      @param damage amount to decrease health by.
   */
   public void takeDamage(int damage) {
      health -= damage;
   }
   
   /**
      @return has remaining health.
   */
   public boolean isAlive() {
      return health > 0;
   }
   
   /**
      Explode and remove the spacecraft.
   */
   @Override
   protected void destroy() {
      super.destroy();
      final int blastDiameter = (int)Math.sqrt(
         Math.pow(getWidth(),2) + Math.pow(getHeight(),2));
      final int x = (int)(getX() + getWidth()/2);
      final int y = (int)(getY() + getHeight()/2);
      new Explosion(getGame(), x, y, blastDiameter);
   }
   
   /**
      Destroy the spacecraft if no health remaining.
   */
   @Override
   public void tick(int frame) {
      if (!isAlive())
         destroy();
   }   
}