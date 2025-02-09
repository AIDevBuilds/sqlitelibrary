package com.sandeepsingh91;

public final class DBCodes {

	public static final int EQUALS = 0;

	public static final int GREATER = 1;

	public static final int GEQUALS = 2;

	public static final int LESS = 3;

	public static final int LEQUALS = 4;

	public static final int ASEC = 5;

	public static final int DESC = 6;

	protected static String getOperator(int operator) {

		if (operator == EQUALS) {
			return "=";
		} else if (operator == GREATER) {
			return ">";
		} else if (operator == LESS) {
			return "<";
		} else if (operator == LEQUALS) {
			return "<=";
		} else if (operator == GEQUALS) {
			return ">=";
		} else {
			return "=";
		}

	}
}
