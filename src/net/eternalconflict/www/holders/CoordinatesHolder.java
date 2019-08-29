package net.eternalconflict.www.holders;

public class CoordinatesHolder {
    private double x;
    private double z;
    public CoordinatesHolder(double x, double z)
    {
        this.x = x;
        this.z = z;
    }
    public CoordinatesHolder clone()
    {
        CoordinatesHolder coordinatesHolder = new CoordinatesHolder(this.x, this.z);
        return coordinatesHolder;
    }
    public void setX(double x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }
    public void setX(int x) {
        this.x = x;
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

    public double distance(CoordinatesHolder coordinatesHolder)
    {
        double d = (this.x - coordinatesHolder.x)*(this.x - coordinatesHolder.x) + (this.z - coordinatesHolder.z)*(this.z - coordinatesHolder.z);
        d = Math.sqrt(d);
        return d;
    }
}
