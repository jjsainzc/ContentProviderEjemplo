package com.example.contentproviderejemplo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.contentproviderejemplo.utilidades.SQLite;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ContentProviderEjemplo extends Activity {
	private SQLite sqLiteUtil;

	private TextView resultadoV;

	private static final int VERSION_ = 1;
	private static final String BASEDATOS = "basedatos.db";

	private Set<String> tablas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider_ejemplo);

		tablas = new HashSet<String>();

		tablas.add("CREATE TABLE persona ("
				+ "persona_id Integer PRIMARY KEY AUTOINCREMENT, "
				+ "nombre Text," + "cedula Text," + "fechaNac DATE,"
				+ "estadoCivil Text," + "discapacitado Integer,"
				+ "estatura Double ); ");

		sqLiteUtil = new SQLite(getApplicationContext(), BASEDATOS, VERSION_,
				tablas);

		
		int cuantos = sqLiteUtil.count("persona");
		Log.i("COUNT", String.valueOf(cuantos));
		if (cuantos <= 0) {

			Map<String, Object> dato = new HashMap<String, Object>();

			dato.put("nombre", "Persona 1");
			dato.put("cedula", "1111111");
			dato.put("fechaNac", "1964-06-30");
			dato.put("estadoCivil", "casado");
			dato.put("discapacitado", 0);
			dato.put("estatura", 5.2f);

			sqLiteUtil.insert("persona", dato);

			dato.put("nombre", "Persona 2");
			dato.put("cedula", "22222222");
			dato.put("fechaNac", "1985-10-15");
			dato.put("estadoCivil", "soltero");
			dato.put("discapacitado", 1);
			dato.put("estatura", 5.4f);

			sqLiteUtil.insert("persona", dato);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_provider_ejemplo, menu);
		return true;
	}

}
