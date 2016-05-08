package hu.uniobuda.nik.pong;

/**
 * Created by Dani on 5/8/2016.
 */
public class EnemyCompPlayer extends AEnemyPlayer {

    @Override
    public void calcmove(Ball ball, Tile prec2) {
        if (ball.x < width / 2) {
            if (Math.abs((ball.y + ball.size / 2) - prec2.getCenter()) > 2) {
                if (ball.y + ball.size / 2 < prec2.getCenter()) prec2.setMove(-1, 4);
                else prec2.setMove(1, 4);
                prec2.Move();
            }
        }
    }
}
