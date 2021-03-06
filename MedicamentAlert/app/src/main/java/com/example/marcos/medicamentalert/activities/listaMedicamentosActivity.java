package com.example.marcos.medicamentalert.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.marcos.medicamentalert.adapters.ListAdapter;
import com.example.marcos.medicamentalert.fragments.MainFragment;
import com.example.marcos.medicamentalert.R;
import com.example.marcos.medicamentalert.bancoDados.Banco;
import com.example.marcos.medicamentalert.models.Medicamento;

/**
 * Created by Marcos on 22/07/2017.
 */

public class listaMedicamentosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static Banco bd;
    public static ListAdapter adapter;
    private static RecyclerView mRecyclerView;
    private NavigationView navigationView = null;
    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_list);
        //navegation viewr menu

        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabb);
        //fab2.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //  public void onClick(View view) {
        //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //         .setAction("Action", null).show();
        // }
        //});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        //How to change elements in the header programatically
        View headerView = navigationView.getHeaderView(0);


        navigationView.setNavigationItemSelectedListener(this);

        //fim navegation viewr
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Medicamentos");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        */

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_layour_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        bd = new Banco(this);
        adapter = new ListAdapter(bd.getMedicamentosNoBanco());
        mRecyclerView.setAdapter(adapter);
       // Toast.makeText(this, bd.getMedicamentosNoBanco().get(0).getData(), Toast.LENGTH_SHORT).show();
        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(cadastraMedicamentoOnClickListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Medicamentos) {
            Intent in=new Intent(getBaseContext(), com.example.marcos.medicamentalert.activities.listaMedicamentosActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_relatorio_semanal) {
            Intent in=new Intent(getBaseContext(),RelatorioSemanalActivity.class);
            startActivity(in);


        } else if (id == R.id.nav_relatorio_diario) {
            Intent in=new Intent(getBaseContext(),relatorioDiarioActivity.class);
            startActivity(in);

        } else if (id == R.id.nav_consultas) {
            Intent in=new Intent(getBaseContext(),ListagemConsultas.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private View.OnClickListener cadastraMedicamentoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroMedicamentosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    };
    //SQLiteDatabase db = MainActivity.bd.getWritableDatabase();
    //Cursor cursor = db.rawQuery("SELECT * from tabela_medicamentos", null);
    //ListCursorAdapter adapter = new ListCursorAdapter(this, cursor);
    //listaDeMedicamentos.setAdapter(adapter);

    public static void medicamentoTomado(String horario, int id){

        bd.atualizaTabelaHorario(horario, id);
        adapter = new ListAdapter(bd.getMedicamentosNoBanco());
        mRecyclerView.setAdapter(adapter);
        Log.i("Alarme", "medicamento tomado");
    }
}