package com.aurosaswat.oraclejdbcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private static final String DRIVER="oracle.jdbc.driver.OracleDriver";
    private static final String URL="jdbc:oracle:thin:@192.168.1.45:1521:XE";
    // jdbc:oracle:thin:@192.168.0.104:1521:XE
    private static final String USERNAME="CTXSYS";
    private static final String PASSWORD="CTXSYS";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        /**
         *
         * The code snippet you provided sets a permissive thread policy for the Android StrictMode. StrictMode is a developer tool in Android that helps identify potential performance issues and violations of best practices.
         *
         * In this specific code, a new `StrictMode.ThreadPolicy` object is created using the `Builder` class. The `Builder` allows you to configure various aspects of the thread policy. In this case, the `permitAll()` method is called, which sets the thread policy to allow all violations.
         * This means that all actions that would typically trigger a StrictMode violation, such as network operations on the main thread or disk operations on the UI thread, will be permitted.
         *
         * Once the thread policy is configured, it is set as the current policy by calling `StrictMode.setThreadPolicy(threadPolicy)`.
         * This ensures that the permissive thread policy takes effect and any subsequent code executed within the application will not trigger StrictMode violations related to thread usage.
         *
         * It's important to note that using a permissive thread policy like this should be done with caution, as it can potentially lead to performance issues or other unintended consequences.
         * It is generally recommended to use strict thread policies during development and testing to catch and fix any violations, and then use a more lenient policy in production environments.
         * */
    }

    public void buttonConnectToOraleDB(View view) {
        try {
            Class.forName(DRIVER);
            this.connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();
            Statement statement=connection.createStatement();
            StringBuilder stringBuffer=new StringBuilder();
            ResultSet resultSet=statement.executeQuery("select TABLE_NAME from cat");

            while(resultSet.next()){
                stringBuffer.append(resultSet.getString(1)+"\n");
            }
            textView.setText(stringBuffer.toString());
            connection.close();
        }catch (Exception e){
            textView.setText(e.toString());
        }
    }
}