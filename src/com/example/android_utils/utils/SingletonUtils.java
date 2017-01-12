package com.example.android_utils.utils;

/**
 * Singleton helper class for lazily initialization. 单例帮助类
 * 
 * @author <a href="http://www.trinea.cn/" target="_blank">Trinea</a>
 * 
 * @param <T>
 */
public abstract class SingletonUtils<T> {

	private T instance;

	protected abstract T newInstance();

	public final T getInstance() {
		if (instance == null) {
			synchronized (SingletonUtils.class) {
				if (instance == null) {
					instance = newInstance();
				}
			}
		}
		return instance;
	}
}
