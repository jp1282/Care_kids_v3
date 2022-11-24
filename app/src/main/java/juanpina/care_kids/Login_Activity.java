package juanpina.care_kids;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Login_Activity extends AppCompatActivity {
    Button button3;
    Button button4, btn_google;
    EditText usuario;
    EditText password;
    String DB_usuario, DB_clave, claveingresada;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.textousuario);
        password = (EditText) findViewById(R.id.textopassword);
        btn_google = (Button) findViewById(R.id.btn_google);

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn_google = new Intent(Login_Activity.this, login_google.class);
                startActivity(btn_google);
            }
        });

        button3=(Button) findViewById(R.id.volver);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button3 = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(button3);
            }
        });

        button4=(Button) findViewById(R.id.ingresar);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                if (!usuario.getText().toString().isEmpty() & !password.getText().toString().isEmpty()){
                    Intent button4 = new Intent(Login_Activity.this, Principal.class);
                    startActivity(button4);
                }else{
                    Toast.makeText(Login_Activity.this, "Debes ingresar los datos de acceso", Toast.LENGTH_SHORT).show();
                }*/
               buscar();
            }
        });

    }

    public void buscar()
    {

        try
        {
            SQLiteDatabase db = openOrCreateDatabase("BD_Usuarios", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS usuario(nombre VARCHAR,clave VARCHAR, nmbContacto1 VARCHAR, nroContacto1 VARCHAR, nmbContacto2 VARCHAR, nroContacto2 VARCHAR)");
            final Cursor c = db.rawQuery("select * from usuario where nombre=?", new String[]{String.valueOf(usuario.getText().toString())});
            c.moveToLast();
            int clave = c.getColumnIndex("clave");

            claveingresada = password.getText().toString();
            DB_clave = c.getString(clave).toString();

            if (claveingresada.equals(DB_clave)){
                Toast.makeText(this,"Usuario y clave correctos.",Toast.LENGTH_LONG).show();
                Intent button4 = new Intent(Login_Activity.this, Principal.class);
                button4.putExtra("usuario",String.valueOf(usuario.getText().toString()));
                startActivity(button4);
            }else{
                Toast.makeText(this,"Clave incorrecta.",Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error el usuario no existe.",Toast.LENGTH_LONG).show();
        }
    }


}