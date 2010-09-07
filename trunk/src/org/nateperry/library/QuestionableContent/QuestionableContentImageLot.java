package org.nateperry.library.QuestionableContent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nateperry.Util;
import org.nateperry.library.NumericImageLot;

public class QuestionableContentImageLot extends NumericImageLot {

	//public static final String QC_IMAGE_NAME = R.string.app_full_name + "/" + ID_NAME + "/%s";
	//public static final String QC_IMAGE_DESCRIPTION = NAME + " comic number %s";
	protected static final String LATEST_REGEX = ".*http://www[.]questionablecontent[.]net/comics/([0-9]+)[.]png.*";
	protected static final Integer DEFAULT_INDEX = 1;
	protected static final Integer OLDEST_INDEX = 1;

	public QuestionableContentImageLot() {
		super();
	}

	public QuestionableContentImageLot(QuestionableContentImageLot comic) {
		super(comic);
	}

	@Override
	public Object clone() {
		return new QuestionableContentImageLot(this);
	}

	@Override
	protected Integer onGetDefaultIndex() {
		return DEFAULT_INDEX;
	}

	@Override
	protected String onGetFileName(Integer index) {
		return getName() + "-" + index + ".png";
	}

	@Override
	protected String onGetIndexName() {
		return "QuestionableContent";
	}

	@Override
	protected String onGetName() {
		return "Questionable Content";
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
		return String.format("http://www.questionablecontent.net/comics/%s.png", index);
	}

	@Override
	protected String onGetUrl() {
		return "http://www.questionablecontent.net";
	}

	@Override
	protected Integer onMoveIndex(Integer index, int count) {
		index += count;

		/// TODO: check bounds here.

		//if (index <= )

		return index;
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

//	@Override
//	protected Integer onGetNewerIndex(Integer index) {
//
//		if (getNewestIndex() > index) {
//			id += 1;
//		}
//
//		return index;
//	}

//	@Override
//	protected Integer onGetOlderIndex(Integer index) {
//
//		if (getOldestIndex() < index) {
//			id -= 1;
//		}
//
//		return index;
//	}

}
