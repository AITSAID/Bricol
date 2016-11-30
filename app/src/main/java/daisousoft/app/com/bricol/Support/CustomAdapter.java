package daisousoft.app.com.bricol.Support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import daisousoft.app.com.bricol.R;

/**
 * Created by HABABONGA on 06/11/2016.
 */
public class CustomAdapter extends ArrayAdapter<Integer> {
        public CustomAdapter(Context context, Integer[] works){
        super(context, R.layout.simple_list_select,works);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View myView = myInflater.inflate(R.layout.simple_list_select , parent , false);
        Integer singleJob = getItem(position);
        TextView jobName = (TextView) myView.findViewById(R.id.jobName);
        ImageView jobImg =(ImageView) myView.findViewById(R.id.jobImg);


        if(111==singleJob){
            jobName.setText(getContext().getResources().getString(R.string.Job1));
            jobImg.setImageResource(R.drawable.job1);
        }
        if(222==singleJob){
            jobName.setText(getContext().getResources().getString(R.string.Job2));
            jobImg.setImageResource(R.drawable.job2);
        }
        if(333==singleJob){
            jobName.setText(getContext().getResources().getString(R.string.Job3));
            jobImg.setImageResource(R.drawable.job3);
        }
        if(444==singleJob){
            jobName.setText(getContext().getResources().getString(R.string.Job4));
            jobImg.setImageResource(R.drawable.job4);
        }
        if(555==singleJob){
            jobName.setText(getContext().getResources().getString(R.string.Job5));
            jobImg.setImageResource(R.drawable.job5);
        }
        if(666==singleJob){
            jobName.setText(getContext().getResources().getString(R.string.Job6));
            jobImg.setImageResource(R.drawable.job6);
        }
        if(777==singleJob){
            jobName.setText(getContext().getResources().getString(R.string.Job7));
            jobImg.setImageResource(R.drawable.job7);
        }


        return myView;
    }
}
