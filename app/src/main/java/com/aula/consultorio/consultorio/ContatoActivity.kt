package com.aula.consultorio.consultorio

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aula.consultorio.R
import com.aula.consultorio.db.Contato
import com.aula.consultorio.db.ContatoRepository
import kotlinx.android.synthetic.main.activity_contato.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class ContatoActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    private val myFormat = "dd/MM/yyyy : HH:mm:ss" // mention the format you need
    private val sdf = SimpleDateFormat(myFormat, Locale.US)
    private var contato: Contato? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        setSupportActionBar(toolbar_child)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        txtDatanascimento.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@ContatoActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }


        })

        btnCadastro.setOnClickListener{

            contato?.nome = txtNome.text?.toString()
            contato?.endereco = txtEndereco.text?.toString()
            contato?.email = txtEmail.text?.toString()
            contato?.telefone = txtTelefone.text?.toString()?.toLong()
            contato?.dataNascimento = cal.timeInMillis
            contato?.site = txtSite.text?.toString()

            if(contato?.id == 0L){
                ContatoRepository(this).create(contato!!)
            }else{
                ContatoRepository(this).update(contato!!)
            }


            //var dados = "[${txtNome.text} : ${txtEndereco.text} : ${txtEmail.text} : ${txtTelefone.text} : ${txtSite.text} : ${txtDatanascimento.text}]"
            Toast.makeText(this, "Contato Incluido Com Sucesso", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun updateDateInView() {

        txtDatanascimento.text = sdf.format(cal.getTime())
    }

    override fun onResume() {
        super.onResume()

        contato = intent?.getSerializableExtra("contato") as Contato?

        if(contato != null){
            contato as Contato
            txtNome.setText(contato?.nome)
            txtEmail.setText(contato?.email)
            txtTelefone.setText(contato?.telefone?.toString())
            txtEndereco.setText(contato?.endereco)
            txtSite.setText(contato?.site)

            if(contato?.dataNascimento != null){
                txtDatanascimento.setText(sdf.format(contato?.dataNascimento))
            }else{
                txtDatanascimento.setText(sdf.format(Date()))
            }


        }else{
            contato = Contato()
        }

    }

}