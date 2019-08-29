package org.lwjglb.engine.graph.other;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.Window;

public class Maths {
    public static Vector2f getDrawScreenSizes(Window window, Vector2f size)
    {
        float xLeft = size.x / window.getWidth();
        float yTop = size.y / window.getHeight();
        return new Vector2f(xLeft, yTop);
    }
    public static Vector2f getDrawScreenPositionsText(Window window, Vector2f position)
    {
        float xLeft = (position.x) / window.getWidth();
        float yTop = (position.y) / window.getHeight();
        return new Vector2f(xLeft, yTop);
    }

    public static Vector2f getDrawScreenPositions(Window window, Vector2f position, Vector2f size)
    {
        float xLeft = (position.x + size.x/2) / window.getWidth();
        xLeft = xLeft*2 - 1;

        float yTop = (position.y + size.y/2) / window.getHeight();
        yTop = yTop*2 - 1;
        yTop = -1*yTop;
        return new Vector2f(xLeft, yTop);
    }
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();

        matrix = setIdentity(matrix);
        matrix = translate(translation, matrix, matrix);
        matrix = scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);

        return matrix;
    }
    public static Matrix4f setIdentity(Matrix4f m) {
        m.m00(1.0f);
        m.m01(0.0f);
        m.m02(0.0f);
        m.m03(0.0f);
        m.m10(0.0f);
        m.m11(1.0f);
        m.m12(0.0f);
        m.m13(0.0f);
        m.m20(0.0f);
        m.m21(0.0f);
        m.m22(1.0f);
        m.m23(0.0f);
        m.m30(0.0f);
        m.m31(0.0f);
        m.m32(0.0f);
        m.m33(1.0f);

        return m;
    }
    public static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();
        dest.m00(src.m00() * vec.x);
        dest.m01(src.m01() * vec.x);
        dest.m02(src.m02() * vec.x);
        dest.m03(src.m03() * vec.x);
        dest.m10(src.m10() * vec.y);
        dest.m11(src.m11() * vec.y);
        dest.m12(src.m12() * vec.y);
        dest.m13(src.m13() * vec.y);
        dest.m20(src.m20() * vec.z);
        dest.m21(src.m21() * vec.z);
        dest.m22(src.m22() * vec.z);
        dest.m23(src.m23() * vec.z);
        return dest;
    }
    public static Matrix4f translate(Vector2f vec, Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();

        dest.m30(dest.m30() + src.m00() * vec.x + src.m10() * vec.y);
        dest.m31(dest.m31() + src.m01() * vec.x + src.m11() * vec.y);
        dest.m32(dest.m32() + src.m02() * vec.x + src.m12() * vec.y);
        dest.m33( dest.m33() + src.m03() * vec.x + src.m13() * vec.y);

        return dest;
    }

}
