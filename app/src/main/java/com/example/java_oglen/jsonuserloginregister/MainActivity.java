package com.example.java_oglen.jsonuserloginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity
{
    EditText et_ad, et_soyad, et_tel, et_mail, et_pass;
    Button button_kayit;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_ad=(EditText) findViewById(R.id.et_ad);
        et_soyad=(EditText) findViewById(R.id.et_soyad);
        et_tel=(EditText) findViewById(R.id.et_tel);
        et_mail=(EditText) findViewById(R.id.et_mail);
        et_pass=(EditText) findViewById(R.id.et_pass);
        button_kayit=(Button) findViewById(R.id.button_kayit);



        button_kayit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                final String ad=et_ad.getText().toString();
                final String soyad=et_soyad.getText().toString();
                final String tel=et_tel.getText().toString();
                final String mail=et_mail.getText().toString();
                final String pass=et_pass.getText().toString();

                if (ad.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Adınızı giriniz", Toast.LENGTH_SHORT).show();
                }
               else if (soyad.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Soyadınızı giriniz", Toast.LENGTH_SHORT).show();
                }
                else if (tel.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Telefonu giriniz", Toast.LENGTH_SHORT).show();
                }
                else if (mail.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Mail giriniz", Toast.LENGTH_SHORT).show();
                }
               else if (pass.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Parolanızı giriniz", Toast.LENGTH_SHORT).show();
                }
                else
                    {

                    url = "http://jsonbulut.com/json/userRegister.php?ref=cb226ff2a31fdd460087fedbb34a6023&" +
                            "userName=" + ad +
                            "&userSurname=" +soyad+
                            "&&userPhone=" + tel +
                            "&userMail=" +mail +
                            "&userPass=" + pass;

                        Log.d("URL",url);
                        new jsonData(url,MainActivity.this).execute();

                }
            }
        });


    }

    public void Giris(View view) {
        Intent i= new Intent(MainActivity.this,LoginPage.class);
        startActivity(i);
    }

    class jsonData extends AsyncTask<Void, Void, Void>
    {
        String url="";
        String data="";
        ProgressDialog pro;
        Context cnx;

        public jsonData(String url, Context cnx){
            this.url=url;
            this.cnx=cnx;
            pro=new ProgressDialog(cnx);
            pro.setMessage("İşlem yapılıyor , Lütfen bekleyiniz");
            pro.show(); //doInbckground başladığında çalışcak



        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                data= Jsoup.connect(url).ignoreContentType(true).get().body().text();

            }catch (Exception ex){

                Log.e("data json hatası", String.valueOf(ex));
            }



            return null;

            }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            //grafiksel özelliği olan işlemler bu bölümde yapılır.
            Log.d("Gelen data",data);
            try {

                JSONObject obj=new JSONObject(data);
                boolean durum=obj.getJSONArray("user").getJSONObject(0).getBoolean("durum");
                String mesaj=obj.getJSONArray("user").getJSONObject(0).getString("mesaj");
                if (durum==true)
                {
                    //kullanıcı kaydı başarılı
                    Toast.makeText(cnx,mesaj,Toast.LENGTH_LONG).show();
                    String kid=obj.getJSONArray("user").getJSONObject(0).getString("kullaniciId");
                    Log.d("kid",kid);
                    Intent i= new Intent(MainActivity.this, LoginPage.class);
                    startActivity(i);
                }

                else
                {
                    //Kullanıcı kaydı başarısız
                    Toast.makeText(cnx,mesaj,Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //proyu kapat
            pro.dismiss();
        }

    }
    }

