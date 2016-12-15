package a7amdon.enis.tn.testiot.model;

/**
 * Created by 7amdon on 12/12/2016.
 */

public class Position {
    private float x;
    private float y;
    private float z;
    private String date;

    public Position(float x, float y, float z, String date) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.date = date;
    }
    public Position() {
     }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
