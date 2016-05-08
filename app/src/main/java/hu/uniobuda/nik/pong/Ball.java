package hu.uniobuda.nik.pong;

/**
 * Created by VIP on 2016.04.25..
 *
 */
public class Ball {
    public int x,y,size;//x,y koordinata,sugar
    public int height,width;//screen size
    public float ex,ey;//x,y tengely menti egysegnyi pixelszam
    private int ball_x_direction,ball_y_direction;//labda iranyvektora
    int speed;//labda sebessege

    public Ball(int size, int height, int width, float ex, float ey) {
        this.size = size;
        this.height = height;
        this.width = width;
        this.ex = ex;
        this.ey = ey;
        this.init();
    }

    //labda alapbeallitasa
    public void init(){
        this.ball_x_direction=1;
        this.ball_y_direction=1;
        this.x=this.width/2;
        this.y=this.height/2;
        this.speed=0;
    }

    //labda mozgasanak meghatarozasa a terfelek vegen, es jelzes ha valahol kiment a palyarol
    public int Move(Tile player1,Tile player2) {
        int res=0; boolean setydir=false;
        if (x+this.size >= player1.x && x+this.size <= player1.x2 &&player1.isMatch(y)) {
            if (y+(this.size/2) < player1.getCenter()) ball_y_direction = -1;
            else ball_y_direction = 1;
            this.speed = player1.getSpeed(y);
            ball_x_direction=-1;
            setydir=true;
        }
        if (x<=player2.x2 && x >=player2.x &&player2.isMatch(y)) {
            if (y+(this.size/2) < player2.getCenter()) ball_y_direction = -1;
            else ball_y_direction = 1;
            this.speed = player2.getSpeed(y);
            ball_x_direction=1;
            setydir=true;
        }

        if (x > width) {//player1 vesztett
            init();
            res=1;
        } else if (x - 10 < 0) {//player2 vesztett
            init();
            res=-1;
        }
        if(!setydir) {
            if (y + this.size > height) {
                ball_y_direction = -1;
            } else if (y - size < 0) {
                ball_y_direction = 1;
            }
        }
        this.x += this.ex * 5 * ball_x_direction;
        this.y += this.ey * this.speed * ball_y_direction;

        return res;
    }
}
