package jackrabbit.grocerylist;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText addTxt;
    private Button addBtn;
    private ListView list;

    private ArrayList<String> groceryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTxt = (EditText)findViewById(R.id.addView);

        addBtn = (Button)findViewById(R.id.addbtn);
        addBtn.setOnClickListener(this);

        list = (ListView)findViewById(R.id.list);

        // test
        groceryList.add("pizza");
        groceryList.add("apples");
        groceryList.add("soup");

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_expandable_list_item_1, android.R.id.text1, groceryList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String)list.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Position: " +itemPosition+ " ListItem: " +itemValue, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        String addValue;

        if (v.equals(addBtn)){
            addValue = addTxt.getText().toString();
            if (addValue != "")
                groceryList.add(addValue);
        }
    }
}
