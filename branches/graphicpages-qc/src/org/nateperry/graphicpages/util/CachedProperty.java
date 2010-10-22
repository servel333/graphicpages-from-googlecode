package org.nateperry.graphicpages.util;

import java.util.Calendar;

public class CachedProperty<T> {

	protected T _cachedValue;
	protected Calendar _lastUpdate;
	protected int _timeout;
	protected final IUpdater<T> _updater;

	public static final int DEFAULT_TIMEOUT = 30 * 60; // in seconds

	public CachedProperty(
			IUpdater<T> updater) { 

		_timeout = DEFAULT_TIMEOUT;
		_updater = updater;
		_lastUpdate = null;

	}

	public CachedProperty(
			IUpdater<T> updater,
			int timeout) {

		_timeout = timeout;
		_updater = updater;
		_lastUpdate = null;

	}

	public CachedProperty(
			IUpdater<T> updater,
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

		if (null == _lastUpdate || isExpired()) {
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
		setCachedProperty(value, true);
	}

	public void setCachedProperty(T value, boolean restartTimeout) {

		if (restartTimeout) {
			_lastUpdate = Calendar.getInstance();
		}
		_cachedValue = value;
	}

	public int getTimeout() {
		return _timeout;
	}

	public void setTimeout(int timeout) {
		_timeout = timeout;
	}

	public boolean isExpired() {

		boolean expired;

		if (null == _lastUpdate) {

			expired = true;

		} else {

			Calendar timeout = Calendar.getInstance();
			timeout.add(Calendar.SECOND, _timeout);
			expired = _lastUpdate.after(timeout);

		}

		return expired;
	}

	public void updateCachedValue() {

		_lastUpdate = Calendar.getInstance();
		_cachedValue = _updater.updateProperty();
	}

	public interface IUpdater<T> {

		public abstract T updateProperty();

	}

}
