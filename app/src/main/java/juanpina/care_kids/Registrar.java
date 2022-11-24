package juanpina.care_kids;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import juanpina.care_kids.R;

public class Registrar extends AppCompatActivity {
    Button button5;
    Button btnvolver;
    private EditText Nombre, Clave, nmbContacto1, nroContacto1, nmbContacto2, nroContacto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        /**/
        Nombre = findViewById(R.id.boxNombre);
        Clave = findViewById(R.id.boxPassword);
        nmbContacto1 = findViewById(R.id.boxNmbContacto1);
        nroContacto1 = findViewById(R.id.boxNroContacto1);
        nmbContacto2 = findViewById(R.id.boxNmbContacto2);
        nroContacto2 = findViewById(R.id.boxNroContacto2);


        button5=(Button) findViewById(R.id.guardar);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent button5 = new Intent(Registrar.this, Login_Activity.class);
                startActivity(button5);
                insertar_usuario();
            }
        });

        btnvolver=(Button) findViewById(R.id.btnvolver);
        btnvolver.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                Intent btnvolver = new Intent(Registrar.this, MainActivity.class);
                startActivity(btnvolver);
            }
        });
    }

    /**/
    public void insertar_usuario()
    {
        try
        {
            String nombre = Nombre.getText().toString();
            String clave = Clave.getText().toString();
            String NmbContacto1 = nmbContacto1.getText().toString();
            String NroContacto1 = nroContacto1.getText().toString();
            String NmbContacto2 = nmbContacto2.getText().toString();
            String NroContacto2 = nroContacto2.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_Usuarios", Context.MODE_PRIVATE,null);
            //db.execSQL("CREATE TABLE IF NOT EXISTS usuario(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre VARCHAR,clave VARCHAR, nmbContacto1 VARCHAR, nroContacto1 VARCHAR, nmbContacto2 VARCHAR, nroContacto2 VARCHAR)");
            db.execSQL("CREATE TABLE IF NOT EXISTS usuario(nombre VARCHAR,clave VARCHAR, nmbContacto1 VARCHAR, nroContacto1 VARCHAR, nmbContacto2 VARCHAR, nroContacto2 VARCHAR)");

            String sql = "insert into usuario(nombre,clave,NmbContacto1, NroContacto1, NmbContacto2, NroContacto2)values(?,?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,nombre);
            statement.bindString(2,clave);
            statement.bindString(3,NmbContacto1);
            statement.bindString(4,NroContacto1);
            statement.bindString(5,NmbContacto2);
            statement.bindString(6,NroContacto2);
            statement.execute();
            Toast.makeText(this,"Usuario guardado satisfactoriamente.",Toast.LENGTH_LONG).show();

            Nombre.setText("");
            Clave.setText("");
            nmbContacto1.setText("");
            nroContacto1.setText("");
            nmbContacto2.setText("");
            nroContacto2.setText("");
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }
}
