package hu.uniobuda.nik.pong;

abstract public class  AEnemyPlayer {
    public int width, height;

    public void setWH(int w, int h){width = w; height = h;}

    abstract public void calcmove(Ball b, Tile t);
}
