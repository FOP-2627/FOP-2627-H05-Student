package h05.core;

import fopbot.KarelWorld;
import fopbot.World;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * This class declares the basic properties and methods for all régisseurs.
 * Each concrete régisseur is responsible for creating and managing an individual world.
 */
public abstract class BaseRegisseur {
    protected KarelWorld world;

    protected final int DEFAULT_WORLD_WIDTH;
    protected final int DEFAULT_WORLD_HEIGHT;


    /**
     * Default constructor that initializes a 10x10 world and makes it visible
     */
    @DoNotTouch
    public BaseRegisseur() {
        this.world = World.getGlobalWorld();

        DEFAULT_WORLD_HEIGHT = 10;
        DEFAULT_WORLD_WIDTH = 10;

        initWorld();
    }

    /**
     * Defines the sequence of operations to be executed in the world.
     * First, it registers the sprite rendering sequence, then sets up world objects.
     * After, it spawns robots and defines their execution logic.
     * Finally, it triggers a finishing action to signal completion.
     */
    @DoNotTouch
    public void start() {
        registerSprites();

        setupWorld();

        spawnRobots();
        run();

        onFinish();
    }

    /**
     * Defines how the robots behave within the world.
     */
    @DoNotTouch
    protected abstract void run();

    /**
     * Sets up the world with specific entities and structures after its initialization.
     */
    @DoNotTouch
    protected abstract void setupWorld();

    /**
     * Spawns robots within the world.
     */
    @DoNotTouch
    protected abstract void spawnRobots();

    /**
     * An optional hook to determine if the level's objective have been achieved.
     * @return true if the level goal is reached, false otherwise
     */
    @DoNotTouch
    protected abstract boolean isFinished();

    /**
     * Responsible for registering custom sprites and defining their rendering priority.
     */
    @DoNotTouch
    protected abstract void registerSprites();

    /**
     * Performs actions once the main part of the level is completed.
     */
    @DoNotTouch
    protected void onFinish(){
        System.out.println("Task complete !");
    }


    /**
     * Initializes the world and make it visible.
     */
    @DoNotTouch
    protected void initWorld() {
        World.setSize(DEFAULT_WORLD_WIDTH, DEFAULT_WORLD_HEIGHT);
        World.setVisible(true);
    }
}
