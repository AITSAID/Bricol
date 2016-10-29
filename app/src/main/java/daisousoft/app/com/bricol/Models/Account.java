package daisousoft.app.com.bricol.Models;

/**
 * Created by HABABONGA on 28/10/2016.
 */
public class Account {

    private String _id;
    private String _name;
    private String _phonenumber;
    private Double _lat;
    private Double _long;
    private int _statut;


    public Account() {
    }

    public Account(String _id, String _name, String _phonenumber, Double _lat, Double _long, int _statut) {
        this._id = _id;
        this._name = _name;
        this._phonenumber = _phonenumber;
        this._lat = _lat;
        this._long = _long;
        this._statut = _statut;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phonenumber() {
        return _phonenumber;
    }

    public void set_phonenumber(String _phonenumber) {
        this._phonenumber = _phonenumber;
    }

    public Double get_lat() {
        return _lat;
    }

    public void set_lat(Double _lat) {
        this._lat = _lat;
    }

    public Double get_long() {
        return _long;
    }

    public void set_long(Double _long) {
        this._long = _long;
    }

    public int get_statut() {
        return _statut;
    }

    public void set_statut(int _statut) {
        this._statut = _statut;
    }
}
