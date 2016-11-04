package daisousoft.app.com.bricol.Models;

/**
 * Created by HABABONGA on 28/10/2016.
 */
public class Jobs {

    private String _idAccount;
    private int idjob;

    public Jobs() {
    }

    public Jobs(String _idAccount, int idjob) {
        this._idAccount = _idAccount;
        this.idjob = idjob;
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
