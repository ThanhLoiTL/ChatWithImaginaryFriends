package com.android.chatwithimaginaryfriends.fragment;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.WordAndReplyAdapter;
import java.util.List;

@SuppressLint("ValidFragment")
public class WordFragment extends ListFragment {
    public List<String> listWord;
    WordAndReplyAdapter wordAndReplyAdapter;

    public WordFragment(List<String> listWord) {
        this.listWord = listWord;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        wordAndReplyAdapter = new WordAndReplyAdapter(getActivity(), R.layout.row_trigger_word, listWord);
        setListAdapter(wordAndReplyAdapter);
        View view = inflater.inflate(R.layout.fragment_interaction, container, false);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }
}
