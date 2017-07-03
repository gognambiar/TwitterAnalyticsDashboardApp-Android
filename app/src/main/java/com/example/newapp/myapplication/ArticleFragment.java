package com.example.newapp.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import static com.example.newapp.myapplication.DatabaseHelper.TABLE_NAME;
import static com.example.newapp.myapplication.DatabaseHelper.COLUMN_KEY;
import static com.example.newapp.myapplication.DatabaseHelper.COLUMN_VALUE;
import static com.example.newapp.myapplication.DatabaseHelper.P_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View myView;
    private static DatabaseHelper dbt;
    private static SQLiteDatabase sdbt;
    private static Context gc;
    private static int ctr;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleFragment newInstance(String param1, String param2) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbt = new DatabaseHelper(getActivity().getApplicationContext());
        sdbt = dbt.getWritableDatabase();
        ctr = 0;
        dbt.deleteData(sdbt);
        addData("Olivier","Giroud");
        addData("Cristiano","Ronaldo");
        addData("Dani","Carvajal");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_article, container, false);
        return myView;
    }

    public static void showMessage(String message) {
        TextView textView4 = (TextView) myView.findViewById(R.id.textView4);
        if (message.equals("Change")) {
            textView4.setText("The Layout is changed");
            //MainActivity.fragCh();

        } else {
            String fst = queryData(message);
            if (fst != null && !fst.isEmpty())
                textView4.setText(fst);
            else
                textView4.setText("Player not found");
        }
    }

    public static void addData(String keyInp,String valInp)
    {
        ctr += 1;
        ContentValues values = new ContentValues();
        values.put(P_KEY, ctr);
        values.put(COLUMN_KEY, keyInp);
        values.put(COLUMN_VALUE, valInp);
        sdbt.insert(TABLE_NAME, null, values);
    }

    public static String queryData(String selection)
    {
        String[] colsFetch = {COLUMN_KEY, COLUMN_VALUE};
        String searchClause = COLUMN_KEY + " = ?";
        String[] searchQuery = {selection};
        Cursor cursor = sdbt.query(TABLE_NAME, colsFetch, searchClause, searchQuery, null, null, null);
        StringBuilder st = new StringBuilder();
        //cursor.moveToFirst();
        while(cursor.moveToNext())
        {
            st.append(cursor.getString(1));
        }
        cursor.close();
        return (st.toString());
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onArticleFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onArticleFragmentInteraction(Uri uri);
    }

}
