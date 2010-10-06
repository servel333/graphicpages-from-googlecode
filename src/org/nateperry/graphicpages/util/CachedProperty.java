package org.nateperry.graphicpages.util;

import java.util.Calendar;

public class CachedProperty<T> {

	protected int _timeout;
	protected T _cachedValue;
	protected Calendar _lastUpdate;
	protected Updater<T> _updater;

	public static final int DEFAULT_TIMEOUT = 30 * 60; // in seconds

	public CachedProperty(
			Updater<T> updater) { 

		_timeout = DEFAULT_TIMEOUT;
		_updater = updater;
	}

	public CachedProperty(
			Updater<T> updater,
			int timeout) {

		_timeout = timeout;
		_updater = updater;
	}

	public CachedProperty(
			Updater<T> updater,
			int timeout,
			T initialValue) {

		_timeout = timeout;
		_updater = updater;

		setCachedProperty(initialValue);
	}

	public CachedProperty(
			CachedProperty<T> value) {

		_timeout = value._timeout;
		_updater = value._updater;
		_lastUpdate = value._lastUpdate;
		_cachedValue = value._cachedValue;
	}

	public T getProperty() {
		if (_lastUpdate == null || isExpired()) {
			updateCachedValue();
		}

		return _cachedValue;
	}

	public T getCachedProperty() {
		return _cachedValue;
	}

	public T getUpdatedProperty() {
		updateCachedValue();
		return _cachedValue;
	}

	public void setCachedProperty(T value) {

		_lastUpdate = Calendar.getInstance();
		_cachedValue = value;
	}

	public int getTimeout() {
		return _timeout;
	}

	public void setTimeout(int timeout) {
		_timeout = timeout;
	}

	public boolean isExpired() {

		Calendar timeout = Calendar.getInstance();
		timeout.add(Calendar.SECOND, _timeout);

		return _lastUpdate.after(timeout);
	}

	protected void updateCachedValue() {

		_lastUpdate = Calendar.getInstance();
		_cachedValue = _updater.updateProperty();
	}

	public interface Updater<T> {

		public abstract T updateProperty();

	}

}
