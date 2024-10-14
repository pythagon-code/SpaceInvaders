/**
   @author Abhay Pokhriyal
*/

package edu.illinois.abhayp4;

import acm.graphics.GCompound;
import acm.graphics.GObject;

/**
   Represents an element that does something every tick of the game.
*/
public abstract class RenderedElement extends GCompound {
   private SpaceInvaders game;
   
   /**
      Constructs and add self to the display list.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
      @param y initial y-coordinate.
   */
   protected RenderedElement(SpaceInvaders game, int x, int y) {
      this.game = game;
      setLocation(x, y);
      game.displayElement(this);
   }
   
   /**
      @return SpaceInvaders program.
   */
   protected SpaceInvaders getGame() {
      return game;
   }
   
   /**
      Move and destroy element if moves out of bounds.
      @param dx amount of x to move by.
      @param dy amount of y to move by.
   */
   protected void moveBounded(double dx, double dy) {
      move(dx, dy);
      final int DESPAWN_DISTANCE = 200;
      if (
         getX() + getWidth() < -DESPAWN_DISTANCE
         || getX() > game.getWidth() + DESPAWN_DISTANCE
         || getY() + getHeight() < -DESPAWN_DISTANCE
         || getY() > game.getHeight() + DESPAWN_DISTANCE
      )
         destroy();
   }
   
   /**
      @return whether the element contacts the other element.
   */
   protected boolean didHit(RenderedElement other) {
      return getBounds().intersects(other.getBounds());
   }
   
   /**
      Destroy element from game and display.
   */
   protected void destroy() {
      game.destroyElement(this);
   }
   
   /**
      Does something every tick of the game.
      @param frame number of passed frames.
   */
   public abstract void tick(int frame);
}