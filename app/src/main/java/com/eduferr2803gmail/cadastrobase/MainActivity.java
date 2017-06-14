package com.eduferr2803gmail.cadastrobase;

import android.app.Service;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editCodigo, editNome, editTelefone, editCelular, editEmail;
    Button btnLimpar, btnSalvar, btnExcluir;
    ListView listViewCliente;

    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adpater;
    ArrayList<String> arrayList;

    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNome = (EditText) findViewById(R.id.editNome);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        editCelular = (EditText) findViewById(R.id.editCelular);
        editEmail = (EditText) findViewById(R.id.editEmail);

        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);


        listViewCliente = (ListView) findViewById(R.id.listViewCliente);
        listarClientes();

        listViewCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                String conteudo = (String) listViewCliente.getItemAtPosition(position);
//                Toast.makeText(MainActivity.this, "Select: " + conteudo, Toast.LENGTH_SHORT).show();

                String codigo = conteudo.substring(0, conteudo.indexOf("-"));

                Cliente cliente = db.selecionar_Cliente(Integer.parseInt(codigo));

                editCodigo.setText(String.valueOf(cliente.getCodigo()));
                editNome.setText(cliente.getNome());
                editTelefone.setText(cliente.getTelefone());
                editCelular.setText(cliente.getCelular());
                editEmail.setText(cliente.getEmail());
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpacampos();
                escondeTeclado();

            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo = editCodigo.getText().toString();
                String nome = editNome.getText().toString();
                String telefone = editTelefone.getText().toString();
                String celular = editCelular.getText().toString();
                String email = editEmail.getText().toString();

                if (nome.isEmpty()) {
                    editNome.setError("Este Campo é obrigatório");
                }
                if (telefone.isEmpty()) {
                    editTelefone.setError("Este Campo é obrigatório");
                }
                if (celular.isEmpty()) {
                    editCelular.setError("Este Campo é obrigatório");
                }
                if (email.isEmpty()) {
                    editEmail.setError("Este Campo é obrigatório");
                } else {
                    if (codigo.isEmpty()) {
                        //insert
                        db.add_cliente(new Cliente(nome, telefone, celular, email));
                        Toast.makeText(MainActivity.this, "Cliente adicionado", Toast.LENGTH_SHORT).show();
                        limpacampos();
                        listarClientes();
                        escondeTeclado();
                    } else {
                        //update
                        db.atualiza_Cliente(new Cliente(Integer.parseInt(codigo), nome, telefone, celular, email));
                        Toast.makeText(MainActivity.this, "Cliente atualizado", Toast.LENGTH_SHORT).show();
                        limpacampos();
                        listarClientes();
                        escondeTeclado();


                    }
                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo = editCodigo.getText().toString();

                if (codigo.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nenhum Cliente selecionado", Toast.LENGTH_LONG).show();
                } else {
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(Integer.parseInt(codigo));
                    db.apagar_Cliente(cliente);
                    Toast.makeText(MainActivity.this, "Cliente apagado", Toast.LENGTH_SHORT).show();

                    limpacampos();
                    listarClientes();
                    escondeTeclado();
                }

            }
        });

        /*TESTE DO CRUD*/
        //insert
//      db.add_cliente(new Cliente("EduFerr","6187654321","61987654321","teste@teste.com"));

        //delete
//        Cliente cliente = new Cliente();
//        cliente.setCodigo(1);
//        db.apagar_Cliente(cliente);

//        Toast.makeText(MainActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();

        //Seleciona Cliente especifico
//        Cliente cliente = db.selecionar_Cliente(1);
//        Log.d("Cliente Selecionado" , "Codigo: " + cliente.getCodigo()
//                                    + "Nome: " + cliente.getNome()
//                                    + "Telefone: " + cliente.getTelefone()
//                                    + "Celular: " + cliente.getCelular()
//                                    + "Email: " + cliente.getEmail());
        //Atualiza Cliente especifico
//        Cliente cliente = new Cliente();
//        cliente.setCodigo(1);
//        cliente.setNome("Eduardo");
//        cliente.setTelefone("6166667777");
//        cliente.setCelular("61988881111");
//        cliente.setEmail("fora@fora.com");
//
//        db.atualiza_Cliente(cliente);
        //        Toast.makeText(MainActivity.this, "Atualizado com sucesso", Toast.LENGTH_LONG).show();

    }

    void escondeTeclado() {
        imm.hideSoftInputFromWindow(editNome.getWindowToken(), 0);
    }

    void limpacampos() {
        editCodigo.setText("");
        editNome.setText("");
        editTelefone.setText("");
        editCelular.setText("");
        editEmail.setText("");

        editNome.requestFocus();
    }

    public void listarClientes() {

        List<Cliente> clientes = db.listaTodosClientes();

        arrayList = new ArrayList<String>();

        adpater = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        listViewCliente.setAdapter(adpater);

        for (Cliente c : clientes) {
            //Log.d("Lista" , "\nID: " + c.getCodigo() + " Nome: " + c.getNome());
            arrayList.add(c.getCodigo() + "-" + c.getNome());
            adpater.notifyDataSetChanged();
        }
    }
}
