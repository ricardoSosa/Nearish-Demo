package com.enterprise.ij.nearish.Other;

import android.util.Base64;
import android.util.Log;

import com.enterprise.ij.nearish.Models.Usuario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by juan on 29/06/2017.
 */

public class ServiceHandler {
    private static  String targetAuthURL = "http://35.197.5.57:9000/users/credentials?";
    private static final String targetSignUpURL = "http://35.197.5.57:9000/users/";
    final String user = "admin";
    final String pass = "password123";

    public String executePostAuth(String email, String password){
        Usuario.getUserInstance().setemail(email);
        Usuario.getUserInstance().setpassword(password);
        int index = email.indexOf('@');
        final String username = email.substring(0,index);
        Usuario.getUserInstance().setname(username);
        StringBuffer response = new StringBuffer();
        InputStream is = null;
        Gson gson= new Gson();
        String user_pass_json = gson.toJson(Usuario.getUserInstance());
        targetAuthURL+="email="+Usuario.getUserInstance().getemail()+"&password="+Usuario.getUserInstance().getpassword();
        System.out.println(targetAuthURL);
        HttpURLConnection httpConnection = null;

        try{
            //Criando a conexão
            URL tagetUrl = new URL(targetAuthURL);
            httpConnection = (HttpURLConnection) tagetUrl.openConnection();

            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            String credentials = user + ":" + pass;
            String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");
            httpConnection.setRequestProperty("Authorization", "Basic "+credBase64);
            httpConnection.connect();


            //Enviando Request
           // OutputStream outputStream = httpConnection.getOutputStream();
            //outputStream.write(user_pass_json.getBytes());
            //outputStream.flush();

                if (httpConnection.getResponseCode() != 200) {
                    return ("Failed : HTTP error code : " + httpConnection.getResponseCode());
                }


            //Recebendo Response
             is = httpConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = rd.readLine()) != null) {
                response.append(line);
            }

            Log.d("serviceHandler", response.toString());
            is.close();
            rd.close();

        }catch (MalformedURLException |EOFException e) {
            e.printStackTrace();
            return "MalformedURLException";

        } catch (IOException e) {
            e.printStackTrace();
            return "Connection error"+httpConnection.getErrorStream ();
        } finally {

            if(httpConnection != null) {
                httpConnection.disconnect();
            }

            targetAuthURL = "http://35.197.5.57:9000/users/credentials?";

        }
           return response.toString();
    }





    public String executePostSignUp(String email, String password, String name){
        Usuario.getUserInstance().setemail(email);
        Usuario.getUserInstance().setpassword(password);
        int index = email.indexOf('@');
        final String username = email.substring(0,index);
        Usuario.getUserInstance().setname(username);
        StringBuffer response = new StringBuffer();
        Gson gson= new Gson();
        String user_pass_json = gson.toJson(Usuario.getUserInstance());
        System.out.println(Usuario.getUserInstance());

        HttpURLConnection httpConnection = null;
        try{
            //Criando a conexão
            URL tagetUrl = new URL(targetSignUpURL);
            httpConnection = (HttpURLConnection) tagetUrl.openConnection();

            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            String credentials = user + ":" + pass;
            String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");
            httpConnection.setRequestProperty("Authorization", "Basic "+credBase64);
            httpConnection.connect();


            //Enviando Request
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(user_pass_json.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 201){
                return ("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }

            //Recebendo Response
            InputStream is = httpConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            rd.close();
            return response.toString();

        }catch (MalformedURLException e) {
            e.printStackTrace();
            return "MalformedURLException";

        } catch (IOException e) {
            e.printStackTrace();
            return ""+httpConnection.getErrorStream ();
        } finally {

            if(httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }
}

