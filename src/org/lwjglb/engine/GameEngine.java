package org.lwjglb.engine;

import net.eternalconflict.www.gamewindow.menus.MainMenu;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameEngine implements Runnable {

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private final Window window;

    private final Thread gameLoopThread;

    private final Timer timer;

    private final IGameLogic gameLogic;

    private final MouseInput mouseInput;

    private double lastFps;
    
    private int fps;
    
    private String windowTitle;

    private MainMenu.STATE State = MainMenu.STATE.MENU;

    private MainMenu menu;


    public GameEngine(String windowTitle, boolean vSync, Window.WindowOptions opts, IGameLogic gameLogic) throws Exception {
        this(windowTitle, 0, 0, vSync, opts, gameLogic);
    }

    public GameEngine(String windowTitle, int width, int height, boolean vSync, Window.WindowOptions opts, IGameLogic gameLogic) throws Exception {
        this.windowTitle = windowTitle;
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync, opts);
        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
        lastFps = timer.getTime();
        fps = 0;
    }

    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        menu = new MainMenu();
        boolean running = true;
        while (running && !window.windowShouldClose()) {
            if(State == MainMenu.STATE.GAME) {
                elapsedTime = timer.getElapsedTime();
                accumulator += elapsedTime;
                input();
            }
            else if(State == MainMenu.STATE.MENU){
               if(window.isKeyPressed(KeyEvent.VK_SPACE)){
                   State = MainMenu.STATE.GAME;
               }
            }
            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }
            render();
            if (!window.isvSync()) {
                sync();
            }

        }
    }

    protected void cleanup() {
        gameLogic.cleanup();
    }
    
    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

    protected void input() {
        mouseInput.input(window);
        gameLogic.input(window, mouseInput);
    }

    protected void update(float interval) {
        gameLogic.update(interval, mouseInput, window);
    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }
    
    
    protected void render() {
        if ( window.getWindowOptions().showFps && timer.getLastLoopTime() - lastFps > 1 ) {
            lastFps = timer.getLastLoopTime();
            window.setWindowTitle(windowTitle + " - " + fps + " FPS");
            fps = 0;
        }
        fps++;
        gameLogic.render(window);
        window.update();
    }

}
