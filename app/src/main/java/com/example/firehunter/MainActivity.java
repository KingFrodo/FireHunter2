package com.example.firehunter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    View view;
    MyAdapter myAdapter;
    ListView listView;
    List<Einsatz> einsätze = new ArrayList<>();
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    private Context context;
    private String mJSONURLString = "http://intranet.ooelfv.at/webext2/rss/json_6stunden.txt";
    List<Einsatz> tee = new ArrayList<>();
    List<Einsatz> brand = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = View.inflate(this, R.layout.main_layout, null);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            setContentView(view);
            context = getApplicationContext();

            listView = (ListView) findViewById(R.id.listView);
            myAdapter = new MyAdapter(this, R.layout.listview_layout, einsätze);

            jsonRead(3);

            listView.setAdapter(myAdapter);
        }
    }

    public void registerLoginButton(View view) {
        login();
    }

    private void signUp() {
        String emailStr = getEmail();
        String passwordStr = getPassword();

        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SIGN UP: ", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            email.setText("");
                            password.setText("");
                            setContentView(view);
                        } else {
                            Log.w("SIGN UP: ", "createUserWithEmail:failure", task.getException());
                            email.setText("");
                            password.setText("");
                        }
                    }
                });
    }

    private void login() {
        String emailStr = getEmail();
        String passwordStr = getPassword();

        mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SIGN UP: ", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            email.setText("");
                            password.setText("");
                            setContentView(view);
                        } else {
                            Log.w("SIGN UP: ", "signInWithEmail:failure", task.getException());
                            signUp();
                        }
                    }
                });
    }

    private String getEmail() {
        String emailStr = email.getText().toString();

        return emailStr;
    }

    private String getPassword() {
        String passwordStr = password.getText().toString();

        return passwordStr;
    }

    public void filterFire(View view) {
        einsätze.clear();
        jsonRead(1);
    }

    public void filterTechnical(View view) {
        einsätze.clear();
        jsonRead(2);
    }

    public void defaultOnClick(View view){
        einsätze.clear();
        jsonRead(3);
    }

    public void jsonRead(int was) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(was == 1) {
                            try {
                                int countEinsaetze = response.getInt("cnt_einsaetze") - 1;
                                JSONObject einsaetze = response.getJSONObject("einsaetze");


                                for (int i = 0; i < countEinsaetze; i++) {
                                    JSONObject count = einsaetze.getJSONObject(i + "");
                                    JSONObject einsatz = count.getJSONObject("einsatz");
                                    JSONObject adresse = einsatz.getJSONObject("adresse");

                                    String startzeit = einsatz.getString("startzeit");
                                    int alarmstufe = einsatz.getInt("alarmstufe");
                                    String art = einsatz.getString("einsatzart");
                                    int anzahl = einsatz.getInt("cntfeuerwehren");

                                    String straße = adresse.getString("default");
                                    String ort = adresse.getString("emun");

                                    if(art.equals("BRAND")) {
                                        einsätze.add(new Einsatz(art, ort, straße, alarmstufe, anzahl, startzeit));
                                    }
                                }

                                myAdapter.notifyDataSetChanged();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }

                        else if(was == 2){
                            try {
                                int countEinsaetze = response.getInt("cnt_einsaetze") - 1;
                                JSONObject einsaetze = response.getJSONObject("einsaetze");


                                for (int i = 0; i < countEinsaetze; i++) {
                                    JSONObject count = einsaetze.getJSONObject(i + "");
                                    JSONObject einsatz = count.getJSONObject("einsatz");
                                    JSONObject adresse = einsatz.getJSONObject("adresse");

                                    String startzeit = einsatz.getString("startzeit");
                                    int alarmstufe = einsatz.getInt("alarmstufe");
                                    String art = einsatz.getString("einsatzart");
                                    int anzahl = einsatz.getInt("cntfeuerwehren");

                                    String straße = adresse.getString("default");
                                    String ort = adresse.getString("emun");

                                    if(art.equals("TEE")) {
                                        einsätze.add(new Einsatz(art, ort, straße, alarmstufe, anzahl, startzeit));
                                    }
                                }

                                myAdapter.notifyDataSetChanged();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }

                        else if(was == 3){
                            try {
                                int countEinsaetze = response.getInt("cnt_einsaetze") - 1;
                                JSONObject einsaetze = response.getJSONObject("einsaetze");


                                for (int i = 0; i < countEinsaetze; i++) {
                                    JSONObject count = einsaetze.getJSONObject(i + "");
                                    JSONObject einsatz = count.getJSONObject("einsatz");
                                    JSONObject adresse = einsatz.getJSONObject("adresse");

                                    String startzeit = einsatz.getString("startzeit");
                                    int alarmstufe = einsatz.getInt("alarmstufe");
                                    String art = einsatz.getString("einsatzart");
                                    int anzahl = einsatz.getInt("cntfeuerwehren");

                                    String straße = adresse.getString("default");
                                    String ort = adresse.getString("emun");


                                    einsätze.add(new Einsatz(art, ort, straße, alarmstufe, anzahl, startzeit));
                                }

                                myAdapter.notifyDataSetChanged();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                        Log.d("JSONERROR: ", "Something happened while reading");
                    }
                }
        );

        myAdapter.notifyDataSetChanged();
        requestQueue.add(jsonObjectRequest);
    }
}