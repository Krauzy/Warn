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
import com.example.ativoeoperante.Util.MaskEditUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class EnderecoActivity extends AppCompatActivity {
    private EditText endereco,cidade,bairro,cep,uf,numero;
    private Button btn;
    private String titulo,desc;
    private int nivel,orgao,prob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        endereco=findViewById(R.id.txtEndereco);
        cidade=findViewById(R.id.txtCid);
        bairro=findViewById(R.id.txrBai);
        cep=findViewById(R.id.txtCEP);
        cep.addTextChangedListener(MaskEditUtil.mask(cep,MaskEditUtil.FORMAT_CEP));
        uf=findViewById(R.id.txtUF);
        btn=findViewById(R.id.btnEfe);
        numero=findViewById(R.id.txtNumb);
        btn.setOnClickListener(e->{
            Denuncia();
        });

        titulo=getIntent().getStringExtra("titulo");
        desc=getIntent().getStringExtra("descricao");
        nivel=getIntent().getIntExtra("nivel",0);
        orgao=getIntent().getIntExtra("orgao",0);
        prob=getIntent().getIntExtra("prob",0);

    }

    //https://warn-ing.herokuapp.com/API/warning?title=TITLE&descriptio=DESCRIPTION&level=LEVEL&street=STREET&district=DISTRICT&number=NUMBER&cep=CEP&city=CITY&state=STATE&user=USER&organ=ORGAN&type=TYPE&token=KEY

    private void Denuncia()
    {

        if(endereco.getText().length()>0 && cidade.getText().length()>0 && bairro.getText().length()>0 && numero.getText().length()>0 && uf.getText().length()==2 && cep.getText().length()==9)
        {
            SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            String url = "https://warn-ing.herokuapp.com/API/warning?title="+titulo+"&description="+desc+"&level="+nivel+"&street="+endereco.getText()+"&district="+bairro.getText()+"&number="
                    +numero.getText()+"&cep="+cep.getText().toString().replace("-","")+"&city="+cidade.getText()+"&state="+uf.getText()+
                    "&user="+myPreferences.getInt("ID",0)+"&organ="+orgao+"&type="+prob+"&token="+myPreferences.getString("KEY","");
            AcessaWsTask task = new AcessaWsTask();
            try {
                String json =task.execute(url).get();
                JSONObject o = new JSONObject(json);
                if(o.getString("message").equals("OK"))
                {
                    Toast.makeText(this, "Denuncia Registrada com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Ocorreu um erro inexperado, confira os dados", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e)
            {
                Toast.makeText(this, "Ocorreu um erro inexperado, confira os dados", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(this, "Preencha os dados!", Toast.LENGTH_SHORT).show();


    }


}