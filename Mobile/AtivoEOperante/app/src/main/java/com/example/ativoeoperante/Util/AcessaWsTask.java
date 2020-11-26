package com.example.ativoeoperante.Util;

import android.os.AsyncTask;


public class AcessaWsTask extends AsyncTask <String,Integer,String>
{
    @Override
    protected String doInBackground(String... url){
        String dados = AcessaWS.consumir(url[0]);
        return dados;
    }
}
