package com.example.java_oglen.jsonuserloginregister;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilePage extends AppCompatActivity
{
    static SharedPreferences sp;
    static SharedPreferences.Editor edit;

    TextView txt;
    Button cikis;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        txt=(TextView)  findViewById(R.id.textView);
        cikis= (Button) findViewById(R.id.cikis_yap);

        sp=getSharedPreferences("urun", Context.MODE_PRIVATE);
        edit=sp.edit();

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder uyari=new AlertDialog.Builder(ProfilePage.this);
                uyari.setTitle("Çıkış Yap");
                uyari.setMessage("Çıkış Yapmak İstediğinize Emin Misiniz?");
                uyari.setCancelable(true);
                //EVET
                uyari.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        edit.remove("kid");
                        if(edit.commit()){
                            Toast.makeText(ProfilePage.this, "Çıkış Yaptınız", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
                uyari.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProfilePage.this, "İptal Edildi", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alt=uyari.create();
                alt.show();

            }
        });
    }
}
