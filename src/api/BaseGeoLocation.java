package api;

public class BaseGeoLocation implements api.GeoLocation {

    private double _x;
    private double _y;
    private double _z;

    public BaseGeoLocation(double x, double y, double z){
        this._x = x;
        this._y = y;
        this._z = z;
    }

    @Override
    public double x() {
        return this._x;
    }

    @Override
    public double y() {
        return this._y;
    }

    @Override
    public double z() {
        return this._z;
    }

    @Override
    public double distance(api.GeoLocation g) {
        return Math.sqrt((Math.pow(g.x()-this._x, 2) + Math.pow(g.y()-this._y, 2) + Math.pow(g.z()-this._z, 2)));
    }
}
