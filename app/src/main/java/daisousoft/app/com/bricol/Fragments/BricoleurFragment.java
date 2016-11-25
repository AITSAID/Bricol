package daisousoft.app.com.bricol.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import daisousoft.app.com.bricol.DAO.myDBHandler;
import daisousoft.app.com.bricol.JobsActivity;
import daisousoft.app.com.bricol.Models.Account;
import daisousoft.app.com.bricol.Models.Jobs;
import daisousoft.app.com.bricol.R;

public class BricoleurFragment extends Fragment {
    myDBHandler mydb ;
    TextView name;
    Button c1,c2,c3;
    Account bricoAccount = null;
    List<Jobs> listjobs = null;
    Bundle bundle = new Bundle();
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_bricoleur, container, false);
        bundle = this.getArguments();
        mydb = new myDBHandler(getContext());
        bricoAccount = mydb.getAccountByID(bundle.getString("ID"));
        listjobs=mydb.getAllJobs(bundle.getString("ID"));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Working on it",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext() , JobsActivity.class);
                startActivity(i);
            }
        };

        final View.OnClickListener onCallListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL ,Uri.parse("tel:"+bricoAccount.get_phonenumber()));
                try{
                    startActivity(phoneIntent);
                }

                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getContext(),"You can't Call Mr :" +bricoAccount.get_name(),Toast.LENGTH_SHORT).show();
                }
            }
        };
        name = (TextView) view.findViewById(R.id.name);
        name.setText(" Hi i'm "+bricoAccount.get_name());
        c1 = (Button) view.findViewById(R.id.display1);
        c1.setTag(c1.getId());
        c2 = (Button) view.findViewById(R.id.display2);
        c2.setTag(c2.getId());
        c3 = (Button) view.findViewById(R.id.display3);
        c3.setTag(c3.getId());
        view.findViewById(R.id.display1).setOnClickListener(onClickListener);
        view.findViewById(R.id.display2).setOnClickListener(onClickListener);
        view.findViewById(R.id.callbrico).setOnClickListener(onCallListener);

        for(Jobs jb :listjobs) {
            EnableJobs(jb.getIdjob());
        }
        //GetAllJobs(bricoAccount.get_id());
    }


    public void EnableJobs(int idjob) {
        //test DATABASE
        //mydb.addAccount(new Account("hdjahdadz5454", "MAROUANE", "0672145149", 33.121, 7.1245, 1));
        //phonebrico.setText(mydb.getAllAccounts().size()+"");
        int choose = checkEmptyChoice();

        if(111==idjob){
            if(choose == 1 ){
                c1.setBackground(getResources().getDrawable(R.drawable.job1));
                c1.setTag("full");
                c1.setVisibility(View.VISIBLE);
            }else{
                if(choose == 2){
                    c2.setBackground(getResources().getDrawable(R.drawable.job1));
                    c2.setTag("full");
                    c2.setVisibility(View.VISIBLE);
                }else{
                    if(choose == 3){
                        c3.setBackground(getResources().getDrawable(R.drawable.job1));
                        c3.setTag("full");
                        c3.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        if(222==idjob){
            if(choose == 1 ){
                c1.setBackground(getResources().getDrawable(R.drawable.job2));
                c1.setTag("full");
                c1.setVisibility(View.VISIBLE);
            }else{
                if(choose == 2){
                    c2.setBackground(getResources().getDrawable(R.drawable.job2));
                    c2.setTag("full");
                    c2.setVisibility(View.VISIBLE);
                }else{
                    if(choose == 3){
                        c3.setBackground(getResources().getDrawable(R.drawable.job2));
                        c3.setTag("full");
                        c3.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        if(333==idjob){
            if(choose == 1 ){
                c1.setBackground(getResources().getDrawable(R.drawable.job3));
                c1.setTag("full");
                c1.setVisibility(View.VISIBLE);
            }else{
                if(choose == 2){
                    c2.setBackground(getResources().getDrawable(R.drawable.job3));
                    c2.setTag("full");
                    c2.setVisibility(View.VISIBLE);
                }else{
                    if(choose == 3){
                        c3.setBackground(getResources().getDrawable(R.drawable.job3));
                        c3.setTag("full");
                        c3.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        if(444==idjob){
            if(choose == 1 ){
                c1.setBackground(getResources().getDrawable(R.drawable.job4));
                c1.setTag("full");
                c1.setVisibility(View.VISIBLE);
            }else{
                if(choose == 2){
                    c2.setBackground(getResources().getDrawable(R.drawable.job4));
                    c2.setTag("full");
                    c2.setVisibility(View.VISIBLE);
                }else{
                    if(choose == 3){
                        c3.setBackground(getResources().getDrawable(R.drawable.job4));
                        c3.setTag("full");
                        c3.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        if(555==idjob){
            if(choose == 1 ){
                c1.setBackground(getResources().getDrawable(R.drawable.job5));
                c1.setTag("full");
                c1.setVisibility(View.VISIBLE);
            }else{
                if(choose == 2){
                    c2.setBackground(getResources().getDrawable(R.drawable.job5));
                    c2.setTag("full");
                    c2.setVisibility(View.VISIBLE);
                }else{
                    if(choose == 3){
                        c3.setBackground(getResources().getDrawable(R.drawable.job5));
                        c3.setTag("full");
                        c3.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        if(666==idjob){
            if(choose == 1 ){
                c1.setBackground(getResources().getDrawable(R.drawable.job6));
                c1.setTag("full");
                c1.setVisibility(View.VISIBLE);
            }else{
                if(choose == 2){
                    c2.setBackground(getResources().getDrawable(R.drawable.job6));
                    c2.setTag("full");
                    c2.setVisibility(View.VISIBLE);
                }else{
                    if(choose == 3){
                        c3.setBackground(getResources().getDrawable(R.drawable.job6));
                        c3.setTag("full");
                        c3.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        if(777==idjob){
            if(choose == 1 ){
                c1.setBackground(getResources().getDrawable(R.drawable.job7));
                c1.setTag("full");
                c1.setVisibility(View.VISIBLE);
            }else{
                if(choose == 2){
                    c2.setBackground(getResources().getDrawable(R.drawable.job7));
                    c2.setTag("full");
                    c2.setVisibility(View.VISIBLE);
                }else{
                    if(choose == 3){
                        c3.setBackground(getResources().getDrawable(R.drawable.job7));
                        c3.setTag("full");
                        c3.setVisibility(View.VISIBLE);
                    }
                }
            }
        }


    }

    public int checkEmptyChoice() {
        if (!c1.getTag().equals("full")) {
            return 1;
        } else {
            if (!c2.getTag().equals("full")) {
                return 2;
            } else {
                if (!c3.getTag().equals("full")) {
                    return 3;
                }
            }
        }

        return 0;
    }
}

