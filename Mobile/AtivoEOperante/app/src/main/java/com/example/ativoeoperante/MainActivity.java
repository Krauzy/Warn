package com.example.ativoeoperante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spiOrgao,spiNivel,spiProb;
    private EditText txtTitulo,txtDesc;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spiOrgao=findViewById(R.id.spiOrgao);
        spiNivel=findViewById(R.id.spiNivel);
        spiProb=findViewById(R.id.spiProb);
        txtTitulo=findViewById(R.id.txtTit);
        txtDesc=findViewById(R.id.txtDen);
        btn=findViewById(R.id.btnProx);
        List<String> nivel = Arrays.asList("1","2","3","4","5");
        List<String> orgaos = Arrays.asList("a preencher");
        List<String> probs = Arrays.asList("a preencher");
        ArrayAdapter<String> adapterN = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,nivel);
        spiNivel.setAdapter(adapterN);

        ArrayAdapter<String> adapterO = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,orgaos);
        spiOrgao.setAdapter(adapterO);
        ArrayAdapter<String> adapterP = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,probs);
        spiProb.setAdapter(adapterP);

        btn.setOnClickListener(e->{
            Botao();
        });
    }

    private boolean VerificaLogin()
    {

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Toast.makeText(this, "Preencha "+myPreferences.getString("email","erro"), Toast.LENGTH_SHORT).show();
        return myPreferences.getBoolean("logado",false);

    }


    private void Botao()
    {


        if(txtTitulo.getText().length()!=0 && txtDesc.getText().length()!=0)
        {
            Intent intent = new Intent(this,EnderecoActivity.class);
            intent.putExtra("titulo",txtTitulo.getText());
            intent.putExtra("descricao",txtDesc.getText());
            intent.putExtra("nivel",spiNivel.getSelectedItem().toString());
            intent.putExtra("orgao",spiOrgao.getSelectedItem().toString());
            intent.putExtra("prob",spiProb.getSelectedItem().toString());
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Preencha todos os dados!", Toast.LENGTH_SHORT).show();
    }

    private void Registrar()
    {

    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_deslogar:
                SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.clear();
                myEditor.commit();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
