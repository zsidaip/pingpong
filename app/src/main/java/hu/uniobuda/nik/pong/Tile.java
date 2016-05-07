package hu.uniobuda.nik.pong;

public class Tile {
    public int x,y,x2,y2;//x,y
    public int height,width;//screen size
    public float ex,ey;//x,y tengely menti egységnyi pixelszám
    private int center;//y tengelymenti kozeppontja a teglalapnak
    private int w_size=9;//objektum szelesseg
    private int h_size=50;//objektum magassag
    private int direction,speed;

    public Tile(int x, int y, int height, int width, float ex, float ey){
        this.x = x; this.x2= (int) (this.x+w_size*ex);
        this.y = y; this.y2= (int) (this.y+h_size*ey);
        //this.center=this.y+((this.y2-this.y/2));
        this.height = height;
        this.width = width;
        this.ex = ex;
        this.ey = ey;
    }

    public void setMove(int direction,int speed){
        this.direction=direction;
        this.speed=speed;
    }

    public void Move(){
        this.y+=direction*this.ey*speed;
        if(this.y<0)this.y=0;
        this.y2= (int) (this.y+(h_size*ey));

        if(this.y2>height) {
            this.y2 = height;
            this.y = (int) (height-(h_size*ey));
        }
        this.center=(this.y+Math.round(h_size*this.ey/2));
    }
    public int getCenter(){
        return this.center;
    }
    public int getSpeed(int y){
        return (Math.abs(center - y))/4;
    }
    public boolean isMatch(int y){
        return y<=this.y2&&y>=this.y;
    }
}
