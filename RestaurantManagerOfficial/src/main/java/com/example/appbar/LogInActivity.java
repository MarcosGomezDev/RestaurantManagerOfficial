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
import com.example.appbar.data.DataBase;
import com.example.appbar.data.ItemData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase inicial donde el usuario de registrarse o iniciar sesion en la aplicación.
 */
@SuppressWarnings("ALL") // Esto hay que quitarlo
public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private DataBase dataBase = new DataBase();
    private ItemData item;
    private AwesomeValidation awesomeValidation;
    private FirebaseAuth mFirebaseAuth;
    private EditText emailEditText, passwordEditText;
//    private CheckBox recUserCheck, conditionsCheck; Para implementar en la próxima actualización.
    private Button logInButton, signInButton;
    private String date;
    private String email;
    private String pass;
    public static boolean newLogin = true;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle(R.string.app_name);
        item = new ItemData();

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
//        recUserCheck = findViewById(R.id.recUserCheck); Para implementar en la próxima actualización.
        logInButton = findViewById(R.id.loginButton);
        signInButton = findViewById(R.id.signInButton);

//        recUserCheck = findViewById(R.id.recUserCheck); Para implementar en la próxima actualización.
//        conditionsCheck = findViewById(R.id.conditionsCheckBox); Para implementar en la próxima actualización.
        logInButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
//        recUserCheck.setChecked(true); Para implementar en la próxima actualización.
//        conditionsCheck.setChecked(false); Para implementar en la próxima actualización.

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
            case R.id.loginButton:
                email = emailEditText.getText().toString();
                pass = passwordEditText.getText().toString();
                if(awesomeValidation.validate()) {
                    setSignIn(email, pass);
                } else {
                    Toast.makeText(LogInActivity.this, "Error en la validación.",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.signInButton:
                email = emailEditText.getText().toString();
                pass = passwordEditText.getText().toString();
                if(awesomeValidation.validate()) {
                    createAccount(email, pass);
                } else {
                    Toast.makeText(LogInActivity.this, "Error en la validación.",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * Método para iniciar sesion en la aplicación.
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     */
    private void setSignIn(String email, String password) {
//        if(conditionsCheck.isChecked()) {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogInActivity.this,
                                        "Usuario iniciado con éxito.",
                                        Toast.LENGTH_LONG).show();
                                goHome();
                            } else {
                                Toast.makeText(LogInActivity.this,
                                        "Error",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
//        } else {
//            showAlert("Debe aceptar los Términos y condiciones de la aplicación.",
//                    "Términos y condiciones");
//        }  Para implementar en la próxima actualización.
    }

    /**
     * Método para crear una cuenta nueva.
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     */
    private void createAccount(String email, String password) {
//        if(conditionsCheck.isChecked()) {  Para implementar en la próxima actualización.
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogInActivity.this,
                                        "Usuario registrado con éxito.",
                                        Toast.LENGTH_LONG).show();
                                setSignIn(email, password);
                            } else {
                                Toast.makeText(LogInActivity.this,
                                        "Error",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
            }));
//        }  Para implementar en la próxima actualización.
    }

    /**
     * Metodo para dirigir al usuario a la vista Home.
     */
    private void goHome() {
        Intent logIn = new Intent(LogInActivity.this,
                MenuActivity.class);
        logIn.putExtra("email", emailEditText.getText().toString());
        logIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logIn);
    }

    /**
     * Metodo para mostrar una alerta.
     * @param errorMessage Mensaje de error.
     * @param errorTitleString Titulo del error.
     */
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
        if(dataBase.getCurrentUser() != null){
            goHome();
        }
    }

    /**
     * Método encargado de mostrar el toast correcto.
     * @param error Error que se pasa al método.
     */
    private void getToastError(String error) {
        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(LogInActivity.this, "El formato del token personalizado es " +
                        "incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(LogInActivity.this, "El token personalizado corresponde a " +
                        "una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(LogInActivity.this, "La credencial de autenticación " +
                        "proporcionada tiene un formato incorrecto o ha caducado.",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(LogInActivity.this, "La dirección de correo electrónico está " +
                        "mal formateada.", Toast.LENGTH_LONG).show();
                emailEditText.setError("La dirección de correo electrónico está mal formateada.");
                emailEditText.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(LogInActivity.this, "La contraseña no es válida o el usuario no " +
                        "tiene contraseña.", Toast.LENGTH_LONG).show();
                passwordEditText.setError("la contraseña es incorrecta ");
                passwordEditText.requestFocus();
                passwordEditText.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(LogInActivity.this, "Las credenciales proporcionadas no " +
                        "corresponden al usuario que inició sesión anteriormente..",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(LogInActivity.this,"Esta operación es sensible y requiere " +
                        "autenticación reciente. Inicie sesión nuevamente antes de volver a " +
                        "intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(LogInActivity.this, "Ya existe una cuenta con la misma " +
                        "dirección de correo electrónico pero diferentes credenciales de " +
                        "inicio de sesión. Inicie sesión con un proveedor asociado a esta " +
                        "dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(LogInActivity.this, "La dirección de correo electrónico " +
                        "ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                emailEditText.setError("La dirección de correo electrónico ya está siendo " +
                        "utilizada por otra cuenta.");
                emailEditText.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(LogInActivity.this, "Esta credencial ya está asociada con una " +
                        "cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(LogInActivity.this, "La cuenta de usuario ha sido inhabilitada " +
                        "por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(LogInActivity.this, "La credencial del usuario ya no es " +
                        "válida. El usuario debe iniciar sesión nuevamente.",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(LogInActivity.this, "No hay ningún registro de usuario que " +
                        "corresponda a este identificador. Es posible que se haya eliminado " +
                        "al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(LogInActivity.this, "La credencial del usuario ya no es " +
                        "válida. El usuario debe iniciar sesión nuevamente.",
                        Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(LogInActivity.this, "Esta operación no está permitida. " +
                        "Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(LogInActivity.this, "La contraseña proporcionada no " +
                        "es válida..", Toast.LENGTH_LONG).show();
                passwordEditText.setError("La contraseña no es válida, debe tener al " +
                        "menos 6 caracteres");
                passwordEditText.requestFocus();
                break;
        }
    }
}





