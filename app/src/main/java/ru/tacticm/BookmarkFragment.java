package ru.tacticm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class BookmarkFragment extends Fragment {
    View fragmentView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        rebuildViews();
        return fragmentView;
    }
    public void onResume(){
        super.onResume();
        rebuildViews();
    }
    private void rebuildViews(){
        ArrayList<Card> clist = CardManager.getBookmarks();
        if (false == clist.isEmpty()) {
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            RecyclerView rList = fragmentView.findViewById(R.id.rview);
            rList.setLayoutManager(lm);
            CardAdapter cd = new CardAdapter(clist);
            rList.setAdapter(cd);
        }
        fragmentView.findViewById(R.id.scrollView).setVisibility(false == clist.isEmpty() ? View.VISIBLE : View.GONE);
        fragmentView.findViewById(R.id.nobookmarks).setVisibility(false == clist.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
