package com.eduferr2803gmail.cadastrobase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduferr on 08/06/2017.
 */

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_CLIENTE = "bd_clientes";

    //Tabela do banco
    private static final String TABELA_CLIENTE = "tb_clientes";
    //Colunas da tabela
    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_TELEFONE = "telefone";
    private static final String COLUNA_CELULAR = "celular";
    private static final String COLUNA_EMAIL = "email";


    public BancoDados(Context context) {
        super(context, BANCO_CLIENTE, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_COLUNA = "CREATE TABLE " + TABELA_CLIENTE + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT, "
                + COLUNA_TELEFONE + " TEXT, " + COLUNA_CELULAR + " TEXT, "
                + COLUNA_EMAIL + " TEXT)";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*====================== CRUD ======================*/

    //Adiciona contato
    void add_cliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_CELULAR, cliente.getCelular());
        values.put(COLUNA_EMAIL, cliente.getEmail());

        db.insert(TABELA_CLIENTE, null, values);
        db.close();
    }

    //Apaga contato
    void apagar_Cliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();
        //convertendo int para String
        db.delete(TABELA_CLIENTE, COLUNA_CODIGO + " = ? ", new String[]{String.valueOf(cliente.getCodigo())});

        db.close();
    }

    //seleciona contato
    Cliente selecionar_Cliente(int codigo) {
        //lendo banco de dados
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_CLIENTE, new String[]{COLUNA_CODIGO,
        COLUNA_NOME, COLUNA_TELEFONE, COLUNA_CELULAR, COLUNA_EMAIL},COLUNA_CODIGO + " = ?",
                new String[]{String.valueOf(codigo)},null, null, null, null);

        //Verificar se o cursor ficou nulo
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Cliente cliente1 = new Cliente(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return cliente1;

    }

    ///atualiza contato
    void atualiza_Cliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_CELULAR, cliente.getCelular());
        values.put(COLUNA_EMAIL, cliente.getEmail());

        db.update(TABELA_CLIENTE, values, COLUNA_CODIGO + " = ?",
                new String[]{String.valueOf(cliente.getCodigo())
                });
    }

    public List<Cliente> listaTodosClientes() {

        List<Cliente> listaClientes = new ArrayList<Cliente>();

        String query = "SELECT * FROM " + TABELA_CLIENTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c  = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                Cliente cliente = new Cliente();
                cliente.setCodigo(Integer.parseInt(c.getString(0)));
                cliente.setNome(c.getString(1));
                cliente.setTelefone(c.getString(2));
                cliente.setCelular(c.getString(3));
                cliente.setEmail(c.getString(4));

                listaClientes.add(cliente);
            } while (c.moveToNext());

        }

        return listaClientes;

    }
}











