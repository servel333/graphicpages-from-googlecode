//package org.nateperry.graphicpages.util;
//
//import java.util.Vector;
//
//public class ListenableManager<T> {
//
//	private Vector<T> _listeners;
//
//	public ListenableManager() {
//		_listeners = new Vector<T>();
//	}
//
//	public void add(T listener) {
//		_listeners.add(listener);
//	}
//
//	public void remove(T listener) {
//		_listeners.remove(listener);
//	}
//
//	public void EventOccured(String name, Object... args) {
//		for (T listener: _listeners) {
//			invoke(name, args);
//		}
//	}
//
//}
