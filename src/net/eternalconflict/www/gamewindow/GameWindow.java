package net.eternalconflict.www.gamewindow;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.EternalConflict;
import net.eternalconflict.www.Launcher;
import net.eternalconflict.www.enums.ObjectTypeEnum;
import net.eternalconflict.www.gamewindow.hud.gui.CursorGui;
import net.eternalconflict.www.handlers.SocketHandler;
import net.eternalconflict.www.holders.PlayerHolder;
import net.eternalconflict.www.holders.objects.*;
import net.eternalconflict.www.maps.SolarSystemMap;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.*;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Renderer;
import org.lwjglb.engine.graph.font.rendering.FontRenderer;
import org.lwjglb.engine.graph.gui.rendering.GuiManger;
import org.lwjglb.engine.graph.gui.rendering.GuiRenderer;
import org.lwjglb.engine.graph.lights.DirectionalLight;
import org.lwjglb.engine.graph.lights.PointLight;
import org.lwjglb.engine.graph.other.Loader;
import org.lwjglb.engine.items.GameItem;
import org.lwjglb.engine.loaders.assimp.StaticMeshesLoader;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class GameWindow implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;


    private Scene scene;

    private CursorGui cursor;
    private static final float CAMERA_POS_STEP = 0.40f;

    private float angleInc;

    private float lightAngle;

    private boolean firstTime;

    private boolean sceneChanged;
    private boolean meshChanged = false;

    private Vector3f pointLightPos;
    private boolean loaded = false;

    private Window window;

    private GuiManger guiManger;

    private DefaultObject sunObject = null;
    private DefaultObject selectedObject = null;
    private DefaultObject shipObject = null;

    private float cameraMoveSpeed = 1;
    private long lastRefresh = 0;
    public static GameWindow instance;

    public GameWindow() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        angleInc = 0;
        lightAngle = 90;
        firstTime = true;
        instance = this;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
    public float getCameraMoveSpeed() {
        return cameraMoveSpeed;
    }

    public void setCameraMoveSpeed(float cameraMoveSpeed) {
        this.cameraMoveSpeed = cameraMoveSpeed;
    }

    public boolean isMeshChanged() {
        return meshChanged;
    }

    public void setMeshChanged(boolean meshChanged) {
        this.meshChanged = meshChanged;
    }

    public static Mesh[] getMesh(ObjectTypeEnum objectTypeEnum, String subTypeName)
    {
        try {

            return StaticMeshesLoader.load("resources/models/" + objectTypeEnum.getFolder() + "/" + subTypeName + ".obj", "resources/textures/" + objectTypeEnum.getFolder());
        } catch (Exception e) {
            try {
                return StaticMeshesLoader.load("resources/models/" + objectTypeEnum.getFolder() + "/NotFound.obj", "resources/textures/" + objectTypeEnum.getFolder());
            } catch (Exception e2) {
                System.out.println(objectTypeEnum.getFolder() + "," + subTypeName);
                return null;
            }
        }
    }
    public GameItem[] getGameItems()
    {
        List<GameItem> planets = new ArrayList<GameItem>();
        SolarSystemMap solarSystemMap = PlayerHolder.player.getViewing();
        List<DefaultObject> defaultObjects = solarSystemMap.getObjects();
        for(DefaultObject defaultObject: defaultObjects)
        {
            planets.add(defaultObject.gameItem);
        }
        return planets.toArray(new GameItem[planets.size()]);
    }
    public Window getWindow()
    {
        return window;
    }
    public Camera getCamera() {
        return camera;
    }
    @Override
    public void init(Window window) throws Exception {
        try {
            this.window = window;
            renderer.init(window);
            scene = new Scene();


            updateMeshes();

            // Shadows
            scene.setRenderShadows(false);


            // Fog
            //Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
            //scene.setFog(new Fog(true, fogColour, 0.02f));


            // Setup  SkyBox
            //float skyBoxScale = 100.0f;
            //SkyBox skyBox = new SkyBox("resources/models/skybox.obj", new Vector4f(0.65f, 0.65f, 0.65f, 10.0f));
            //skyBox.setScale(skyBoxScale);
            //scene.setSkyBox(skyBox);

            // Setup Lights
            setupLights();


            //TextMaster.init();

            //FontType font = FontTypeEnum.getFontType(FontTypeEnum.Verdana, window);
            //GuiLabels text = new GuiLabels("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", 1, font, new Vector2f(0f,0.5f), 1f, true);
            //text.setColour(1,0,0);
            new GuiRenderer();
            new FontRenderer();

            List<DefaultObject> allthings = SolarSystemMap.viewing.getObjects();
            for(DefaultObject defaultObject: allthings) {
                GuiManger hud = defaultObject.getHudOverlay();
                if (hud == null)
                {
                    defaultObject.setupHud();
                }
            }

            guiManger = new GuiManger("textures/window.png", "Planet: NA", new Vector2f(0, 0), new Vector2f(300, 100), false);
            guiManger.setVisible(false);
            GuiManger.addGuis(guiManger);

            if (sunObject != null) {
                selectObject(sunObject);
            }

            cursor = new CursorGui();
            glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            this.setLoaded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMeshes() {
        List<GameItem> planets = new ArrayList<GameItem>();
        SolarSystemMap solarSystemMap = PlayerHolder.player.getViewing();
        List<DefaultObject> defaultObjects = solarSystemMap.getObjects();
        int i = 0;
        for(DefaultObject defaultObject: defaultObjects)
        {
            if (defaultObject instanceof PlanetObject) {
                PlanetObject planetObject = (PlanetObject)defaultObject;
                GameItem planet = new GameItem(GameWindow.getMesh(defaultObject.getObjectType(), planetObject.getPlanetType().getName()));
                planet.setPosition((float) defaultObject.getPosition().getX(), 0f, (float) defaultObject.getPosition().getZ());
                planet.setScale((float) ((PlanetObject) defaultObject).getSize());
                planetObject.setGameItem(planet);
                planets.add(planet);
            }
            if (defaultObject instanceof StarObject) {
                StarObject starObject = (StarObject)defaultObject;
                if (sunObject == null) sunObject = defaultObject;
                GameItem star = new GameItem(GameWindow.getMesh(defaultObject.getObjectType(), starObject.getStarType().getName()));
                star.setPosition((float) defaultObject.getPosition().getX() , 0f, (float) defaultObject.getPosition().getZ() );
                star.setScale((float) ((StarObject) defaultObject).getSize() );
                starObject.setGameItem(star);
                planets.add(star);
             }
            if (defaultObject instanceof StationObject) {

                StationObject starObject = (StationObject)defaultObject;
                sunObject = defaultObject;
                System.out.println("Station Found!");
                GameItem star = new GameItem(GameWindow.getMesh(defaultObject.getObjectType(), "StationBeta"));//MinningShip
                star.setPosition((float) defaultObject.getPosition().getX() , 0f, (float) defaultObject.getPosition().getZ() );
                star.setScale((float) ((StationObject) defaultObject).getSize() );
                starObject.setGameItem(star);
                planets.add(star);
            }
            if (defaultObject instanceof MiningShipObject) {
                shipObject = defaultObject;
                MiningShipObject miningShipObject = (MiningShipObject)defaultObject;
                System.out.println("Mining Ship Loaded");
                GameItem star = new GameItem(GameWindow.getMesh(defaultObject.getObjectType(), "MinningShip"));//MinningShip
                star.setPosition((float) defaultObject.getPosition().getX() , 0f, (float) defaultObject.getPosition().getZ() );
                star.setScale((float) ((MiningShipObject) defaultObject).getSize() );
                miningShipObject.setGameItem(star);
                planets.add(star);
            }

i++;

        }

 /*       Font FONT = new Font("Arial", Font.PLAIN, 20);
        String CHARSET = "ISO-8859-1";
        FontTexture test = new FontTexture(FONT, CHARSET);
        TextItem statusTextItem = new TextItem("test", test);
        //statusTextItem.getMesh().getMaterial().setSpecularColour(new Vector4f(1, 1, 1, 1));
        planets.add(statusTextItem);*/


        scene.setGameItems(planets.toArray(new GameItem[planets.size()]));
    }
    public void refreshView()
    {
        if (System.currentTimeMillis() - lastRefresh > 500) {
            lastRefresh = System.currentTimeMillis();
            List<DefaultObject> allthings = SolarSystemMap.viewing.getObjects();
            List<GameItem> planets = new ArrayList<GameItem>();
            for (DefaultObject defaultObject : allthings) {
                GameItem gameItem = defaultObject.getGameItem();
                if (gameItem != null) {
                    gameItem.setPosition(defaultObject.getPosition().getFloatX() - selectedObject.getPosition().getFloatX(), defaultObject.getPosition().getFloatY() - selectedObject.getPosition().getFloatY(), defaultObject.getPosition().getFloatZ() - selectedObject.getPosition().getFloatZ());
                    if (gameItem.getPosition().distance(selectedObject.getGameItem().getPosition()) < 1000) {
                        planets.add(gameItem);
                    }
                }
            }
            scene.setGameItems(planets.toArray(new GameItem[planets.size()]));
        }
    }
    private void selectObject(DefaultObject selectMe) {
        if (selectMe == null) return;
        selectedObject = selectMe;
        List<DefaultObject> allthings = SolarSystemMap.viewing.getObjects();
        List<GameItem> planets = new ArrayList<GameItem>();
        for(DefaultObject defaultObject: allthings)
        {
            GameItem gameItem = defaultObject.getGameItem();
            if (gameItem != null) {
                gameItem.setPosition(defaultObject.getPosition().getFloatX() - selectedObject.getPosition().getFloatX(), defaultObject.getPosition().getFloatY() - selectedObject.getPosition().getFloatY(), defaultObject.getPosition().getFloatZ() - selectedObject.getPosition().getFloatZ());
                if (gameItem.getPosition().distance(selectedObject.getGameItem().getPosition()) < 1000) {
                    planets.add(gameItem);
                }
            }
        }

        GameItem gameItem = selectMe.getGameItem();
        gameItem.setSelected(false);
        camera.setPosition(gameItem.getPosition().x + gameItem.getScale() * 2, gameItem.getPosition().y + gameItem.getScale() * 2, gameItem.getPosition().z + gameItem.getScale() * 2);
        camera.setRotation(32f, -43f, 0);
        GameWindow.instance.setCameraMoveSpeed(gameItem.getScale() / 2);

        scene.setGameItems(planets.toArray(new GameItem[planets.size()]));
    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0f, 0f, 0f));
        //sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 0.0f;
        Vector3f lightDirection = new Vector3f(0, 1, 1);
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightDirection, lightIntensity);
        sceneLight.setDirectionalLight(directionalLight);

        pointLightPos = new Vector3f(0.0f, 0.0f, 0.0f);
        lightIntensity = 0.70f;

        Vector3f pointLightColour = new Vector3f(1.0f, 1.0f, 1.0f);

        PointLight.Attenuation attenuation = new PointLight.Attenuation(0.75f, 0.6f, 0.3f);

        PointLight pointLight = new PointLight(pointLightColour, pointLightPos, lightIntensity, attenuation);
        sceneLight.setPointLightList( new PointLight[] {pointLight});

    }

    public DefaultObject getDefaultObject(GameItem gameItem)
    {
        List<DefaultObject> all = PlayerHolder.player.getViewing().getObjects();
        for(DefaultObject defaultObject: all)
        {
            if (defaultObject.getGameItem() == gameItem) return defaultObject;
        }
        return null;
    }
    @Override
    public void input(Window window, MouseInput mouseInput) {
        sceneChanged = false;
        cameraInc.set(0, 0, 0);

        float speedChanger = 1;
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL) || window.isKeyPressed(GLFW_KEY_RIGHT_CONTROL))
        {
            speedChanger = 20;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT) || window.isKeyPressed(GLFW_KEY_RIGHT_SHIFT))
        {
            speedChanger = 0.1f;
        }
        if (window.isKeyPressed(GLFW_KEY_W)) {
            sceneChanged = true;
            cameraInc.z = -this.cameraMoveSpeed * speedChanger;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            sceneChanged = true;
            cameraInc.z = this.cameraMoveSpeed * speedChanger;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            sceneChanged = true;
            cameraInc.x = -this.cameraMoveSpeed * speedChanger;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            sceneChanged = true;
            cameraInc.x = this.cameraMoveSpeed * speedChanger;
        }
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            sceneChanged = true;
            cameraInc.y = -this.cameraMoveSpeed * speedChanger;
        } else if (window.isKeyPressed(GLFW_KEY_E)) {
            sceneChanged = true;
            cameraInc.y = this.cameraMoveSpeed * speedChanger;
        }
        if (window.isKeyPressed(GLFW_KEY_X))
        {
            selectObject(sunObject);
        }
        if (window.isKeyPressed(GLFW_KEY_C))
        {
            selectObject(shipObject);
        }
        if (window.isKeyPressed(GLFW_KEY_SPACE))
        {
            boolean selected = false;
            List<DefaultObject> allthings = SolarSystemMap.viewing.getObjects();
            for(DefaultObject defaultObject: allthings) {
                GuiManger hud = defaultObject.getHudOverlay();
                if (defaultObject.getHudOverlay() != null) {
                    if (defaultObject.isMouseOnMe(mouseInput)) {
                        selectObject(defaultObject);
                        selected = true;
                    }
                }
            }
            if (selected == false)
            {
                GameItem gameItem = cursor.getSelected();
                if (gameItem != null) {
                    DefaultObject defaultObject = getDefaultObject(gameItem);
                    selectObject(defaultObject);
                }
            }
          // }
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            sceneChanged = true;
            angleInc -= 0.5f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            sceneChanged = true;
            angleInc += 0.5f;
        } else {
            //angleInc = 0;
        }

        pointLightPos.x = camera.getPosition().x;
        pointLightPos.y = camera.getPosition().y;
        pointLightPos.z = camera.getPosition().z;

        if(window.isKeyPressed(GLFW_KEY_ESCAPE))
        {
            System.exit(0);
        }
    }

    public void updateWindow(float interval, MouseInput mouseInput, Window window)
    {
        List<DefaultObject> allthings = SolarSystemMap.viewing.getObjects();
        boolean overObject = false;
        for(DefaultObject defaultObject: allthings)
        {
            GuiManger hud = defaultObject.getHudOverlay();
            if (defaultObject.getHudOverlay() != null)
            {
                if (defaultObject.isMouseOnMe(mouseInput))
                {
                    guiManger.setPosition(new Vector2f(defaultObject.getHudOverlay().getPosition().x + hud.getSize().x, defaultObject.getHudOverlay().getPosition().y));

                    guiManger.setText("Planet:" + defaultObject.getName() + " Distance: " + selectedObject.getPosition().distance(defaultObject.getPosition()));
                    overObject = true;
                }

                Vector3f vector3f = new Vector3f(defaultObject.getGameItem().getPosition());
                vector3f = Utils.getScreenCoords(GameWindow.instance.getCamera(), window, vector3f);
                if (vector3f != null)
                {
                        defaultObject.setHudPosition(new Vector2f(vector3f.x, vector3f.y));
                        defaultObject.setHudVisible(true);
                }
                else
                {
                    defaultObject.setHudVisible(false);
                }

            }
        }
        if (overObject)
        {
            guiManger.setVisible(true);
        }
        else
        {
            guiManger.setVisible(false);
            //mouseOver = null;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window) {
        cursor.update(mouseInput);
        updateWindow(interval,mouseInput,window);

        if (mouseInput.isRightButtonPressed()) {
            // Update camera based on mouse            
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
            sceneChanged = true;
        }

        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        lightAngle += angleInc;
        if (lightAngle < 0) {
            lightAngle = 0;
        } else if (lightAngle > 180) {
            lightAngle = 180;
        }
        float zValue = (float) Math.cos(Math.toRadians(lightAngle));
        float yValue = (float) Math.sin(Math.toRadians(lightAngle));
        Vector3f lightDirection = this.scene.getSceneLight().getDirectionalLight().getDirection();
        lightDirection.x = 0;
        lightDirection.y = yValue;
        lightDirection.z = zValue;


        lightDirection.normalize();
        // Update view matrix
        camera.updateViewMatrix();
    }

    @Override
    public void render(Window window) {
        if (firstTime) {
            sceneChanged = true;
            firstTime = false;
        }

        renderer.render(window, camera, scene, sceneChanged);

        if (isMeshChanged()) refreshView();

        if (EternalConflict.quit)
        {
            org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose(window.getWindowHandle(), true);
        }
    }

    @Override
    public void cleanup() {
        try {
            ConfigFile data = new ConfigFile();
            data.set("command", "quit");
            SocketHandler.instance.sendRawData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EternalConflict.quit = true;
        SocketHandler.instance.close();

        GuiRenderer.instance.cleanUp();

        FontRenderer.instance.cleanUp();

        renderer.cleanup();

        scene.cleanup();

        Loader.cleanUp();



        Launcher.instance.getMainFrame().setVisible(true);
        Launcher.instance.getMainFrame().dispatchEvent(new WindowEvent(Launcher.instance.getMainFrame(), WindowEvent.WINDOW_CLOSING));
        Launcher.instance.close();

        System.out.println("Press Any-Key To Exit....");
    }
}
