package ru.tacticm;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BookFragment extends Fragment {
    EditText edit;
    ImageView clear;
    RecyclerView rList;
    ArrayList<Card> clist = new ArrayList<>();
    static boolean showhint = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_book, container, false);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rList = fragmentView.findViewById(R.id.rview);
        rList.setLayoutManager(lm);
        edit = fragmentView.findViewById(R.id.edit);
        clear = fragmentView.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText("");
            }
        });
        edit.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable e) {
                String s = e.toString().trim().toLowerCase();
                clear.setVisibility("".equals(s)?View.GONE : View.VISIBLE);
                update(s);
            }
        });
        if (1==App.getInstance().getInt(c.option1, 1))
           edit.requestFocus();
        update("");
        if (false==showhint){
            showhint = true;
            Tools.okDialog(getContext(), null, R.string.app_hint);
        }
        return fragmentView;
    }
    //
    public void update(String s) {
        clist = CardManager.find(s);
        if (false == clist.isEmpty()) {
            CardAdapter cd = new CardAdapter(clist);
            rList.setAdapter(cd);
        }
    }
}
