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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface {

    private EditText addTxt;
    private Button addBtn;
    private ListView list;

    private ArrayList<String> groceryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        addTxt = (EditText)findViewById(R.id.addView);

        addBtn = (Button)findViewById(R.id.addbtn);
        addBtn.setOnClickListener(this);

        list = (ListView)findViewById(R.id.list);

        // test
        groceryList.add("pizza");
        groceryList.add("apples");
        groceryList.add("soup");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_expandable_list_item_1, android.R.id.text1, groceryList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) list.getItemAtPosition(position);
                PopUp(adapter, itemValue, itemPosition);

            }
        });
    }

    public void PopUp(final ArrayAdapter<String> adapter, final String itemVaule, final int itemPosition){
        AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Selected");

        // set Dialog message
        alertDialogBuilder.setMessage("Do you wish to delete " +itemVaule+ ": " +itemPosition)
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

        String addValue;

        // add button
        if (v.equals(addBtn)){
            addValue = addTxt.getText().toString();
            // check to see if the addTxt is empty
            if (addValue != null){
                groceryList.add(addValue);
                Toast.makeText(this, addValue+ " added", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Nothing to add", Toast.LENGTH_SHORT).show();

            addTxt.setText("");
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }
}
