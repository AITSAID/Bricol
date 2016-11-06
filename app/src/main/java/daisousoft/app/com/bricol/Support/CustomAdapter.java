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


        if(2131624106==singleJob){
            jobName.setText("job1");
            jobImg.setImageResource(R.drawable.job1);
        }
        if(2131624107==singleJob){
            jobName.setText("job2");
            jobImg.setImageResource(R.drawable.job2);
        }
        if(2131624108==singleJob){
            jobName.setText("job3");
            jobImg.setImageResource(R.drawable.job3);
        }
        if(2131624109==singleJob){
            jobName.setText("job4");
            jobImg.setImageResource(R.drawable.job4);
        }
        if(2131624110==singleJob){
            jobName.setText("job5");
            jobImg.setImageResource(R.drawable.job5);
        }
        if(2131624111==singleJob){
            jobName.setText("job6");
            jobImg.setImageResource(R.drawable.job6);
        }
        if(2131624112==singleJob){
            jobName.setText("job7");
            jobImg.setImageResource(R.drawable.job7);
        }


        return myView;
    }
}