package jackrabbit.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String FILENAME = "groceryList";
    private EditText addTxt;
    private Button addBtn;
    private Spinner spinner_qty;

    private ListView list;

    private ArrayList<String> groceryList = new ArrayList<String>();

    private String addValue;
    private String qty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        read();

        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        addTxt = (EditText)findViewById(R.id.addView);

        // quantity selection
        spinner_qty = (Spinner)findViewById(R.id.spinner_quantity);
        spinner_qty.setOnItemSelectedListener(this);
        // add button
        addBtn = (Button)findViewById(R.id.addbtn);
        addBtn.setOnClickListener(this);

        // list view
        list = (ListView)findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_expandable_list_item_1, android.R.id.text1, groceryList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) list.getItemAtPosition(position);
                PopUp(adapter, itemValue, 0);

            }
        });
    }

    public void PopUp(final ArrayAdapter<String> adapter, final String itemVaule, final int itemPosition){
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Selected");

        // set Dialog message
        alertDialogBuilder.setMessage("Do you wish to delete " +itemVaule)
                .setCancelable(false)
                .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.remove(itemVaule);
                    }
                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {

        // add button
        if (v.equals(addBtn)){
            addValue = addTxt.getText().toString();

            // check to see if the addTxt is empty
            if (addValue != ""){
                if (qty == "1") {
                    groceryList.add(addValue);
                } else
                    groceryList.add(addValue + " x" + qty);

                // reset qty spinner fore easier use
                spinner_qty.setSelection(0);
            }
            else
                Toast.makeText(this, "Nothing to add", Toast.LENGTH_SHORT).show();

            addTxt.setText("");
        }
    }


    public void saveList() {

        FileOutputStream fOut = null;

        for (int i=0; i<groceryList.size(); i++){
            groceryList.set(i, groceryList.get(i)+ ",");
        }

        try {
             fOut = openFileOutput(FILENAME, MODE_WORLD_READABLE);
            for (String s: groceryList){
                fOut.write(s.getBytes());
            }
            fOut.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        Toast.makeText(this, "Saved file", Toast.LENGTH_SHORT).show();

    }

    public void read () {

        FileInputStream fin = null;
        int c;
        String temp = "";

        try {
            fin = openFileInput(FILENAME);
            while ((c = fin.read()) != -1){
                temp = temp + Character.toString((char) c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String[] str = temp.split(",,"); // split temp into an array

        for (int i=0; i<str.length; i++) {
            if (str[i] == "") {

            }else
                groceryList.add(str[i]);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        qty = String.valueOf(parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // nothing here
    }


    // automatically call when the activity has been paused or stopped
    @Override
    public void onPause(){
        super.onPause();
        saveList();
    }

    @Override
    public void onStop(){
        super.onStop();
        saveList();
    }
}
