package com.tntadvance.firebasenews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tntadvance.firebasenews.R;
import com.tntadvance.firebasenews.adapter.NewsAdapter;
import com.tntadvance.firebasenews.connection.FirebaseConnection;
import com.tntadvance.firebasenews.dao.NewsDao;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by thana on 3/29/2017 AD.
 */

public class MainFragment extends Fragment {

    private ListView listView;
    private List<NewsDao> newsList;
    private NewsAdapter adapter;
    private FirebaseConnection firebase;
    private FloatingActionButton fab;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        showData();
        return rootView;
    }

    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        listView = (ListView) rootView.findViewById(R.id.listView);
        newsList = new ArrayList<>();
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDao dao = new NewsDao();

                openAddFragment(dao);

//                FragmentListener listener = (FragmentListener) getActivity();
//                listener.onItemListClicked(dao);
            }
        });
    }

    private void showData(){
        Query query = firebase.getDatabase("news");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NewsDao dao = postSnapshot.getValue(NewsDao.class);
                    newsList.add(dao);
                }
                adapter = new NewsAdapter(newsList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        NewsDao dao = newsList.get(i);

                        openAddFragment(dao);

//                        FragmentListener listener = (FragmentListener) getActivity();
//                        listener.onItemListClicked(dao);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //implement interface
    public interface FragmentListener {
        void onItemListClicked(NewsDao dao);
    }

    private void openAddFragment(NewsDao dao){
//        NewsDao dao = new NewsDao();

        FragmentListener listener = (FragmentListener) getActivity();
        listener.onItemListClicked(dao);
    }


}
