package com.rhyme.modiriathesab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddIncFragment extends Fragment {
    private OnItemSelectedListener listener;
    Context mContext;
    myDbAdapter db;
    TextView totalTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_cost,
                container, false);

        db = new myDbAdapter(mContext);
        SharedPreferences sp = mContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        EditText titleET = view.findViewById(R.id.titleET);
        EditText amountET = view.findViewById(R.id.amountET);
        Button sbtn = view.findViewById(R.id.submitBtn);

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleET.getText().toString()!=null && amountET.getText().toString()!=null)
                {
                    db.insertTransaction(sp.getString(getString(R.string.preference_username), "unknown"), Integer.parseInt(amountET.getText().toString()), titleET.getText().toString());
                    db.addTotal(sp.getString(getString(R.string.preference_username), "unknown"), Integer.parseInt(amountET.getText().toString()));
                    Toast.makeText(mContext,"تراکنش اضافه شد",Toast.LENGTH_SHORT).show();
                    totalTv.setText(String.valueOf(db.getTotal(sp.getString("mhUsername", "unknown"))));
                    amountET.setText("");
                    titleET.setText("");
                }
            }
        });

        return view;
    }

    public AddIncFragment(TextView totalTv){
        this.totalTv = totalTv;
    }

    public interface OnItemSelectedListener {
        public void onRssItemSelected(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
