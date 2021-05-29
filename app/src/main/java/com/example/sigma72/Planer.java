package com.example.sigma72;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Planer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Planer extends Fragment {





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    public List<String> toDoList;
//    public ArrayAdapter arrayAdapter;
//    public ListView listView;
//    public EditText editText;
//    private EditText editTextD;
//
//    public void addItemToList (View view){
//
//        toDoList.add(editText.getText().toString());
//        listView.setAdapter(arrayAdapter);
//        arrayAdapter.notifyDataSetChanged();
//        editText.setText("1");
//        editText.setText("");
//        editTextD.setText("2");
//    }


    public Planer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Planer newInstance(String param1, String param2) {
        Planer fragment = new Planer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  listView.setAdapter(arrayAdapter);
        return inflater.inflate(R.layout.fragment_blank2,  null);
    }

}