package com.rhyme.modiriathesab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionsFragment extends Fragment {
    private OnItemSelectedListener listener;
    Context mContext;
    myDbAdapter db;

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
        View view = inflater.inflate(R.layout.fragment_transactions,
                container, false);

        db = new myDbAdapter(mContext);
        SharedPreferences sp = mContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        ArrayList<Transaction> transactions = db.getTransactions(sp.getString("mhUsername", "unknown"));

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        TransactionAdapter adapter = new TransactionAdapter(transactions);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        return view;
    }

    public TransactionsFragment(){
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
