package leandro.whatsapp.com.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;

    //retorna a instancia do FirebaseDatabase
    public static DatabaseReference getFirebaseDatabase(){
        if(databaseReference==null){
            databaseReference = FirebaseDatabase.getInstance().getReference();

        }
        return databaseReference;
    }
    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if(auth==null){
            auth= FirebaseAuth.getInstance();
        }
        return auth;


    }
}
