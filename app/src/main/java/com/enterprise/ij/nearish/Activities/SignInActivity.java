package com.enterprise.ij.nearish.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprise.ij.nearish.Models.Usuario;
import com.enterprise.ij.nearish.Other.ServiceHandler;
import com.enterprise.ij.nearish.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignInActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        checkSession();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        signUpLink = (TextView) findViewById(R.id.link_signup);
        signUpLink.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void checkSession() {
        if(Usuario.getUserInstance().getToken()!=null){
            if(Usuario.getUserInstance().isFirstTime()){
                Intent intent = new Intent(this, GettingToKnowYou.class);
                startActivity(intent);
            }else{
             /**
              * Aquí va la llamada a la Activity principal
              * **/
            }
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    //==============================================================================================
    //                  SOLICITAR EL PERMISO PARA LEER LOS CONTACTOS DEL USUARIO
    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    //Metodo para comprobar y solicitar los permisos
    private boolean mayRequestContacts() {
        /**Analiza si la versión de SDK del dispositivo sobre el cual esta corriendo
         la aplicación es menor a la versión Mashmallow */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        /**Comprueba si el permiso para leer contactos se ha concedido con anterioridad */
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        /**Si un usuario previamente a denegado el permiso y aun asi sigue intentando entrar en la
         funcionalidad, por lo que es conveniete informarle las razones del permiso. */
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            /**Generar una notificación para informarle al usuario el porque de la solicitud del
             permiso, acompañado de un boton para conceder los permisos a la aplicación. */
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            /**Solicita que se le otorguen permisos a esta aplicación solo hizo
                             click sobre el boton OK del Snackbar. */
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    //Metodo que se invoca cada vez que se solicitan permisos

    /**
     * Recibe como parametros
     * El codigo de la solicitud aprobada.
     * Los permisos solicitados.
     * los resultados para los permisos correspondientes.
     */

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    //==============================================================================================
    //                  VALIDAR LOS COMPONENTES DEL FORMULARIO DE INGRESO
    //==============================================================================================

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        /**Comprobar si el objeto para el usuario esta vacio o no*/
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        /**
         * Bandera evidenciar algun error durante la validación de los datos
         * Variable para contener el campo a ser enfocado
         */
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        /**Comprobar si hubo un fallo durante el ingreso de datos*/
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("a");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        //return password.length() > 4;
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    //==============================================================================================
    //      CREAR LA LISTA DE EMAILS PROVENIENTES DEL PERFIL DE USUARIO PARA EL AUTOCOMPLETADO
    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    //Consultar Asincronicamente el o los emails almacenados en el perfil del usuario

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        /**Obtener y Retornar un Cursor que apunta a una tabla con los datos especificados  en la
         * consulta*/
        return new CursorLoader(this,
                /**Creamos un filtro para que la consulta se centre solo en los emails*/
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    //Mover los correos Obtenidos en la anterior consulta a través del Cursor retornado
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        /**Mientras el Cursor no termine de recorrer las Filas de Tabla, adicione las direcciones
         * de correo a la lista*/
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        /**Enviar la lista de Emails para el Autocompletado*/
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    //Adicionar la lista de terminos al TextView de tipo Autocomplete
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        /**Crear un adaptador que permitira adaptar los datos de la la lista al AutoCompleteTextView
         * utilizando una lista dropdown*/
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignInActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    //Definen los campos seleccionados para la consulta
    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    //==============================================================================================
    //  CLASE PARA ALMACENAR LOS USUARIOS Y METODOS ASICRONOS PARA VALIDAR EL USUARIO INGRESADO
    //==============================================================================================
    //----------------------------------------------------------------------------------------------
    //Clase para Almacenar los Usuarios
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Usuario user;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        //------------------------------------------------------------------------------------------
        //Hilo para validar si el Correo y contraseña ingresados son correctos

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            // Simulate network access.
            //Thread.sleep(2000);
            ServiceHandler serviceHandler = new ServiceHandler();
            String answer = serviceHandler.executePostAuth(mEmail,mPassword);

            if(answer.contains("false")||answer.contains("MalformedURLException")||answer.isEmpty()){
                return false;
            }else if(answer.contains("Connection error")){
                return null;
            }
            else{
                JSONArray JSONanswer = null;
                try {
                    JSONanswer = new JSONArray(answer);

                    user = Usuario.getUserInstance();
                    user.setToken(JSONanswer.getJSONObject(0).getString("id"));
                    System.out.println(Usuario.getUserInstance());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            /**Ciclo en el cual se comparan los Emails y Contraseñas alamacenados en el Array tipo
             * string definido al inicio del activity y el email y clave ingresados por el usuario
             * en el formulario de Login*/
            /*for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if(success!=null){
                if (success) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Welcome "+Usuario.getUserInstance().getname(),Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("id", user.getToken());
                    intent.putExtra("email", user.getemail());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Wrong email or password ",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    mEmailView.requestFocus();
                }
            }else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Connection to the server refused",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                mEmailView.requestFocus();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

