package org.lwjglb.engine.graph.other;

public class RawMesh {

    private int vaoID;
    private int vertexCount;

    public RawMesh(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }


}
