package org.nateperry.graphicpages.single.questionablecontent;

import java.io.File;
import java.net.URI;

public class WrappedFile extends File {

	private static final long serialVersionUID = 8340936134764925864L;

	public WrappedFile(File dir, String name) {
		super(dir, name);
	}

	public WrappedFile(String path) {
		super(path);
	}

	public WrappedFile(String dirPath, String name) {
		super(dirPath, name);
	}

	public WrappedFile(URI uri) {
		super(uri);
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
