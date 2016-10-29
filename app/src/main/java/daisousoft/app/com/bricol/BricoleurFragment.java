package daisousoft.app.com.bricol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import daisousoft.app.com.bricol.DAO.myDBHandler;
import daisousoft.app.com.bricol.Models.Account;

public class BricoleurFragment extends Fragment {
    myDBHandler mydb ;
    TextView name;
    Account bricoAccount = null;
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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Working on it",Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getContext(),"You can Call Mr :" +bricoAccount.get_name(),Toast.LENGTH_SHORT).show();
                }
            }
        };
        name = (TextView) view.findViewById(R.id.name);
        name.setText(" Hi i'm "+bricoAccount.get_name());
        view.findViewById(R.id.j1).setOnClickListener(onClickListener);
        view.findViewById(R.id.j2).setOnClickListener(onClickListener);
        view.findViewById(R.id.callbrico).setOnClickListener(onCallListener);
    }
}

