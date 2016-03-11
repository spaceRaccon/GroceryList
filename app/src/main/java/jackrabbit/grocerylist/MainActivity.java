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

public class MainActivity extends AppCompatActivity {

    private EditText addTxt;
    private Button addBtn;
    private ListView list;

    String[] values = new String[] {"Apples", "Cookies", "Pizza"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTxt = (EditText)findViewById(R.id.addView);

        addBtn = (Button)findViewById(R.id.addbtn);

        list = (ListView)findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_expandable_list_item_1, android.R.id.text1, values);
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
}
