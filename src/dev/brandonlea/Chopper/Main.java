package dev.brandonlea.Chopper;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(name = "Chopper", author = "Brandonlea", logo = "", version = 1.0, info = "Wood cutting script tutorial")
public class Main extends Script {

    private Area wcArea = new Area(3123, 3456, 3148, 3418);

    @Override
    public int onLoop() throws InterruptedException {

        if(!wcArea.contains(myPlayer())) {
            if(getWalking().webWalk(wcArea)) {
                new ConditionalSleep(2000, 5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return false;
                    }
                }.sleep();
            }
        } else {
            RS2Object tree = getObjects().closest("Tree");

            if(tree != null) {
                if(!myPlayer().isAnimating()) {
                    tree.interact("Chop down");

                    getMouse().moveOutsideScreen();

                    sleep(random(2000, 5000));
                }
            }

        }


        return 602;
    }



}
