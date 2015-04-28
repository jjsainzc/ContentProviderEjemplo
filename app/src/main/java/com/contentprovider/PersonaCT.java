package com.contentprovider;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.contentproviderejemplo.utilidades.SQLite;

public class PersonaCT extends ContentProvider {

	// Definición del CONTENT_URI
	private static final String URI = "content://com.contentprovider/personas";
	public static final Uri CONTENT_URI = Uri.parse(URI);

	// UriMatcher
	private static final int PERSONAS = 1;
	private static final int PERSONA_ID = 2;
	private static final UriMatcher URIWATCHER;

	static {
		URIWATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URIWATCHER.addURI("com.contentprovider", "personas", PERSONAS);
		URIWATCHER.addURI("com.contentprovider", "personas/#", PERSONA_ID);
	}

	// Base de datos
	private static final int VERSION_ = 1;
	private static final String BASEDATOS = "basedatos.db";
	private Set<String> tablas;
	private SQLite sQLiteUtil;

	@Override
	public boolean onCreate() {
		tablas = new HashSet<String>();

		tablas.add("CREATE TABLE persona ("
				+ "persona_id Integer PRIMARY KEY AUTOINCREMENT, "
				+ "nombre Text," + "cedula Text," + "fechaNac DATE,"
				+ "estadoCivil Text," + "discapacitado Integer,"
				+ "estatura Double ); ");

		sQLiteUtil = new SQLite(getContext(), BASEDATOS, VERSION_, tablas);
		
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// Si es una consulta a un ID concreto construimos el WHERE
		String where = selection;
		if (URIWATCHER.match(uri) == PERSONA_ID) {
			where = "persona_id =" + uri.getLastPathSegment();
		}

		SQLiteDatabase db = sQLiteUtil.getWritableDatabase();

		Cursor c = db.query("persona", projection, where, selectionArgs, null,
				null, sortOrder);

		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int cont;

		// Si es una consulta a un ID concreto construimos el WHERE
		String where = selection;
		if (URIWATCHER.match(uri) == PERSONA_ID) {
			where = "persona_id =" + uri.getLastPathSegment();
		}

		SQLiteDatabase db = sQLiteUtil.getWritableDatabase();

		cont = db.update("persona", values, where, selectionArgs);

		return cont;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int cont;

		// Si es una consulta a un ID concreto construimos el WHERE
		String where = selection;
		if (URIWATCHER.match(uri) == PERSONA_ID) {
			where = "persona_id =" + uri.getLastPathSegment();
		}

		SQLiteDatabase db = sQLiteUtil.getWritableDatabase();
		cont = db.delete("persona", where, selectionArgs);

		return cont;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long regId = 1;

		SQLiteDatabase db = sQLiteUtil.getWritableDatabase();
		regId = db.insert("persona", null, values);
		Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

		return newUri;
	}

	@Override
	public String getType(Uri uri) {
		int match = URIWATCHER.match(uri);

		switch (match) {
		case PERSONAS:
			return "vnd.android.cursor.dir/vnd.content.persona";
		case PERSONA_ID:
			return "vnd.android.cursor.item/vnd.content.persona";
		default:
			return null;
		}
	}

}
