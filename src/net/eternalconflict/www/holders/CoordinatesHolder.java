package net.eternalconflict.www.holders;

import org.joml.Vector3f;

public class CoordinatesHolder {
    private double x;
    private double y;
    private double z;
    public CoordinatesHolder(double x, double z)
    {
        this.x = x;
        this.y = 0;
        this.z = z;
    }
    public CoordinatesHolder(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public CoordinatesHolder(Vector3f vector3f)
    {
        this.x = vector3f.x;
        this.y = vector3f.y;
        this.z = vector3f.z;
    }
    public CoordinatesHolder clone()
    {
        CoordinatesHolder coordinatesHolder = new CoordinatesHolder(this.x, this.y, this.z);
        return coordinatesHolder;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setZ(float z) {
        this.z = z;
    }
    public void setZ(int z) {
        this.z = z;
    }
    public double getX() {
        return x;
    }


    public double getZ() {
        return z;
    }

    public int getIntX() {
        return (int)x;
    }

    public int getIntZ() {
        return (int)z;
    }

    public int getIntY() {
        return (int)y;
    }
    public float getFloatX() {
        return (float)x;
    }
    public float getFloatY() {
        return (float)y;
    }
    public float getFloatZ() {
        return (float)z;
    }


    public double distance(CoordinatesHolder coordinatesHolder)
    {
        double d = (this.x - coordinatesHolder.x)*(this.x - coordinatesHolder.x) + (this.z - coordinatesHolder.z)*(this.z - coordinatesHolder.z);
        d = Math.sqrt(d);
        return d;
    }

    public Vector3f getVector()
    {
        return new Vector3f((float)this.x, (float)this.y, (float)this.z);
    }
}
