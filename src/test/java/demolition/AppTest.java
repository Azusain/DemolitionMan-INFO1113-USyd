package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import java.util.concurrent.TimeUnit;

public class AppTest {
    
    @Test
    public void sizeTest() {
        assertEquals(480, App.HEIGHT*App.BLOCK_SIZE+App.UI_HEIGHT);
    }

    @Test
    public void FPSTest(){ assertEquals(60,App.FPS);
    }

    //test if Counter works
    @Test
    public void counterTest(){
        Counter c=new Counter(60);
        assertTrue(c.count());
        while(c.count()){

        }
        assertFalse(c.count());
        c.reset();
        assertTrue(c.count());
    }

    @Test
    //test the collision between Bomb and Creature
    public void colliedTest(){
        App app=new App();
        app.player.x=30;
        app.player.y=30;
        App.Bomb b=app.new Bomb(30,30);
        app.hitCreature(b.x,b.y,b);
        assertTrue(b.hit);
    }

    @Test
    //to test if app can load all resources properly
    public void loadResourcesTest(){
        App app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
    }

    @Test
    //to test if the image is loaded properly
    public void drawImageTest(){
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
        app.yellowEnemy.activated=false;
        app.redEnemy.activated=false;
        app.player.activated=false;
        app.delay(1000);
        //image lasts for 3 secs
        app.draw();
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @Test
    //player cannot move through 'W'(wall) that blocks his way
    public void isBlockedTest(){
        App app=new App();
        //draw gameBackground like below
        //  +-----+
        //  | W W |
        //  |   P |
        //  +-----+
        app.g=app.new GameBackground();
        app.g.gamaBackground=new char[][]{{'W','W'},{' ',' '}};
        app.player.x=App.BLOCK_SIZE;
        app.player.y=App.BLOCK_SIZE+App.UI_HEIGHT;
        assertTrue(app.player.isBlocked("UP"));
        assertFalse(app.player.isBlocked("LEFT"));
    }
}

