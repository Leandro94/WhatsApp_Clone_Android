package leandro.whatsapp.com.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode){
        //int cont=0;
        if(Build.VERSION.SDK_INT>=23){
            List<String> listPermissoes =  new ArrayList<>();

            for(String permissao: permissoes){
               Boolean temPermissao = ContextCompat.checkSelfPermission(activity,permissao)== PackageManager.PERMISSION_GRANTED;
               if(!temPermissao){
                   listPermissoes.add(permissao);
               }
               //Caso a lista esteja vazia náo é necessário solicitar permissao
                if(listPermissoes.isEmpty()) return true;

                String[] novasPermissoes = new String[listPermissoes.size()];
                listPermissoes.toArray(novasPermissoes);

                //solicita permissao
                 ActivityCompat.requestPermissions(activity, novasPermissoes,requestCode);

                //Log.d("XXX","array "+novasPermissoes[cont]);
                //cont++;


            }
        }
        return true;
    }
}
