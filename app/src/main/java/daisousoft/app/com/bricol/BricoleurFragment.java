package daisousoft.app.com.bricol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BricoleurFragment extends Fragment {

    Bundle bundle = new Bundle();
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_bricoleur, container, false);
        bundle = this.getArguments();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SHOULD BE A " + bundle.getString("ID") , Toast.LENGTH_SHORT).show();
            }
        };

        view.findViewById(R.id.j1).setOnClickListener(onClickListener);
        view.findViewById(R.id.j2).setOnClickListener(onClickListener);
    }
}

