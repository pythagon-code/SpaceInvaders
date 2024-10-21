/**
   @author Abhay Pokhriyal
*/

package edu.illinois.abhayp4;

import java.awt.Color;

/**
   Represents an alien that can shoot lasers in a 3-shot spread pattern.
   Has greater health than TroopAlien.
*/
public class ScatterBeamAlien extends AlienSpacecraft {
   private static int HEALTH = 200, POINTS = 20;
   
   private static final int R = 236, G = 59, B = 179;
   private static final Color pink = new Color(R, G, B);
   
   /**
      Construct and render a PlayerSpacecraft and display instructions for player.
      @param game SpaceInvaders program game.
   */
   public ScatterBeamAlien(SpaceInvaders game, int x) {
      super(game, x, HEALTH, pink, POINTS);
   }
   
   /**
      Move the alien and maybe shoot in a 3-shot spread pattern.
      @frame number of passed frames.
   */
   @Override
   public void tick(int frame) {
      super.tick(frame);
      final double ATTACK_RARITY = 0.0045;
      if (Math.random() <= ATTACK_RARITY) {
         final int ANGLE = 180;
         final int SPREAD = ANGLE + 20;
         final int x = (int)(getX() + getWidth()/2);
         final int y = (int)(getY() + getHeight()/2);
         final int Y_OFFSET = 10;
         new EnemyProjectile(getGame(), x, y + Y_OFFSET, -SPREAD);
         new EnemyProjectile(getGame(), x, y + Y_OFFSET, ANGLE);
         new EnemyProjectile(getGame(), x, y + Y_OFFSET, SPREAD);
      }
   }
}