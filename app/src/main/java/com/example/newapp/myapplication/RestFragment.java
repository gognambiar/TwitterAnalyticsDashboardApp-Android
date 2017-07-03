package com.example.newapp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View myNewView;
    private static final String TAG = "MyActivity";
    public static final String EXTRA_MESSAGE = "com.example.newapp.MESSAGE";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestFragment newInstance(String param1, String param2) {
        RestFragment fragment = new RestFragment();
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
    }

    @Override
    public void onStart() {
        super.onStart();
        Button button3 = (Button) getView().findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callrestnode();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myNewView = inflater.inflate(R.layout.fragment_rest, container, false);
        return myNewView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRestFragmentInteraction(uri);
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

    /*public void callrest()
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://api.zappos.com/Search?key=b743e26728e16b81da139182bb2094357c31d331";
        Log.v(TAG,"***************************Hello********************");
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            TextView textView3 = (TextView) myNewView.findViewById(R.id.textView3);
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject finalResult = results.getJSONObject(0);
                    String nres = finalResult.getString("brandName");
                    Log.v(TAG, nres);
                    textView3.setText(nres);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }*/

    //String url = "http://10.0.2.2:3000/twt";

    public void callrestnode() {
        EditText editText = (EditText) getView().findViewById(R.id.editText3);
        String message = editText.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://10.0.2.2:3000/twt";
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("seword",message);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
            //TextView textView3 = (TextView) myNewView.findViewById(R.id.textView3);
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG,"***************************Hello********************");
                Log.i("onResponse", response.toString());
                    //String nres = response.getString("text");
                    //textView3.setText(nres);
                    Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, response.toString());
                    startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG,"***************************Hola********************");
                Log.e("onErrorResponse", error.toString());
            }
        }) {
        };
        queue.add(jsonObjectRequest);
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
        void onRestFragmentInteraction(Uri uri);
    }
}
