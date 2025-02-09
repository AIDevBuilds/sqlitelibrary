package com.sandeepsingh91.db;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sandeepsingh91.Column;
import com.sandeepsingh91.Lo;
import com.sandeepsingh91.TableName;
import com.sandeepsingh91.WMap;
import com.sandeepsingh91.callback.Callback;

@SuppressWarnings("rawtypes")
public class Database<T> {

	private static Database mDatabase;

	private String mSelect, mWhere, mOrderBy;

	private static List<Field> mList;

	private static Callback mCallback = null;

	private static DataBaseHelper mDataBaseHelper;

	private ContentValues mContentValues;

	private Database() {

	}

	public static Database getSingleInstance(Context i_context) {

		if (mDatabase == null) {

			synchronized (Database.class) {

				if (mDatabase == null) {

					mDataBaseHelper = new DataBaseHelper(i_context);

					mDatabase = new Database();

					try {
						mDataBaseHelper.createDataBase();

					} catch (IOException ioe) {

						throw new Error("Unable to create database");

					}
				}
			}
		}

		return mDatabase;
	}

	// public static SQLiteQuery getInstance(Context context, String dataDir,
	// String dbName) {
	//
	// if (mDatabase == null) {
	//
	// synchronized (SQLiteQuery.class) {
	//
	// if (mDatabase == null) {
	// mDatabase = new SQLiteQuery();
	// }
	// }
	// }
	//
	// mDataBaseHelper = new DataBaseHelper(context);
	//
	// try {
	// mDataBaseHelper.createDataBase();
	//
	// } catch (IOException ioe) {
	// ioe.printStackTrace();
	// throw new Error("Unable to create database");
	// }
	//
	// return mDatabase;
	// }

	@SuppressWarnings("unchecked")
	public Database init() {

		mList = new LinkedList<Field>();

		mSelect = "";
		mWhere = "";
		mOrderBy = "";

		mContentValues = new ContentValues();

		return mDatabase;
	}

	public Database select() {

		mSelect = "SELECT * from ";

		return mDatabase;
	}

	public Database select(String[] columnNames) {

		mSelect = "SELECT ";

		String data = StringUtils.replaceEachRepeatedly(ArrayUtils.toString(columnNames), new String[] { "{", "}" },
				new String[] { "", "" });

		mSelect += data + " from ";

		return mDatabase;
	}

	public Database select(String[] columnNames, boolean isDistinct) {

		if (isDistinct) {
			mSelect = "SELECT DISTINCT ";
		} else {
			mSelect = "SELECT ";
		}

		String data = StringUtils.replaceEachRepeatedly(ArrayUtils.toString(columnNames), new String[] { "{", "}" },
				new String[] { "", "" });

		mSelect += data + " from ";

		return mDatabase;
	}

	public Database where(WMap whereMap) {

		mWhere += " ";

		for (int i = 0; i < whereMap.size(); i++) {

			if (i == whereMap.size() - 1) {

				mWhere += whereMap.getKey(i) + whereMap.getOperator(whereMap.getKey(i))
						+ whereMap.getValue(whereMap.getKey(i));

			} else {

				mWhere += (String) whereMap.getKey(i) + whereMap.getOperator(whereMap.getKey(i))
						+ whereMap.getValue(whereMap.getKey(i)) + " AND ";
			}

		}

		return mDatabase;
	}

	public Database orderBY(String columnNames, String order) {

		mOrderBy += " ORDER BY " + columnNames + " " + order;

		return mDatabase;
	}

	public void execute(Class<?> activity, Callback callback) {

		String tablename = activity.getAnnotation(TableName.class).value();

		if (StringUtils.isNotBlank(tablename)) {

			String query = mSelect + tablename + mWhere + mOrderBy;

			execute(query, activity, callback);

		} else {
			Lo.e("invaild table name");
		}

	}

	public void execute(String query, Class<?> activity, Callback callback) {

		mCallback = callback;

		SQLiteDatabase sqld = mDataBaseHelper.getWritableDatabase();

		sqld.beginTransaction();

		Cursor cursor = sqld.rawQuery(query, null);

		ArrayList arrayList = fetchAndRetrun(activity, cursor);

		sqld.setTransactionSuccessful();
		sqld.endTransaction();
		sqld.close();

		if (mCallback != null) {

			mCallback.onSuccess(arrayList);
		}

	}

	public Database insert(String[] columns, String[] values) {

		if (ArrayUtils.isSameLength(columns, values)) {

			for (int i = 0; i < values.length; i++) {
				mContentValues.put(columns[i], values[i]);
			}

		} else {
			Lo.e("size of array not match");
		}

		return mDatabase;
	}

	public long executeInsert(Class<?> activity, Callback callback) {

		long insertID = 0;

		String tablename = activity.getAnnotation(TableName.class).value();

		if (StringUtils.isNotBlank(tablename)) {

			SQLiteDatabase sqld = mDataBaseHelper.getWritableDatabase();

			sqld.beginTransaction();

			try {
				insertID = sqld.insertOrThrow(tablename, null, mContentValues);
			} catch (SQLException e) {
				Lo.e(e.getMessage());
			}

			sqld.setTransactionSuccessful();
			sqld.endTransaction();
			sqld.close();

		} else {
			Lo.e("invaild table name");
		}

		return insertID;

	}

	public int executeUpdate(T object, WMap whereMap, Callback callback) {

		int insertID = 0;

		String tablename = object.getClass().getAnnotation(TableName.class).value();

		if (StringUtils.isNotBlank(tablename)) {

			for (Field field : object.getClass().getDeclaredFields()) {

				field.setAccessible(true);

				checkIfColumn(field);
			}

			ContentValues contentValues = new ContentValues();

			try {

				for (Field field : mList) {

					String columnName = field.getAnnotation(Column.class).value();

					if (!StringUtils.isNotBlank(columnName)) {

						columnName = field.getName();
					}

					String fieldType = field.getType().toString();

					if (fieldType.contains("String")) {

						contentValues.put(columnName, (String) field.get(object));

					} else if (fieldType.contains("int") || fieldType.contains("Integer")) {

						contentValues.put(columnName, field.getInt(object));

					} else if (fieldType.contains("float") || fieldType.contains("Float")) {

						contentValues.put(columnName, field.getFloat(object));

					} else if (fieldType.contains("long") || fieldType.contains("Long")) {

						contentValues.put(columnName, field.getLong(object));

					}

				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			where(whereMap);

			SQLiteDatabase sqld = mDataBaseHelper.getWritableDatabase();

			sqld.beginTransaction();

			try {
				insertID = sqld.update(tablename, contentValues, mWhere, null);
			} catch (SQLException e) {
				Lo.e(e.getMessage());
			}

			sqld.setTransactionSuccessful();
			sqld.endTransaction();
			sqld.close();

		} else {
			Lo.e("invaild table name");
		}

		return insertID;
	}

	public void executeDelete(Class<?> activity, Callback callback) {

		String tablename = activity.getAnnotation(TableName.class).value();

		if (StringUtils.isNotBlank(tablename)) {

			String query = "DELETE FROM " + tablename + " WHERE " + mWhere;

			execute(query, activity, callback);

		} else {
			Lo.e("invaild table name");
		}
	}

	private void checkIfColumn(Field field) {

		if (field.isAnnotationPresent(Column.class)) {
			mList.add(field);
		}
	}

	private ArrayList fetchAndRetrun(Class<?> activity, Cursor cursor) {

		ArrayList<T> arrayList = new ArrayList<T>();

		for (Field field : activity.getDeclaredFields()) {

			field.setAccessible(true);
			// mList.add(field);
			checkIfColumn(field);
		}

		try {

			Method[] meth = activity.getMethods();

			if (cursor.moveToFirst()) {

				do {

					T obj = null;

					obj = (T) activity.newInstance();

					for (Field field : mList) {

						String columnName = field.getAnnotation(Column.class).value();

						if (!StringUtils.isNotBlank(columnName)) {

							columnName = field.getName();
						}

						String fieldType = field.getType().toString();

						String methodName = "set" + StringUtils.capitalize(field.getName());

						Method method2 = null;

						try {
							method2 = activity.getMethod(methodName, field.getType());
						} catch (NoSuchMethodException e) {

						}

						if (fieldType.contains("String")) {

							String value = cursor.getString(cursor.getColumnIndex(columnName));

							method2.invoke(obj, new Object[] { value });

						} else if (fieldType.contains("int") || fieldType.contains("Integer")) {

							int value = cursor.getInt(cursor.getColumnIndex(columnName));

							method2.invoke(obj, new Object[] { value });

						} else if (fieldType.contains("float") || fieldType.contains("Float")) {

							float value = cursor.getFloat(cursor.getColumnIndex(columnName));

							method2.invoke(obj, new Object[] { value });

						} else if (fieldType.contains("long") || fieldType.contains("Long")) {

							long value = cursor.getLong(cursor.getColumnIndex(columnName));

							method2.invoke(obj, new Object[] { value });

						}

					}
					arrayList.add(obj);
				} while (cursor.moveToNext());
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return arrayList;

	}
}
