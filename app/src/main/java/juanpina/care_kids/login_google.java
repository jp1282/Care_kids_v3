package juanpina.care_kids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_google extends AppCompatActivity {
    Button btn_ingresar;
    EditText correo,password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google);
        mAuth = FirebaseAuth.getInstance();

        btn_ingresar = findViewById(R.id.btnLogin);
        correo = findViewById(R.id.mail);
        password = findViewById(R.id.contraseña);


        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailuser = correo.getText().toString();
                String passuser = password.getText().toString();

                if(emailuser.isEmpty() && passuser.isEmpty()){
                    Toast.makeText(login_google.this, "Debe ingresar los datos correo y contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(emailuser, passuser);
                }
            }
        });
    }



    private void loginUser(String emailuser, String passuser) {
        mAuth.signInWithEmailAndPassword(emailuser, passuser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(login_google.this, Registrar.class));
                    Toast.makeText(login_google.this, "Bienbenido, porfavor cree su perfil", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login_google.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login_google.this, "Error al autenticarse", Toast.LENGTH_SHORT).show();
            }
        });
    }
}