/**
   @author Abhay Pokhriyal
*/

import java.awt.Color;

/**
   Represents an alien that can shoot straight lasers.
*/
public class TroopAlien extends AlienSpacecraft {
   
   private static int HEALTH = 100, POINTS = 10;
   
   private static final int R = 0, G = 244, B = 0;
   private static Color green = new Color(R, G, B);
   
   /**
      Construct and render a PlayerSpacecraft and display instructions for player.
      @param game SpaceInvaders program game.
      @param x initial x-coordinate.
   */
   public TroopAlien(SpaceInvaders game, int x) {
      super(game, x, HEALTH, green, POINTS);
   }
   
   /**
      Move the alien and maybe shoot.
      @param frame number of passed frames.
   */
   @Override
   public void tick(int frame) {
      super.tick(frame);
      final double ATTACK_RARITY = 0.006;
      if (Math.random() <= ATTACK_RARITY) {
         final int ANGLE = 180;
         final int x = (int)(getX() + getWidth()/2);
         final int y = (int)(getY() + getHeight()/2);
         final int Y_OFFSET = 10;
         new EnemyProjectile(getGame(), x, y + Y_OFFSET, ANGLE);
      }
   }
}