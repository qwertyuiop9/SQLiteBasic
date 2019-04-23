package com.alssimpleapps.sqlitebasic;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alssimpleapps.sqlitebasic.SQLite.DatabaseManager;
import com.alssimpleapps.sqlitebasic.SQLite.DatabaseStrings;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Database resources
    private DatabaseManager dbManager;
    private CursorAdapter adapter;

    // GUI resources
    private EditText etName, etSurname;
    private ListView listview;
    private Button btnAdd;

    // Other resources
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (dbManager.deleteUser((int) id))
                adapter.changeCursor(dbManager.getTableRecords(DatabaseStrings.TABLE_NAME));
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link XML - Java
        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_surname);
        btnAdd = findViewById(R.id.btn_add);
        listview = findViewById(R.id.list_view);

        // onClickListener
        btnAdd.setOnClickListener(this);

        // initializing Database
        dbManager = new DatabaseManager(this);
        Cursor cursor = dbManager.getTableRecords(DatabaseStrings.TABLE_NAME);
        adapter= new CursorAdapter(this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View v = getLayoutInflater().inflate(R.layout.user_row, null);
                return v;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                String name = cursor.getString(cursor.getColumnIndex(DatabaseStrings.FIELD_NAME));
                String surname = cursor.getString(cursor.getColumnIndex(DatabaseStrings.FIELD_SURNAME));

                TextView tvRowName = view.findViewById(R.id.tv_row_name);
                TextView tvRowSurname = view.findViewById(R.id.tv_row_surname);
                ImageButton btnDelete = view.findViewById(R.id.btn_row_delete);

                tvRowName.setText(name);
                tvRowSurname.setText(surname);
                btnDelete.setOnClickListener(onClickListener);

            }
        };

        listview.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_add:
                salva(v);
                break;

            case R.id.list_view:
                int position = listview.getPositionForView(v);
                long id = adapter.getItemId(position);
                break;

        }

    }

    public void salva(View v) {
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        if (name.length() != 0 && surname.length() != 0) {
            dbManager.addUser(name, surname, "");
            adapter.changeCursor(dbManager.getTableRecords(DatabaseStrings.TABLE_NAME));
        }
    }
}
