package com.aula.consultorio.consultorio

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.aula.consultorio.R
import com.aula.consultorio.db.Contato
import com.aula.consultorio.db.ContatoRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var contatos: ArrayList<Contato>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)

        //val contatos = arrayOf("Segunda-Feira","Terça-Feira","Quarta-Feira","Quinta-Feira", "Sexta-Feira")
        //Ativa Lista de de Pessoas.
        //val contatos = ContatoRepository(this).findAll()
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contatos)
        //lista.adapter = adapter


        lista.setOnItemClickListener { _, _, position, _ ->
        //val  nomeContato = contatos[position]
        //val intent = Intent(this@MainActivity, MainActivity::class.java)
        //  startActivity(intent)
        // Toast.makeText(this, "Clicked item : $position", Toast.LENGTH_SHORT).show()
           // Log.i(APP_NAME, "position: $position id: " + contatos?.get(id.toInt())?.id)
            val intent = Intent(this@MainActivity, ContatoActivity::class.java)
            intent.putExtra("contato", contatos?.get(position))
            startActivity(intent)
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.novo -> {
                val intent = Intent(this, ContatoActivity::class.java)
                startActivity(intent)
                return false
            }

            R.id.sincronizar -> {
                Toast.makeText(this, "Enviar", Toast.LENGTH_LONG).show()
                return false
            }

            R.id.receber -> {
                Toast.makeText(this, "Receber", Toast.LENGTH_LONG).show()
                return false
            }

            R.id.mapa -> {
                Toast.makeText(this, "Mapa", Toast.LENGTH_LONG).show()
                return false
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        contatos = ContatoRepository(this).findAll()
        if (contatos != null) {
            lista.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, contatos!!
            )
        }
    }
}