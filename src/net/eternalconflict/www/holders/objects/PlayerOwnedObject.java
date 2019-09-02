package net.eternalconflict.www.holders.objects;

import net.eternalconflict.www.holders.CoordinatesHolder;

public class PlayerOwnedObject extends DefaultObject {
    private String OwnerID;
    private CoordinatesHolder destination;
    private double velocity = 0;
    public PlayerOwnedObject(String OwnerID, String id, String name) {
        super(id, name);
        this.OwnerID = OwnerID;
        this.destination = new CoordinatesHolder(0,0);
    }

    public PlayerOwnedObject(String OwnerID, String id, String name, double x, double z) {
        super(id, name, x, z);
        this.OwnerID = OwnerID;
        this.destination = new CoordinatesHolder(0,0);
    }

    public PlayerOwnedObject(String OwnerID, String name) {
        super(name);
        this.OwnerID = OwnerID;
        this.destination = new CoordinatesHolder(0,0);
    }

    public PlayerOwnedObject(String OwnerID, String name, double x, double z) {
        super(name, x, z);
        this.OwnerID = OwnerID;
        this.destination = new CoordinatesHolder(0,0);
    }

    public PlayerOwnedObject(String OwnerID, String id, String name, CoordinatesHolder c) {
        super(name, id, c);
        this.OwnerID = OwnerID;
        this.destination = new CoordinatesHolder(0,0);
    }


    public void setDestination(CoordinatesHolder destination) {
        this.destination = destination.clone();
    }

    public void setDestination(double x, double z) {
        this.destination = new CoordinatesHolder(x, z);
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public CoordinatesHolder getDestination() {
        return destination;
    }

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String ownerID) {
        OwnerID = ownerID;
    }
}
