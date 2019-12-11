package leandro.whatsapp.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import leandro.whatsapp.com.R;
import leandro.whatsapp.com.config.ConfiguracaoFirebase;
import leandro.whatsapp.com.helper.Base64Custom;
import leandro.whatsapp.com.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText editNome, editSenha, editEmail;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editLoginEmail);
        editSenha =  findViewById(R.id.editLoginSenha);


    }
    public void salvarUsuario(final Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,"Sucesso ao cadastrar usuário",Toast.LENGTH_SHORT).show();
                    finish();
                    try{
                        String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                        usuario.setId(identificadorUsuario);
                        usuario.salvar();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    String excecao="";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um e-mail válido!";
                    }catch(FirebaseAuthUserCollisionException e){
                        excecao = "E-mail já cadastrado!";
                    }
                    catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "+e.getMessage();
                        e.printStackTrace();
                    }

                }

            }
        });

    }
    public void validarCadastro(View view){

        //recuperar textos dos campos
        String textoNome = editNome.getText().toString();
        String textoEmail = editEmail.getText().toString();
        String textoSenha = editSenha.getText().toString();

        if( !textoNome.isEmpty()){//verifica nome

            if(!textoEmail.isEmpty()){//verifica e-mail

                if(!textoSenha.isEmpty()){//verifica senha
                    Usuario usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);

                    salvarUsuario(usuario);

                }
                else{
                    Toast.makeText(CadastroActivity.this,"Preencha a senha!",Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(CadastroActivity.this,"Preencha o email!",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(CadastroActivity.this,
                    "Preencha o nome!",Toast.LENGTH_SHORT).show();
        }

    }
}
