package com.example.java_oglen.jsonuserloginregister;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class LoginPage extends AppCompatActivity

{
    static SharedPreferences sha;
    static SharedPreferences.Editor edit;

    EditText etmail, etpass;
    Button buttonlogin, buttongotoregister;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        etmail=(EditText) findViewById(R.id.etmail);
        etpass=(EditText) findViewById(R.id.etpass);
        buttonlogin=(Button) findViewById(R.id.button_login);
        sha=getSharedPreferences("urun", Context.MODE_PRIVATE);
        edit=sha.edit();
        String kulId= sha.getString("kid","");

        if (!kulId.equals(""))
        {
            Intent i= new Intent(LoginPage.this, ProfilePage.class);
            i.putExtra("kulId",kulId);
            startActivity(i);

        }
        buttongotoregister=(Button) findViewById(R.id.button_gotoregister);

        buttongotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(LoginPage.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                final String mail = etmail.getText().toString();
                final String pass = etpass.getText().toString();

                if (mail.equals(""))
                {
                    Toast.makeText(LoginPage.this, "Mail giriniz", Toast.LENGTH_SHORT).show();
                }
                else if (pass.equals(""))
                {
                    Toast.makeText(LoginPage.this, "Parolanızı giriniz", Toast.LENGTH_SHORT).show();
                }
                else {
                    url = "http://jsonbulut.com/json/userLogin.php?ref=cb226ff2a31fdd460087fedbb34a6023&" +
                            "userEmail=" + mail +
                            "&userPass=" + pass +
                            "&face=no";

                    new jsonData(url, LoginPage.this).execute();

                    Intent intent = new Intent(LoginPage.this, ProfilePage.class);
                    startActivity(intent);

                }
            }
        });
    }

    class jsonData extends AsyncTask<Void,Void,Void>
    {
        String url = "";
        String dataLogin = "";
        Context cnx;
        ProgressDialog pro;

        public jsonData(String url, Context cnx)
        {
            this.url=url;
            this.cnx=cnx;
            pro = new ProgressDialog(cnx);
            pro.setMessage("Giriş Yapılıyor, Lütfen Bekleyiniz");
            pro.show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Log.d("Gelen Data: ", dataLogin);

            try {
                JSONObject object = new JSONObject(dataLogin);
                boolean durum = object.getJSONArray("user").getJSONObject(0).getBoolean("durum");
                String mesaj = object.getJSONArray("user").getJSONObject(0).getString("mesaj");
                if(durum==true)
                {

                    Toast.makeText(cnx, mesaj, Toast.LENGTH_SHORT).show();
                    String kid = object.getJSONArray("user").getJSONObject(0).getJSONObject("bilgiler").getString("userId");
                    Log.d("kid = ", kid);
                    LoginPage.edit.putString("kid", kid);
                   LoginPage.edit.commit();

                    Intent i= new Intent(cnx, ProfilePage.class);
                    cnx.startActivity(i);
                }
                else
                {

                    Toast.makeText(cnx, mesaj, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            pro.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                dataLogin = Jsoup.connect(url).ignoreContentType(true).get().body().text();
            }catch (Exception ex){
                Log.e("Data JSON Hatası","doInBackground",ex);
            }

            return null;
        }

    }


}
