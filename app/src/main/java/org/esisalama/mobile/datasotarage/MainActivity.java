package org.esisalama.mobile.datasotarage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.esisalama.mobile.datasotarage.database.AppDatabase;
import org.esisalama.mobile.datasotarage.database.User;
import org.esisalama.mobile.datasotarage.database.UserDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText firstName;
    private EditText email;
    private EditText matricule;
    private TextView textView;
    private Button btnSave;
    private Button btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialisation();

        setup();
    }

    private void setup() {
        btnSave.setOnClickListener(view -> {
            String nom = name.getText().toString();
            String postNom = firstName.getText().toString();
            String mail = email.getText().toString();
            String mat = matricule.getText().toString();

            enregistrer(nom, postNom, mail, mat);
        });

        btnRead.setOnClickListener(view -> {
            lire();
        });
    }

    private void lire() {
        AppDatabase db = Room
                .databaseBuilder(this, AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        UserDAO dao = db.userDao();
        List<User> users = dao.findAll();

        for (User u: users) {
            textView.setText(textView.getText() + "\n" + u.matricule);
        }

    }

    private void enregistrer(String nom, String postNom, String email, String matricule) {
        User user = new User(nom, postNom, email, matricule);
        AppDatabase db = Room
                .databaseBuilder(this, AppDatabase.class, "db")
                .allowMainThreadQueries()
                .build();

        UserDAO dao = db.userDao();
        dao.instert(user);

        User dbUser = dao.findOne(1);
        Toast.makeText(this, dbUser.matricule, Toast.LENGTH_SHORT).show();
    }

    private void initialisation() {
        name = findViewById(R.id.name);
        firstName = findViewById(R.id.firstName);
        email = findViewById(R.id.email);
        matricule = findViewById(R.id.mat);
        textView = findViewById(R.id.textView);
        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
    }
}