package mx.linkom.mtto_grupokap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;

import mx.linkom.mtto_grupokap.Controller.PagerControlador;

public class DashboardActivity extends  mx.linkom.mtto_grupokap.Menu {
    private FirebaseAuth fAuth;
    private mx.linkom.mtto_grupokap.Configuracion Conf;
    JSONArray ja1,ja2,ja3;
    TextView perma,sali,entr;
    TextView permaT,saliT,entrT;
    String var1,var2,var3,var4,var5;
    String var6,var7,var8,var9,var10;
    LinearLayout rlVistantes,rlTrabajadores;

    private GridView gridList,gridList2;
    TabLayout tablayout;
    TabItem tabRecibir,tabEstacionar,tabRecoger,tabEntrega;
    ViewPager viewPager;
    PagerControlador pagerAdapter;
    TextView Titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fAuth = FirebaseAuth.getInstance();
        Conf = new mx.linkom.mtto_grupokap.Configuracion(this);
         Titulo = (TextView) findViewById(R.id.titulo);
         Titulo.setText("Trabajador: "+Conf.getNomResi());
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        TabItem tabs1 = (TabItem) findViewById(R.id.tabDiarios);
        TabItem tabs2 = (TabItem) findViewById(R.id.tabProgramados);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerAdapter=new PagerControlador(getSupportFragmentManager(),tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    pagerAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==1){
                    pagerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));


    }
    @Override
    public void onStart() {
        super.onStart();

        Registro();
        Sesion();

    }



    public void Registro (){
        try {
            fAuth.createUserWithEmailAndPassword(mx.linkom.mtto_grupokap.Global.EMAIL, mx.linkom.mtto_grupokap.Global.PASS)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("TAG","Se ha registrado exitosamente");
                            } else {
                                Log.e("TAG","Ha fallado el registro " + task.getException());

                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("TAG","Error ",e);
        }
    }

    public void Sesion (){
        try {
            fAuth.signInWithEmailAndPassword(mx.linkom.mtto_grupokap.Global.EMAIL, mx.linkom.mtto_grupokap.Global.PASS)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("TAG","Se ha logeado exitosamente");
                            } else {
                                Log.e("TAG","Ha fallado la autenticación " + task.getException());
                            }
                        }
                    });
        }catch (Exception e) {
            Log.e("TAG","Error ",e);
        }
    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        finish();
    }


}