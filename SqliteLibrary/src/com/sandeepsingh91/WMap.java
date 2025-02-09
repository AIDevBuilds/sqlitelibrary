package com.sandeepsingh91;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;

public final class WMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	transient static String[] mKeys;
	transient static String[] mValues;
	transient static Integer[] mOperators;

	transient int size;

	public WMap() {

	}

	public void put(String key, String value, Integer operator) {

		mKeys = ArrayUtils.add(mKeys, key);
		mValues = ArrayUtils.add(mValues, value);
		mOperators = ArrayUtils.add(mOperators, operator);

		size++;

	}

	public String getKey(int index) {

		return mKeys[index];
	}

	public String getValue(String key) {

		int index = ArrayUtils.indexOf(mKeys, key);

		return mValues[index];
	}

	public String getOperator(String key) {

		int index = ArrayUtils.indexOf(mKeys, key);

		return DBCodes.getOperator(mOperators[index]);

	}

	public int size() {
		return size;
	}

	public void remove(String key) {

		int index = ArrayUtils.indexOf(mKeys, key);

		mKeys = ArrayUtils.remove(mKeys, index);
		mValues = ArrayUtils.remove(mValues, index);
		mOperators = ArrayUtils.remove(mOperators, index);

		size++;
	}

	public void clean() {

		mKeys = ArrayUtils.nullToEmpty(mKeys);
		mValues = ArrayUtils.nullToEmpty(mValues);
		mOperators = ArrayUtils.nullToEmpty(mOperators);

		size++;
	}
}
