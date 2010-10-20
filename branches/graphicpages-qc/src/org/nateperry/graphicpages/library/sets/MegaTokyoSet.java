package org.nateperry.graphicpages.library.sets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nateperry.graphicpages.U;
import org.nateperry.graphicpages.library.IImageSet;

public class MegaTokyoSet implements IImageSet {

	//http://megatokyo.com/strips/
	//http://megatokyo.com/strips/1281.gif
	protected static final String LATEST_REGEX = "src=\"strips[/]([0-9]+).gif\"";
	protected static final Integer OLDEST_INDEX = 1;
	protected static final Integer DEFAULT_INDEX = OLDEST_INDEX;
	
	public MegaTokyoSet() {
		super();
	}

	public Integer getDefaultIndex() {
		return DEFAULT_INDEX;
	}

	public String getFileName(Integer index) {
		return getName() + "-" + index + ".gif";
	}

	public String getIndexName() {
		return "MegaTokyo";
	}

	public String getName() {
		return "MegaTokyo";
	}

	public Integer getNewestIndex(Integer newest) {

		String line = null;

		try {
			line = U.findOnPage(getUrl(), LATEST_REGEX);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!U.isNullOrEmpty(line)) {

			Matcher matcher = Pattern.compile(LATEST_REGEX).matcher(line);
			String st_latest = null;
			if (matcher.lookingAt())
			{
				st_latest = matcher.group(1);
			}

			try {
				newest = Integer.parseInt(st_latest);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}

		return newest;
	}

	public Integer getOldestIndex() {
		return OLDEST_INDEX;
	}

	public String getPageDescription(Integer index) {
		return String.format("%s %s", getName(), index);
	}

	public String getPageName(Integer index) {
		return String.format("%s %s", getName(), index);
	}

	public String getPageUrl(Integer index) {
		return String.format("http://megatokyo.com/strips/%s.gif", index);
	}

	public String getUrl() {
		return "http://megatokyo.com";
	}

	public Integer parseFileName(String filename) {
		Integer index = -1;
		filename = filename.replaceAll("^" + getName() + "-", "");
		filename = filename.replaceAll("[.][a-zA-Z]+$", "");

		try {
			index = Integer.parseInt(filename);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return index;
	}

}
