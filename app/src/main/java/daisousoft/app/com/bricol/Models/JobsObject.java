package daisousoft.app.com.bricol.Models;

/**
 * Created by HABABONGA on 05/11/2016.
 */
public class JobsObject {
    private int _id;
    private String _idAccount;
    private int idjob;

    public JobsObject() {
    }

    public JobsObject(int _id, String _idAccount, int idjob) {
        this._id = _id;
        this._idAccount = _idAccount;
        this.idjob = idjob;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_idAccount() {
        return _idAccount;
    }

    public void set_idAccount(String _idAccount) {
        this._idAccount = _idAccount;
    }

    public int getIdjob() {
        return idjob;
    }

    public void setIdjob(int idjob) {
        this.idjob = idjob;
    }
}
