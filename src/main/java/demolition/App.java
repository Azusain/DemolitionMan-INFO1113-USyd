package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author azusaings@gmail.com
 * @date 2021-11-3
 * @version 1.0
 * */

public class App extends PApplet {

    //you can change the size of the window by changing the BLOCK_SIZE
    public static int BLOCK_SIZE = 32;

    //the number of the blocks
    public static final int WIDTH = 15;
    public static final int HEIGHT = 13;

    public static final int FPS = 60;

    //size of UI
    public static final int UI_HEIGHT=2*BLOCK_SIZE;

    //current level
    int level=1;

    //declare image var
    PImage wall;
    PImage goal;
    PImage empty;
    PImage broken;
    GameBackground g;
    GameBackground g1;
    GameBackground g2;

    //declare Creature
    Player player=new Player(0,0);
    redEnemy redEnemy=new redEnemy(0,0);
    yellowEnemy yellowEnemy=new yellowEnemy(0,0);


    //store PImage of Bomb
    ArrayList<PImage> bombSprites=new ArrayList<>();
    ArrayList<PImage> explosionSprites=new ArrayList<>();

    //display every bombs in iteration
    ArrayList<Bomb> bombs=new ArrayList<>();


    //Icons
    PImage clock;
    PImage playerLives;

    //Counter
    //set time limit to 130 secs
    Counter gameCounter=new Counter(7800);


    public App() {

    }

    class GameBackground{
        GameBackground(String filePath){
            try {
                BufferedReader br=createReader(filePath);
                for(int i=0;i<HEIGHT;++i){
                    String currLine=br.readLine();
                    for(int j=0;j<WIDTH;++j){
                        //if find player or enemy, store it in elsewhere(R,Y,P)
                        if(currLine.charAt(j)=='R'||currLine.charAt(j)=='Y'||currLine.charAt(j)=='P'){
                            gamaBackground[i][j]=' ';
                            switch (currLine.charAt(j)){
                                case 'R':
                                    redEnemy.x=j*BLOCK_SIZE;
                                    redEnemy.y=i*BLOCK_SIZE+UI_HEIGHT;
                                    break;
                                case 'P':
                                    player.x=j*BLOCK_SIZE;
                                    player.y=i*BLOCK_SIZE+UI_HEIGHT;
                                    break;
                                case 'Y':
                                    yellowEnemy.x=j*BLOCK_SIZE;
                                    yellowEnemy.y=i*BLOCK_SIZE+UI_HEIGHT;
                                    break;
                                default:
                                    break;
                            }
                        }else{
                            gamaBackground[i][j]=currLine.charAt(j);
                        }

                    }
                }
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        GameBackground(){

        }
        char[][] gamaBackground=new char[HEIGHT][WIDTH];
    }

    public void settings() {
        size(WIDTH*BLOCK_SIZE, HEIGHT*BLOCK_SIZE+UI_HEIGHT);
    }

    public void setup() {
        frameRate(60);
        // Load images during setup
        wall=loadImage("src\\main\\resources\\wall\\solid.png");
        empty=loadImage("src\\main\\resources\\empty\\empty.png");
        goal=loadImage("src\\main\\resources\\goal\\goal.png");
        broken=loadImage("src\\main\\resources\\broken\\broken.png");

        //gameBackground
        g1=new GameBackground("src\\main\\resources\\level1.txt");
        g2=new GameBackground("src\\main\\resources\\level2.txt");
//Player
        //player_right
        player.right.add(loadImage("src\\main\\resources\\player\\player_right1.png"));
        player.right.add(loadImage("src\\main\\resources\\player\\player_right2.png"));
        player.right.add(loadImage("src\\main\\resources\\player\\player_right3.png"));
        player.right.add(loadImage("src\\main\\resources\\player\\player_right4.png"));
        //player_left
        player.left.add(loadImage("src\\main\\resources\\player\\player_left1.png"));
        player.left.add(loadImage("src\\main\\resources\\player\\player_left2.png"));
        player.left.add(loadImage("src\\main\\resources\\player\\player_left3.png"));
        player.left.add(loadImage("src\\main\\resources\\player\\player_left4.png"));
        //player_up
        player.up.add(loadImage("src\\main\\resources\\player\\player_up1.png"));
        player.up.add(loadImage("src\\main\\resources\\player\\player_up2.png"));
        player.up.add(loadImage("src\\main\\resources\\player\\player_up3.png"));
        player.up.add(loadImage("src\\main\\resources\\player\\player_up4.png"));
        //player_down
        player.down.add(loadImage("src\\main\\resources\\player\\player1.png"));
        player.down.add(loadImage("src\\main\\resources\\player\\player2.png"));
        player.down.add(loadImage("src\\main\\resources\\player\\player3.png"));
        player.down.add(loadImage("src\\main\\resources\\player\\player4.png"));
//Red_Enemy
        //redEnemy_right
        redEnemy.right.add(loadImage("src\\main\\resources\\red_enemy\\red_right1.png"));
        redEnemy.right.add(loadImage("src\\main\\resources\\red_enemy\\red_right2.png"));
        redEnemy.right.add(loadImage("src\\main\\resources\\red_enemy\\red_right3.png"));
        redEnemy.right.add(loadImage("src\\main\\resources\\red_enemy\\red_right4.png"));
        //redEnemy_left
        redEnemy.left.add(loadImage("src\\main\\resources\\red_enemy\\red_left1.png"));
        redEnemy.left.add(loadImage("src\\main\\resources\\red_enemy\\red_left2.png"));
        redEnemy.left.add(loadImage("src\\main\\resources\\red_enemy\\red_left3.png"));
        redEnemy.left.add(loadImage("src\\main\\resources\\red_enemy\\red_left4.png"));
        //redEnemy_up
        redEnemy.up.add(loadImage("src\\main\\resources\\red_enemy\\red_up1.png"));
        redEnemy.up.add(loadImage("src\\main\\resources\\red_enemy\\red_up2.png"));
        redEnemy.up.add(loadImage("src\\main\\resources\\red_enemy\\red_up3.png"));
        redEnemy.up.add(loadImage("src\\main\\resources\\red_enemy\\red_up4.png"));
        //redEnemy_down
        redEnemy.down.add(loadImage("src\\main\\resources\\red_enemy\\red_down1.png"));
        redEnemy.down.add(loadImage("src\\main\\resources\\red_enemy\\red_down2.png"));
        redEnemy.down.add(loadImage("src\\main\\resources\\red_enemy\\red_down3.png"));
        redEnemy.down.add(loadImage("src\\main\\resources\\red_enemy\\red_down4.png"));
//Yellow_Enemy
        //right
        yellowEnemy.right.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_right1.png"));
        yellowEnemy.right.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_right2.png"));
        yellowEnemy.right.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_right3.png"));
        yellowEnemy.right.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_right4.png"));
        //left
        yellowEnemy.left.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_left1.png"));
        yellowEnemy.left.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_left2.png"));
        yellowEnemy.left.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_left3.png"));
        yellowEnemy.left.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_left4.png"));
        //up
        yellowEnemy.up.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_up1.png"));
        yellowEnemy.up.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_up2.png"));
        yellowEnemy.up.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_up3.png"));
        yellowEnemy.up.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_up4.png"));
        //down
        yellowEnemy.down.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_down1.png"));
        yellowEnemy.down.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_down2.png"));
        yellowEnemy.down.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_down3.png"));
        yellowEnemy.down.add(loadImage("src\\main\\resources\\yellow_enemy\\yellow_down4.png"));
//Bomb
        //Bomb
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb1.png"));
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb2.png"));
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb3.png"));
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb4.png"));
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb5.png"));
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb6.png"));
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb7.png"));
        bombSprites.add(loadImage("src\\main\\resources\\bomb\\bomb8.png"));
        //explosion
        explosionSprites.add(loadImage("src\\main\\resources\\explosion\\centre.png"));
        explosionSprites.add(loadImage("src\\main\\resources\\explosion\\vertical.png"));
        explosionSprites.add(loadImage("src\\main\\resources\\explosion\\horizontal.png"));
//clock
        clock=loadImage("src\\main\\resources\\icons\\clock.png");
        gameCounter.reset();
//playerIcon
        playerLives=loadImage("src\\main\\resources\\icons\\player.png");
    }

    public void draw()
    {
        background(255,140,0);
        if(level==1){
            g=g1;
            if(player.x==13*BLOCK_SIZE&&player.y==11*BLOCK_SIZE+UI_HEIGHT){
                ++level;
                yellowEnemy.activated=false;
                redEnemy.activated=false;
                player.x=BLOCK_SIZE;
                player.y=6*BLOCK_SIZE+UI_HEIGHT;
            }
        }else if(level==2){
            g=g2;
            if(player.x==13*BLOCK_SIZE&&player.y==6*BLOCK_SIZE+UI_HEIGHT){
                g=null;
                player.activated=false;
                textSize(40);
                fill(0);
                text("YOU WIN",150,250);
            }
        }
        //load gameBackground from char[][] array
        if(g!=null){
            for(int row=0;row<HEIGHT;++row){
                for(int col=0;col<WIDTH;++col) {
                    switch (g.gamaBackground[row][col]){
                        case 'W':
                            image(wall,col*BLOCK_SIZE,row*BLOCK_SIZE+UI_HEIGHT,BLOCK_SIZE,BLOCK_SIZE);
                            break;
                        case 'G':
                            image(goal,col*BLOCK_SIZE,row*BLOCK_SIZE+UI_HEIGHT,BLOCK_SIZE,BLOCK_SIZE);
                            break;
                        case 'B':
                            image(broken,col*BLOCK_SIZE,row*BLOCK_SIZE+UI_HEIGHT,BLOCK_SIZE,BLOCK_SIZE);
                            break;
                        case ' ':
                            image(empty,col*BLOCK_SIZE,row*BLOCK_SIZE+UI_HEIGHT,BLOCK_SIZE,BLOCK_SIZE);
                            break;
                        case 'O':
                            image(empty,col*BLOCK_SIZE,row*BLOCK_SIZE+UI_HEIGHT,BLOCK_SIZE,BLOCK_SIZE);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        //load player and enemies

        player.move();
        redEnemy.move();
        yellowEnemy.move();

        //display bombs
        if(g!=null){
            ArrayList<Integer> trashSet=new ArrayList<>();
            int count=0;
            for(Bomb b:bombs){
                if(!b.activated){
                    for(Position p:b.brokens){
                        g.gamaBackground[p.y][p.x]=' ';
                    }
                    trashSet.add(count);
                }else {
                    b.display();
                }
                ++count;
            }

            //clear inactivated bombs and calculate dmg to player
            for(int NTrash:trashSet){
                if(bombs.get(NTrash).hit){
                    player.HP--;
                }
                bombs.remove(NTrash);
            }
        }

        if(player.HP<=0||!gameCounter.count()){
            g=null;
            player.activated=false;
            background(255,140,0);
            textSize(40);
            fill(0);
            text("YOU LOSE",150,250);
        }


        if(g!=null){
            //playerLives
            image(playerLives,100,20,32,32);
            textSize(25);
            fill(0);
            text(player.HP,150,45);
            //clock
            image(clock,300,20,32,32);
            text(gameCounter.currentCount/60,330,45);
        }

    }


    /**
     * to detect if the bomb hits the creatures and do correspond action
     * @param bomb certain bomb is passed in
     * */
    void hitCreature(int x,int y,Bomb bomb){
        if (player.x==x&&player.y==y){
            bomb.hit=true;
        }
        if (yellowEnemy.x==x&&yellowEnemy.y==y){
            yellowEnemy.activated=false;
        }
        if (redEnemy.x==x&&redEnemy.y==y){
            redEnemy.activated=false;
        }
    }


    /**
     * to make sure the player move with keyboard pressed and the released
     * */
    public void keyReleased(){
        //insure the bomb guy move with key Pressed and Released
        player.movementLock=false;
        player.bombLock=false;
    }


    /**
     * to store the coordinates of certain object
     * */
    class Position {
        Position(int x,int y){
            this.x=x;
            this.y=y;
        }
        int x;
        int y;
        boolean activated=true;
    }


//Each Creature has 4 states up,down,left and right
//each direction has 3 states representing different images


    /**
     * represent player and enemies
     * */
    class Creature extends Position{
        Creature(int x,int y){
            super(x,y);
            up=new ArrayList<>();
            down=new ArrayList<>();
            left=new ArrayList<>();
            right=new ArrayList<>();

        }


        //detect if the way is blocked is certain direction

        /**
         * @param direction detect if the way is blocked in certain direction
         * @return boolean  if the way is blocked, return true.
         * */

        boolean isBlocked(String direction){
            int x_block=x/BLOCK_SIZE;
            int y_block=(y-UI_HEIGHT)/BLOCK_SIZE;
            switch(direction){
                case "UP":
                    if(y_block<0||g.gamaBackground[y_block-1][x_block]!=' '&&g.gamaBackground[y_block-1][x_block]!='G'){
                        return true;
                    }
                    break;
                case "DOWN":
                    if(y_block>App.HEIGHT||g.gamaBackground[y_block+1][x_block]!=' '&&g.gamaBackground[y_block+1][x_block]!='G'){
                        return true;
                    }
                    break;
                case "LEFT":
                    if(x_block<0||g.gamaBackground[y_block][x_block-1]!=' '&&g.gamaBackground[y_block][x_block-1]!='G'){
                        return true;
                    }
                    break;
                case "RIGHT":
                    if(x_block>App.WIDTH||g.gamaBackground[y_block][x_block+1]!=' '&&g.gamaBackground[y_block][x_block+1]!='G'){
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }

        void move(){}



        /**
         * display image after position was handled
         * */
        void display(){
            switch (state){
                case "UP":
                    //make sure every sprite maintains 0.2 sec
                    image(up.get(upState/12),x,y,WIDTH,HEIGHT);
                    if(upState>=47){
                        image(up.get(0),x,y,WIDTH,HEIGHT);
                    }else {
                        ++upState;
                    }
                    break;
                case "DOWN":
                    image(down.get(downState/12),x,y,WIDTH,HEIGHT);
                    if(downState>=47){
                        image(down.get(0),x,y,WIDTH,HEIGHT);
                    }else {
                        ++downState;
                    }
                    break;
                case "LEFT":
                    image(left.get(leftState/12),x,y,WIDTH,HEIGHT);
                    if(leftState>=47){
                        image(left.get(0),x,y,WIDTH,HEIGHT);
                    }else {
                        ++leftState;
                    }
                    break;
                case "RIGHT":
                    image(right.get(rightState/12),x,y,WIDTH,HEIGHT);
                    if(rightState>=47){
                        image(right.get(0),x,y,WIDTH,HEIGHT);
                    }else {
                        ++rightState;
                    }
                    break;
                default:
                    break;
            }
        }


        //do action
        String state;
        ArrayList<PImage> up;
        ArrayList<PImage> down;
        ArrayList<PImage> left;
        ArrayList<PImage> right;

        //set the size of the image
        static final int WIDTH=24;
        static final int HEIGHT=32;

        //to make a counter
        int upState=0;
        int downState=0;
        int leftState=0;
        int rightState=0;

        //to realize random direction or clock-wise direction
        String[] direction=new String[]{"UP","RIGHT","DOWN","LEFT"};

        int HP;
    }

    class Items extends Position{
        Items(int x,int y){
            super(x,y);
        }
    }

    class Bomb extends Items{
        Bomb(int x,int y){
            super(x,y);
            bombCounter=new Counter(15);
            expCounter=new Counter(30);
            brokens=new ArrayList<>();
        }


        /**
         * generate animation of the bomb
         * */
        void display(){

            //prepare to explode
            if(!bombCounter.count()){
                ++bombState;
                if(bombState<8){
                    bombCounter.reset();
                }
            }
            if(bombState<8){
                image(bombSprites.get(bombState),x,y,WIDTH,HEIGHT);
            }else {
                //explosion
                int x_block=x/BLOCK_SIZE;
                int y_block=(y-UI_HEIGHT)/BLOCK_SIZE;
                if(expCounter.count()){

                    //centre
                    image(explosionSprites.get(0),x,y,WIDTH,HEIGHT);

                    //generate shockwave in 4 directions
                    // vertical
                    int verTop=(y_block>=2)?y_block-2:0;
                    int verBottom=(y_block<=12)?y_block+2:12;
                    //DOWN
                    int currPos=y_block+1;
                    while(currPos<=verBottom){
                        //break gameBackground
                        if(g.gamaBackground[currPos][x_block]!='W'){
                            image(explosionSprites.get(1),x_block*BLOCK_SIZE,currPos*BLOCK_SIZE+UI_HEIGHT,WIDTH,HEIGHT);
                            hitCreature(x_block*BLOCK_SIZE,currPos*BLOCK_SIZE+UI_HEIGHT,this);
                            if(g.gamaBackground[currPos][x_block]=='B'){
                                brokens.add(new Position(x_block,currPos));
                                break;
                            }
                        }else {
                            break;
                        }
                        ++currPos;
                    }

                    //UP
                    currPos=y_block-1;
                    while(currPos>=verTop){
                        if(g.gamaBackground[currPos][x_block]!='W'){
                            image(explosionSprites.get(1),x_block*BLOCK_SIZE,currPos*BLOCK_SIZE+UI_HEIGHT,WIDTH,HEIGHT);
                            hitCreature(x_block*BLOCK_SIZE,currPos*BLOCK_SIZE+UI_HEIGHT,this);
                            if(g.gamaBackground[currPos][x_block]=='B'){
                                brokens.add(new Position(x_block,currPos));
                                break;
                            }
                        }else {
                            break;
                        }
                        --currPos;
                    }
                    //horizontal
                    int horTop=(x_block>=2)?x_block-2:0;
                    int horBottom=(x_block<=12)?x_block+2:14;
                    //RIGHT
                    currPos=x_block+1;
                    while(currPos<=horBottom){
                        if(g.gamaBackground[y_block][currPos]!='W'){
                            image(explosionSprites.get(2),currPos*BLOCK_SIZE,y_block*BLOCK_SIZE+UI_HEIGHT,WIDTH,HEIGHT);
                            hitCreature(currPos*BLOCK_SIZE,y_block*BLOCK_SIZE+UI_HEIGHT,this);
                            if(g.gamaBackground[y_block][currPos]=='B'){
                                brokens.add(new Position(currPos,y_block));
                                break;
                            }
                        }else{
                            break;
                        }
                        ++currPos;
                    }
                    //LEFT
                    currPos=x_block-1;
                    while(currPos>=horTop){
                        if(g.gamaBackground[y_block][currPos]!='W'){
                            image(explosionSprites.get(2),currPos*BLOCK_SIZE,y_block*BLOCK_SIZE+UI_HEIGHT,WIDTH,HEIGHT);
                            hitCreature(currPos*BLOCK_SIZE,y_block*BLOCK_SIZE+UI_HEIGHT,this);
                            if(g.gamaBackground[y_block][currPos]=='B'){
                                brokens.add(new Position(currPos,y_block));
                                break;
                            }
                        }else{
                            break;
                        }
                        --currPos;
                    }
                }else {
                    g.gamaBackground[y_block][x_block]=' ';
                    this.activated=false;
                }

            }

        }

        int bombState=0;
        Counter bombCounter;
        Counter expCounter;
        ArrayList<Position> brokens;
        final int WIDTH=BLOCK_SIZE;
        final int HEIGHT=BLOCK_SIZE;
        public boolean hit=false;
    }

    class Player extends Creature{
        Player(int x,int y){
            super(x,y);
            state="DOWN";
            this.HP=3;

        }


        /**
         * control your player with  w,a,s,d
         * */
        public void move(){
            if(!activated){
                return;
            }
            //move in 4 directions
            //control
            if(keyPressed&&key=='w'){
                state="UP";
                //detect if it reaches the up_boundary
                if(!isBlocked("UP")&&!movementLock){
                    y-=BLOCK_SIZE;
                }
                //refresh the sprite
                if(upState==47){
                    upState=0;
                }
                movementLock=true;
            //DOWN
            }else if(keyPressed&&key=='s'){
                if(!state.equals("DOWN")){
                    initialize();
                }
                state="DOWN";
                if(!isBlocked("DOWN")&&!movementLock){
                    y+=BLOCK_SIZE;
                }
                if(downState==47){
                    downState=0;
                }
                movementLock=true;
            //LEFT
            }else if(keyPressed&&key=='a'){
                if(!state.equals("LEFT")){
                    initialize();
                }
                state="LEFT";
                if(!isBlocked("LEFT")&&!movementLock){
                    x-=BLOCK_SIZE;
                }
                if(leftState==47){
                    leftState=0;
                }
                movementLock=true;
            //RIGHT
            }else if(keyPressed&&key=='d'){
                if(!state.equals("RIGHT")){
                    initialize();
                }
                state="RIGHT";
                if(!isBlocked("RIGHT")&&!movementLock){
                    x+=BLOCK_SIZE;
                }
                if(rightState==47){
                    rightState=0;
                }
                movementLock=true;
                //stay its ground but face to certain direction
            } else if(keyPressed&&keyCode==SHIFT&&!bombLock){
                //Bomb placement
                bombs.add(new Bomb(x,y));
                g.gamaBackground[(y-UI_HEIGHT)/BLOCK_SIZE][x/BLOCK_SIZE]='O';
                bombLock=true;
            }
            display();
        }

        /**
         * initialize the action_state
         * */
        void initialize(){
            upState=0;
            downState=0;
            leftState=0;
            rightState=0;
        }

        //insure the bomb guy move with key Pressed and Released
        boolean movementLock=false;
        boolean bombLock;

    }

    class redEnemy extends Creature{
        redEnemy(int x,int y){
            super(x,y);
            state="RIGHT";
            counter=new Counter(60);
        }

        //moves straight and turns randomly
        //stop 1 sec after every move
        public void move(){
            if(!activated){
                return;
            }
            //control
            if(!counter.count()){
                switch (state){
                    case "UP":
                        if(isBlocked("UP")){
                            state=direction[(int)(Math.random()*direction.length)];
                        }else {
                            state="UP";
                            upState=0;
                            y-=BLOCK_SIZE;
                        }
                        break;
                    case "DOWN":
                        if(isBlocked("DOWN")){
                            state=direction[(int)(Math.random()*direction.length)];
                        }else {
                            state="DOWN";
                            downState=0;
                            y+=BLOCK_SIZE;
                        }
                        break;
                    case "LEFT":
                        if(isBlocked("LEFT")){
                            state=direction[(int)(Math.random()*direction.length)];
                        }else {
                            state="LEFT";
                            leftState=0;
                            x-=BLOCK_SIZE;
                        }
                        break;
                    case "RIGHT":
                        if(isBlocked("RIGHT")){
                            state=direction[(int)(Math.random()*direction.length)];
                        }else {
                            state="RIGHT";
                            rightState=0;
                            x+=BLOCK_SIZE;
                        }
                    default:
                        break;
                }
                counter.reset();
            }
            //display
            display();

        }

        Counter counter;
    }

    class yellowEnemy extends Creature{
        yellowEnemy(int x,int y){
            super(x,y);
            state="RIGHT";
            counter=new Counter(60);
        }


        /**
         * turns clock-wise
         * */
        public void move(){
            if(!activated){
                return;
            }
            //control
            if(!counter.count()){
                switch (state){
                    case "UP":
                        if(isBlocked("UP")){
                            state="RIGHT";
                        }else {
                            state="UP";
                            upState=0;
                            y-=BLOCK_SIZE;
                        }
                        break;
                    case "DOWN":
                        if(isBlocked("DOWN")){
                            state="LEFT";
                        }else {
                            state="DOWN";
                            downState=0;
                            y+=BLOCK_SIZE;
                        }
                        break;
                    case "LEFT":
                        if(isBlocked("LEFT")){
                            state="UP";
                        }else {
                            state="LEFT";
                            leftState=0;
                            x-=BLOCK_SIZE;
                        }
                        break;
                    case "RIGHT":
                        if(isBlocked("RIGHT")){
                            state="DOWN";
                        }else {
                            state="RIGHT";
                            rightState=0;
                            x+=BLOCK_SIZE;
                        }
                    default:
                        break;
                }
                counter.reset();
            }
            display();
        }

        Counter counter;
    }




    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}


/**
 * this class is used for counting the time
 * */
class Counter{
    Counter(int ticks){
        this.duration=ticks;
        this.currentCount=ticks;
    }

    /**
     * count and if time's up,return false
     * @return boolean if time's up,return false
     * */
    boolean count(){
        if(currentCount==0){
            return false;
        }
        --currentCount;
        return true;
    }


    /**
     * reset the counter to the origin duration
     * */
    void reset(){
        currentCount=duration;
    }


    int duration;
    int currentCount;
}

