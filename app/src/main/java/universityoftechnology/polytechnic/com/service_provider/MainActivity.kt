package universityoftechnology.polytechnic.com.service_provider

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    var optionalUser : Spinner? = null
    var editTextMaNhaHang : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        optionalUser = findViewById(R.id.option_user)
        editTextMaNhaHang = findViewById(R.id.ma_nha_hang)

        optionalUser!!.setSelection(0, true)

        ArrayAdapter.createFromResource(
            this,
            R.array.optionl_user,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            optionalUser!!.adapter = adapter

        }

        optionalUser!!.onItemSelectedListener = this

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0){
            editTextMaNhaHang!!.visibility = View.VISIBLE
        }
        else{
            editTextMaNhaHang!!.visibility = View.GONE
        }
    }
}
