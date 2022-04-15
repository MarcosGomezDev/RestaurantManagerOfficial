package com.example.appbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("ALL") // Esto hay que quitarlo
public class LogIn extends AppCompatActivity implements View.OnClickListener{

    private DataBase dataBase;
    private DatabaseReference myRef;

    private Items item2;
    private AwesomeValidation awesomeValidation;
    private FirebaseAuth mFirebaseAuth;
    private EditText emailEditText, passwordEditText;
    private CheckBox recUserCheck, conditionsCheck;
    private Button logInButton, signInButton, rellenarButton;
    private String date;
    private String email;
    private String pass;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle(R.string.app_name);

        dataBase = new DataBase();
        myRef = dataBase.getInstance().getReference();

        item = new Items();
        if (! item.correctInto) {
            item.addAllSampleItems();
            item.correctInto = true;
        }

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        recUserCheck = findViewById(R.id.recUserCheck);
        logInButton = findViewById(R.id.logInButton);
        signInButton = findViewById(R.id.signInButton);
        rellenarButton = findViewById(R.id.rellenarButton);
        recUserCheck = findViewById(R.id.recUserCheck);
        conditionsCheck = findViewById(R.id.conditionsCheckBox);
        logInButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        rellenarButton.setOnClickListener(this);

        recUserCheck.setChecked(true);
        conditionsCheck.setChecked(false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,
                R.id.emailEditText, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this,
                R.id.passwordEditText, ".{6,}", R.string.invalid_password);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logInButton:
                email = emailEditText.getText().toString();
                pass = passwordEditText.getText().toString();
                if(awesomeValidation.validate()) {
                    setSignIn(email, pass);
                } else {
                    Toast.makeText(LogIn.this, "Error en la validación.",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.signInButton:
                email = emailEditText.getText().toString();
                pass = passwordEditText.getText().toString();
                if(awesomeValidation.validate()) {
                    createAccount(email, pass);
                } else {
                    Toast.makeText(LogIn.this, "Error en la validación.",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.rellenarButton:
                emailEditText.setText("marcos@marcos.es");
                passwordEditText.setText("marcos");
                break;

            default:
                Toast.makeText(LogIn.this, "Algo salió mal.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void setSignIn(String email, String password) {
        if(conditionsCheck.isChecked()) {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogIn.this, "Usuario iniciado con éxito.",
                                        Toast.LENGTH_LONG).show();
                                goHome();
                            } else {
                                String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException()))
                                        .getErrorCode();
                                getToastError(errorCode);
                            }
                        }
                    });
        } else {
            showAlert("Debe aceptar los Términos y condiciones de la aplicación.",
                    "Términos y condiciones");
        }
    }

    private void createAccount(String email, String password) {
        if(conditionsCheck.isChecked()) {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogIn.this, "Usuario registrado con éxito.",
                                        Toast.LENGTH_LONG).show();
                                setSignIn(email, password);
                            } else {
                                String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException()))
                                        .getErrorCode();
                                getToastError(errorCode);
                            }
                        }
            }));
        }
    }

    private void goHome() {
        Intent logIn = new Intent(LogIn.this,
                MainActivity.class);
        logIn.putExtra("email", emailEditText.getText().toString());
        logIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logIn);
    }

    private void showAlert(String errorMessage, String errorTitleString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(errorMessage);
        builder.setTitle(errorTitleString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            goHome();
        }

    }

    private void createSampleItems() {
        item = new Items();
        item.addAllSampleItems();
    }

    public void additem (@NonNull String PK, @NonNull String description, @NonNull double price) {

        item = new Items(description, price);

        myRef.child(dataBase.PARENT_ITEMS()).child(PK).setValue(item);

    }

    private void getToastError(String error) {
        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(LogIn.this, "El formato del token personalizado es " +
                        "incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(LogIn.this, "El token personalizado corresponde a " +
                        "una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(LogIn.this, "La credencial de autenticación " +
                        "proporcionada tiene un formato incorrecto o ha caducado.",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(LogIn.this, "La dirección de correo electrónico está " +
                        "mal formateada.", Toast.LENGTH_LONG).show();
                emailEditText.setError("La dirección de correo electrónico está mal formateada.");
                emailEditText.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(LogIn.this, "La contraseña no es válida o el usuario no " +
                        "tiene contraseña.", Toast.LENGTH_LONG).show();
                passwordEditText.setError("la contraseña es incorrecta ");
                passwordEditText.requestFocus();
                passwordEditText.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(LogIn.this, "Las credenciales proporcionadas no " +
                        "corresponden al usuario que inició sesión anteriormente..",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(LogIn.this,"Esta operación es sensible y requiere " +
                        "autenticación reciente. Inicie sesión nuevamente antes de volver a " +
                        "intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(LogIn.this, "Ya existe una cuenta con la misma " +
                        "dirección de correo electrónico pero diferentes credenciales de " +
                        "inicio de sesión. Inicie sesión con un proveedor asociado a esta " +
                        "dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(LogIn.this, "La dirección de correo electrónico " +
                        "ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                emailEditText.setError("La dirección de correo electrónico ya está siendo " +
                        "utilizada por otra cuenta.");
                emailEditText.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(LogIn.this, "Esta credencial ya está asociada con una " +
                        "cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(LogIn.this, "La cuenta de usuario ha sido inhabilitada " +
                        "por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(LogIn.this, "La credencial del usuario ya no es " +
                        "válida. El usuario debe iniciar sesión nuevamente.",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(LogIn.this, "No hay ningún registro de usuario que " +
                        "corresponda a este identificador. Es posible que se haya eliminado " +
                        "al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(LogIn.this, "La credencial del usuario ya no es " +
                        "válida. El usuario debe iniciar sesión nuevamente.",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(LogIn.this, "Esta operación no está permitida. " +
                        "Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(LogIn.this, "La contraseña proporcionada no " +
                        "es válida..", Toast.LENGTH_LONG).show();
                passwordEditText.setError("La contraseña no es válida, debe tener al " +
                        "menos 6 caracteres");
                passwordEditText.requestFocus();
                break;

        }

    }

}





//DatabaseReference myRef = DataBase.instance.getReference("msffg");
//myRef.setValue("Hello, Wosdfrld!");




/* Todo esto va fuera
        // DatabaseReference databaseReference = DataBase.instance.getReference("message");

        DataBase.DataRef(DataBase.PATH_EMPLOYEES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView2.setText(snapshot.getValue(String.class) + " " + date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textView2.setText("Error en la conexión con la base de datos.");
            }
        });

        public void lolo() {
            DataBase dataBase = new DataBase();
            dataBase.DataRef(dataBase.getPATH_EMPLOYEES()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    textView2.setText(snapshot.getValue(String.class) + " " + date);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    textView2.setText("Error en la conexión con la base de datos.");
                }
            });
        }
*/



//      awesomeValidation.addValidation(activity,
//      R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);




/*
    Estos dos metodos dan error

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
*/



