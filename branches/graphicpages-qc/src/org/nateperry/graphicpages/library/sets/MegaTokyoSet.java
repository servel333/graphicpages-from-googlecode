package org.nateperry.graphicpages.library.sets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nateperry.graphicpages.library.NumericImageSet;

public class MegaTokyoSet extends NumericImageSet {

	//http://megatokyo.com/strips/
	//http://megatokyo.com/strips/1281.gif
	protected static final String LATEST_REGEX = "src=\"strips[/]([0-9]+).gif\"";
	protected static final Integer OLDEST_INDEX = 1;
	protected static final Integer DEFAULT_INDEX = OLDEST_INDEX;

	public MegaTokyoSet() {
		super();
	}

	public MegaTokyoSet(MegaTokyoSet lot) {
		super(lot);
	}

	@Override
	public Object clone() {
		return new MegaTokyoSet(this);
	}

	@Override
	protected Integer onGetDefaultIndex() {
		return DEFAULT_INDEX;
	}

	@Override
	protected String onGetFileName(Integer index) {
		return getName() + "-" + index + ".gif";
	}

	@Override
	protected String onGetIndexName() {
		return "MegaTokyo";
	}

	@Override
	protected String onGetName() {
		return "MegaTokyo";
	}

	@Override
	protected Integer onGetNewestIndex() {

		Integer latest = _newestIndex.getCachedProperty();
		String line = null;

		try {
			line = Util.findOnPage(getUrl(), LATEST_REGEX);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!Util.isNullOrEmpty(line)) {

			Matcher matcher = Pattern.compile(LATEST_REGEX).matcher(line);
			String st_latest = null;
			if (matcher.lookingAt())
			{
				st_latest = matcher.group(1);
			}

			try {
				latest = Integer.parseInt(st_latest);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}

		return latest;
	}

	@Override
	protected Integer onGetOldestIndex() {
		return OLDEST_INDEX;
	}

	@Override
	protected String onGetPageDescription(Integer index) {
		return String.format("%s %s", getName(), index);
	}

	@Override
	protected String onGetPageName(Integer index) {
		return String.format("%s %s", getName(), index);
	}

	@Override
	protected String onGetPageUrl(Integer index) {
		return String.format("http://megatokyo.com/strips/%s.gif", index);
	}

	@Override
	protected String onGetUrl() {
		return "http://megatokyo.com";
	}

	@Override
	protected Integer onParseFileName(String filename) {

		int index = -1;
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
