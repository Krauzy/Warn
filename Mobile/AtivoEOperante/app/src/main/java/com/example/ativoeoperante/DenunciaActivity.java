package com.example.ativoeoperante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ativoeoperante.Classes.Generico;
import com.example.ativoeoperante.Util.AcessaWsTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DenunciaActivity extends AppCompatActivity {
    private Spinner spiOrgao,spiNivel,spiProb;
    private EditText txtTitulo,txtDesc;
    private Button btn;
    private String KEY;
    // Key = A93I53PP_042104
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
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        List<String> nivel = Arrays.asList("1","2","3","4","5");
        List<Generico> orgaos = preencheListas("https://warn-ing.herokuapp.com/API/organs/all?token="+myPreferences.getString("KEY",""));
        List<Generico> probs = preencheListas("https://warn-ing.herokuapp.com/API/types/all?token="+myPreferences.getString("KEY",""));
        ArrayAdapter<String> adapterN = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,nivel);
        spiNivel.setAdapter(adapterN);

        ArrayAdapter<Generico> adapterO = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,orgaos);
        spiOrgao.setAdapter(adapterO);
        ArrayAdapter<Generico> adapterP = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,probs);
        spiProb.setAdapter(adapterP);

        btn.setOnClickListener(e->{
            Botao();
        });
    }


    private List<Generico> preencheListas(String URL)
    {
        List<Generico> lista = new ArrayList<>();
        AcessaWsTask task = new AcessaWsTask();
        try {
            String json= task.execute(URL).get();
            JSONObject o = new JSONObject(json),aux;
            int i=1;
            while(i<=o.length())
            {
                aux= o.getJSONObject(String.valueOf(i));
                lista.add(new Generico(aux.getInt("id"),aux.getString("name"),aux.getString("description")));
                i++;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
        }
        return lista;
    }


    private void Botao()
    {


        if(txtTitulo.getText().length()>0 && txtDesc.getText().length()>0)
        {
            Generico org =(Generico) spiOrgao.getSelectedItem(), prob =(Generico) spiProb.getSelectedItem() ;
            Intent intent = new Intent(this,EnderecoActivity.class);
            intent.putExtra("titulo",txtTitulo.getText().toString());
            intent.putExtra("descricao",txtDesc.getText().toString());
            intent.putExtra("nivel",Integer.parseInt(spiNivel.getSelectedItem().toString()));
            intent.putExtra("orgao",org.getId());
            intent.putExtra("prob",prob.getId());
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Preencha todos os dados!", Toast.LENGTH_SHORT).show();
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
