package dev.brandonlea.Chopper;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(name = "Chopper", author = "Brandonlea", logo = "", version = 1.0, info = "Wood cutting script tutorial")
public class Main extends Script {

    private Area wcArea = new Area(3123, 3456, 3148, 3418),
    bankArea = new Area(3179, 3447, 3190, 3433);

    @Override
    public int onLoop() throws InterruptedException {

        if(!getInventory().contains("Bronze axe")) {
            checkItem();
        } else if(!getInventory().isFull()) {
            chopTree();
        } else {
            bankDeposit();
        }

        return 603;
    }

    private void chopTree() {
        RS2Object tree = getObjects().closest("Tree");

        if(!myPlayer().isAnimating() && tree.isVisible()) {
            new ConditionalSleep(1000, 1500) {
                @Override
                public boolean condition() throws InterruptedException {
                    log("Using Anti-Ban");
                    return false;
                }
            }.sleep();
        }

        if(!myPlayer().isAnimating() && tree != null) {
            if(tree.interact("Chop Down")) {
                new ConditionalSleep(5000, 2000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return !myPlayer().isAnimating();
                    }
                }.sleep();
            }
        } else if(tree == null || !wcArea.contains(myPosition())) {
            getCamera().toEntity(tree);
            log("Searching for Tree...");

            if(getWalking().webWalk(wcArea)) {
                new ConditionalSleep(5000, 2000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return tree.isVisible();
                    }
                }.sleep();
            }
        }
    }

    private void bankDeposit() throws InterruptedException {
        if(!bankArea.contains(myPosition())) {
            if(getWalking().webWalk(bankArea)) {
                new ConditionalSleep(5000, 250) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return false;
                    }
                }.sleep();
            }
        } else if(!bank.isOpen()) {
            bank.open();
        } else {
            bank.depositAllExcept("Bronze axe");
        }
    }

    private void checkItem() throws InterruptedException {
        if(!getInventory().contains("Bronze axe")) {
            if(!bankArea.contains(myPosition())) {
                if(getWalking().webWalk(bankArea)) {
                    new ConditionalSleep(5000, 250) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return false;
                        }
                    }.sleep();
                }
            } else if(!bank.isOpen()) {
                bank.open();
            } else {
                bank.depositAll();
                bank.withdraw("Bronze axe", 1);
                bank.close();
            }
        }
    }



}