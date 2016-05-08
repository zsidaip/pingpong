package hu.uniobuda.nik.pong;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by VIP on 2016.04.20..
 */
public class Game extends View {
    private Rect ore;//kepernyo torlo teglalap
    private Paint black;
    private Paint white;
    Ball ball;//labda
    Tile prec1,prec2;//jatekosok
    private boolean b_init;//van-e mar initializalva a jatek
    byte point1,point2;//jatekosok pontszamai
    AEnemyPlayer enemyPlayer;//bluetooth jatek eseten ellenfel

    public void SetEnemyPlayer(AEnemyPlayer ePlayer)
    {
        enemyPlayer = ePlayer;
    }
    public String getResult() {
        return eredmeny;
    }

    private String eredmeny;//game result

    public String getPoint1() {
        return String.valueOf(point1);
    }

    public String getPoint2() {
        return String.valueOf(point2);
    }

    public Game(Context context) {
        super(context);
        initialize();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Game(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }
    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public Game(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    //jatek kirajzolasa
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.eredmeny!="")return;
        if(!b_init){
            b_init=true;
            init(getWidth(),getHeight());
        }
        int res=ball.Move(prec1, prec2);
        if(res!=0){
            if(res==1){
                point2+=1;
            }else{
                point1+=1;
            }
            if(point1==10)this.eredmeny="Win!";
            if(point2==10)this.eredmeny="Lost!";
        }
        //demo run
        enemyPlayer.calcmove(ball,prec2);

        prec1.Move();
        //clear view
        this.ore.set(0,0, canvas.getWidth(), canvas.getHeight());
        canvas.drawRect(this.ore, this.black);
        //draw ball
        canvas.drawCircle(ball.x, ball.y, ball.size, this.white);
        //draw player1, left
        canvas.drawRect(prec1.x, prec1.y, prec1.x2, prec1.y2, this.white);
        //draw player2
        canvas.drawRect(prec2.x, prec2.y, prec2.x2, prec2.y2, this.white);


        invalidate();
    }
    //szinpaletta beallitasa
    void initialize(){
        ore=new Rect();
        black=new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        white=new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        b_init=false;

        this.eredmeny="";
    }
//meretezesek beallitasa
    private void init(int w, int h){
        enemyPlayer.setWH(w,h);
        float ex=(float)w/(float)480;
        float ey=(float)h/(float)320;
        //int ey=h/295;
        ball=new Ball(10,h,w,ex,ey);
        prec1=new Tile(w-Math.round(10*ex),h/2,h,w,ex,ey);
        prec2=new Tile(1,h/2,h,w,ex,ey);
        point1=0;
        point2=0;
    }
}