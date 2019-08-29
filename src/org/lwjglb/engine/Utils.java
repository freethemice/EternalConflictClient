package org.lwjglb.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjglb.engine.graph.Camera;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class Utils {

    public static String loadResource(String fileName) throws Exception {
        String result;
        //try (InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(fileName);

        try (InputStream in = new FileInputStream(fileName);
             Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public static List<String> readAllLines(String fileName) throws Exception {
        List<String> list = new ArrayList<>();
        //try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(fileName)))) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }
    public static Vector3f getScreenCoords(Camera camera, Window window, Vector3f worldPosition) {

        Vector3f clowne = new Vector3f();
        clowne.x = worldPosition.x - camera.getPosition().x;
        clowne.y = worldPosition.y - camera.getPosition().y;
        clowne.z = worldPosition.z - camera.getPosition().z;
        float big = camera.getPosition().distance(worldPosition);
        if (big > 100) {
            clowne.x = clowne.x / big;
            clowne.y = clowne.y / big;
            clowne.z = clowne.z / big;
            clowne.x = clowne.x * 100 + camera.getPosition().x;
            clowne.y = clowne.y * 100 + camera.getPosition().y;
            clowne.z = clowne.z * 100 + camera.getPosition().z;
        }
        else
        {
            clowne.x = worldPosition.x;
            clowne.y = worldPosition.y;
            clowne.z = worldPosition.z;
        }
        Matrix4f viewProjMatrix = new Matrix4f(window.getProjectionMatrix());
        viewProjMatrix = viewProjMatrix.mul(camera.getViewMatrix());
        int[] viewport = window.getViewPort();
        Vector3f winCoords = viewProjMatrix.project(clowne, viewport, new Vector3f());
        winCoords.y = (window.getHeight() - winCoords.y);
        if (winCoords.z < 1)
        {
            if (winCoords.x > 0 && winCoords.x < window.getWidth()) {
                if (winCoords.y > 0 && winCoords.y < window.getHeight()) {
                    return winCoords;
                }
            }
        }
        return null;
    }

    public static int[] listIntToArray(List<Integer> list) {
        int[] result = list.stream().mapToInt((Integer v) -> v).toArray();
        return result;
    }

    public static float[] listToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }

    public static boolean existsResourceFile(String fileName) {
        boolean result;
        try (InputStream is = Utils.class.getResourceAsStream(fileName)) {
            result = is != null;
        } catch (Exception excp) {
            result = false;
        }
        return result;
    }

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) ;
            }
        } else {
            try (
                    InputStream source = new FileInputStream(resource);//Utils.class.getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

}
