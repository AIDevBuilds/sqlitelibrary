package com.sandeepsingh91.callback;

import java.util.ArrayList;

public interface Callback<T> {

	public void onSuccess(ArrayList<T> arrayList);
}
