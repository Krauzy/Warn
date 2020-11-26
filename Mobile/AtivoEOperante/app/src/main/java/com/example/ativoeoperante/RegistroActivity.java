package com.example.ativoeoperante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ativoeoperante.Util.AcessaWsTask;
import com.example.ativoeoperante.Util.MaskEditUtil;

import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtCPF,nome,email,senha,senhacf,lastname,data;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtCPF=findViewById(R.id.txtCPF);
        txtCPF.addTextChangedListener(MaskEditUtil.mask(txtCPF, MaskEditUtil.FORMAT_CPF));
        nome=findViewById(R.id.txtNome);
        lastname=findViewById(R.id.txtlastN);
        email=findViewById(R.id.email);
        senha=findViewById(R.id.senha1);
        senhacf=findViewById(R.id.senha2);
        btn=findViewById(R.id.btnRegistro);
        data=findViewById(R.id.dateNasc);
        data.addTextChangedListener(MaskEditUtil.mask(data, MaskEditUtil.FORMAT_DATE));
        btn.setOnClickListener(e->{
            Cadastro();
        });

    }

    private void Cadastro()
    {
            if(nome.getText().length()>0  && lastname.getText().length()>0 && txtCPF.getText().length()>0 && email.getText().length()>0 && senha.getText().length()>0 && senhacf.getText().length()>0
                    && data.getText().length()>0)
            {
                if(senha.getText().toString().equals(senhacf.getText().toString()))
                {
                    if(data.getText().length()==10)
                    {


                        String cpf = txtCPF.getText().toString();
                        cpf=cpf.replace(".","").replace("-","");
                        AcessaWsTask task = new AcessaWsTask();
                        String URL= "https://warn-ing.herokuapp.com/API/register?name="+nome.getText()+"&lastname="+lastname.getText()+"&cpf="+cpf+"&date="+converteData(data.getText().toString())+"&email="
                                +email.getText()+"&password="+senha.getText()+"&admin=false";
                        try {
                            String json=task.execute(URL).get();
                            JSONObject i = new JSONObject(json);
                            if(i.getString("message").equals("OK"))
                            {
                                Toast.makeText(this, "Conta Registrada com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent  = new Intent(this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(this, "Falha no cadastro!", Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(this, "Falha no cadastro!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(this, "Insira a data corretamente", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "Senhas não correspondem!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Preencha todos os dados!", Toast.LENGTH_SHORT).show();
    }

    public String converteData(String data)
    {
        String[] separado = data.split("/");
        return separado[2]+"-"+separado[1]+"-"+separado[0];
    }
}