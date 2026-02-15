package net.JavierSG.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*


class MainActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var spType: Spinner
    private lateinit var tvTotal: TextView
    private lateinit var btnCalculate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa las vistas
        etAmount = findViewById(R.id.et_amount)
        spType = findViewById(R.id.sp_type)
        tvTotal = findViewById(R.id.tv_total)
        btnCalculate = findViewById(R.id.btn_calculate)

        // Configura el Spinner con las opciones de IVA
        val ivaTypes = listOf("16%", "10%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ivaTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spType.adapter = adapter

        // Configura el botón para calcular
        btnCalculate.setOnClickListener {
            calculateIVA()
        }
    }

    private fun calculateIVA() {
        val amountStr = etAmount.text.toString()
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa un monto válido", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(this, "Por favor ingresa un número válido", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val ivaType = spType.selectedItem.toString()
        val iva = when (ivaType) {
            "16%" -> amount * 0.16
            "10%" -> amount * 0.10
            else -> 0.0
        }

        val total = amount + iva
        tvTotal.text = String.format("$ %.2f", total)
    }
}