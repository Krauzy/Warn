package com.example.ativoeoperante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ativoeoperante.Util.AcessaWsTask;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button btnEnt,btnReg;
    private EditText txtemail,txtsenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnEnt=findViewById(R.id.btnEnt);
        btnReg=findViewById(R.id.btnReg);
        txtemail=findViewById(R.id.txtemail);
        txtsenha=findViewById(R.id.txtsenha);
        btnEnt.setOnClickListener(e->{
            Login();
        });
        btnReg.setOnClickListener(e->{
            startActivity(new Intent(this,RegistroActivity.class));
        });
        VerificaLogin();

    }

    private void VerificaLogin()
    {
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean logado =myPreferences.getBoolean("logado",false);
        if(logado)
        {
            String email = myPreferences.getString("email","");
            String senha = myPreferences.getString("senha","");
            AcessaWsTask task = new AcessaWsTask();
            String URL= "https://warn-ing.herokuapp.com/API/login?email="+email+"&password="+senha;
            try {
                String json=task.execute(URL).get();
                JSONObject i = new JSONObject(json);
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putString("KEY",i.getString("KEY"));
                myEditor.putInt("ID",i.getInt("ID"));
                myEditor.commit();
                AcessaActivity(email,senha);
            }
            catch (Exception e)
            {

            }

        }

    }

    private void Login()
    {

        if(txtemail.getText().length()>0 && txtsenha.getText().length()>0)
        {
            AcessaWsTask task = new AcessaWsTask();
            String URL= "https://warn-ing.herokuapp.com/API/login?email="+txtemail.getText()+"&password="+txtsenha.getText();
            try {
                String json=task.execute(URL).get();
                JSONObject i = new JSONObject(json);


                SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putString("email",txtemail.getText().toString());
                myEditor.putString("senha",txtsenha.getText().toString());
                myEditor.putBoolean("logado",true);
                myEditor.putString("KEY",i.getString("KEY"));
                myEditor.putInt("ID",i.getInt("ID"));
                myEditor.commit();
                AcessaActivity(txtemail.getText().toString(),txtsenha.getText().toString());
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Dados incorretos ou conta inexistente!", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, "Preencha os dados!", Toast.LENGTH_SHORT).show();
    }

    private void AcessaActivity(String email,String senha)
    {
        Intent i = new Intent(this, DenunciaActivity.class);
        i.putExtra("email",email);
        i.putExtra("senha",senha);
        startActivity(i);
    }
}