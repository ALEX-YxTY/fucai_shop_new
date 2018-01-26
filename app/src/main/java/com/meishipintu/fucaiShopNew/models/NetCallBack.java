package com.meishipintu.fucaiShopNew.models;

public interface NetCallBack<T> {
	void onSuccess(T data);

	void onError(String error);
}
