package daisousoft.app.com.bricol.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import daisousoft.app.com.bricol.Models.Account;
import daisousoft.app.com.bricol.Models.Jobs;

/**
 * Created by HABABONGA on 28/10/2016.
 */
public class myDBHandler extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "myDBHandler";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bricollappdb";

    // Table Names
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_JOB = "job";

    // Account Table - column names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "_name";
    private static final String COLUMN_PHONENUMBER = "_phonenumber";
    private static final String COLUMN_LAT = "_lat";
    private static final String COLUMN_LONG = "_long";
    private static final String COLUMN_STATUT = "_statut";

    // Job Table - column names
    private static final String COLUMN_ACCOUNT = "_idAccount";
    private static final String COLUMN_JOBID = "idjob";

    // ACCOUNT table create statement
    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE "
            + TABLE_ACCOUNT + "(" + COLUMN_ID + " TEXT,"
            + COLUMN_NAME + " TEXT," + COLUMN_LAT
            + " REAL," + COLUMN_LONG + " REAL," + COLUMN_PHONENUMBER
            + " TEXT," + COLUMN_STATUT + " NUMERIC" + ")";

    // JOB table create statement
    private static final String CREATE_TABLE_JOB = "CREATE TABLE "+TABLE_JOB + "(" +
            COLUMN_ACCOUNT +" TEXT,"+COLUMN_JOBID+" NUMERIC"+")";

    public myDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_JOB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB);
        // create new tables
        onCreate(db);

    }

    /*
* Creating ACCOUNT
*/
    public long addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, account.get_id());
        values.put(COLUMN_NAME, account.get_name());
        values.put(COLUMN_PHONENUMBER, account.get_phonenumber());
        values.put(COLUMN_LAT, account.get_lat());
        values.put(COLUMN_LONG, account.get_long());
        values.put(COLUMN_STATUT, account.get_statut());




        // insert row
        long account_id = db.insert(TABLE_ACCOUNT, null, values);

        return account_id;
    }


    /*
 * Updating  ACCOUNT
 */
    public int updateAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, account.get_id());
        values.put(COLUMN_NAME, account.get_name());
        values.put(COLUMN_PHONENUMBER, account.get_phonenumber());
        values.put(COLUMN_LAT, account.get_lat());
        values.put(COLUMN_LONG, account.get_long());
        values.put(COLUMN_STATUT, account.get_statut());

        // updating row
        return db.update(TABLE_ACCOUNT, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(account.get_id()) });
    }


    /*
* getting all ACCOUNTS
* */
    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<Account>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Account Ac = new Account();
                Ac.set_id(c.getString(c.getColumnIndex(COLUMN_ID)));
                Ac.set_name(c.getString(c.getColumnIndex(COLUMN_NAME)));
                Ac.set_lat(c.getDouble(c.getColumnIndex(COLUMN_LAT)));
                Ac.set_long(c.getDouble(c.getColumnIndex(COLUMN_LONG)));
                Ac.set_phonenumber(c.getString(c.getColumnIndex(COLUMN_PHONENUMBER)));
                Ac.set_statut(c.getInt(c.getColumnIndex(COLUMN_STATUT)));

                // adding to accounts list
                accounts.add(Ac);
            } while (c.moveToNext());
        }

        return accounts;
    }

    /*
* getting ACCOUNT BY ID
* */
    public Account getAccountByID(String deviceID) {
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT + " WHERE "+ COLUMN_ID +"='"+deviceID+"'";
        Account Ac = new Account();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
                Ac.set_id(c.getString(c.getColumnIndex(COLUMN_ID)));
                Ac.set_name(c.getString(c.getColumnIndex(COLUMN_NAME)));
                Ac.set_lat(c.getDouble(c.getColumnIndex(COLUMN_LAT)));
                Ac.set_long(c.getDouble(c.getColumnIndex(COLUMN_LONG)));
                Ac.set_phonenumber(c.getString(c.getColumnIndex(COLUMN_PHONENUMBER)));
                Ac.set_statut(c.getInt(c.getColumnIndex(COLUMN_STATUT)));
        }

        return Ac;
    }

    /*
* Delete all ACCOUNTS
* */
    public void deleteAllAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_ACCOUNT);
        db.close();
    }




    /*
* Creating ACCOUNT
*/
    public long addJob(Jobs job) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT, job.get_idAccount());
        values.put(COLUMN_JOBID, job.getIdjob());



        // insert row
        long jooob_id = db.insert(TABLE_JOB, null, values);

        return jooob_id;
    }


    /*
* getting all Jobs for an account
* */
    public ArrayList<Jobs> getAllJobs(String IDaccount) {
        ArrayList<Jobs> joobs = new ArrayList<Jobs>();
        String selectQuery = "SELECT  * FROM " + TABLE_JOB + " WHERE "+ COLUMN_ACCOUNT +"='"+IDaccount+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Jobs jb = new Jobs();
                jb.set_idAccount(c.getString(c.getColumnIndex(COLUMN_ACCOUNT)));
                jb.setIdjob(c.getInt(c.getColumnIndex(COLUMN_JOBID)));

                // adding to jobs list
                joobs.add(jb);
            } while (c.moveToNext());
        }

        return joobs;
    }

    /*
* getting all Jobs for an account
* */
    public ArrayList<String> getAccountbyJob(int idJob) {
        ArrayList<String> joobs = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_JOB + " WHERE "+ COLUMN_JOBID +"='"+idJob+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String result;
                result = c.getString(c.getColumnIndex(COLUMN_ACCOUNT));
                // adding to Accounts ID list
                joobs.add(result);
            } while (c.moveToNext());
        }

        return joobs;
    }

    /*
    * Delete all JOBS
    * */
    public void deleteAllJobs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_JOB);
        db.close();
    }

    /*
    * Delete JOB By Id
    * */
    public void deleteJobById(String idDevice , int idJob) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_JOB+" where "+COLUMN_ACCOUNT+ "='" + idDevice + "' AND "+COLUMN_JOBID+" ='"+idJob+"'");
        db.close();
    }


}
