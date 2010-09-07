//package org.nateperry.graphicpages;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class QuestionableContentImageLot extends ImageLot {
//
//	//public static final String QC_IMAGE_NAME = R.string.app_full_name + "/" + ID_NAME + "/%s";
//	//public static final String QC_IMAGE_DESCRIPTION = NAME + " comic number %s";
//	protected static final String LATEST_REGEX = ".*http://www[.]questionablecontent[.]net/comics/([0-9]+)[.]png.*";
//
//	public QuestionableContentImageLot() {
//		super();
//	}
//
//	public QuestionableContentImageLot(QuestionableContentImageLot comic) {
//		super(comic);
//	}
//
//	@Override
//	protected String onGetIdName() {
//		return "QuestionableContent";
//	}
//
//	@Override
//	protected String onGetName() {
//		return "Questionable Content";
//	}
//
//	@Override
//	protected String onGetPageUrl(int id) {
//		return String.format("http://www.questionablecontent.net/comics/%s.png", id);
//	}
//
//	@Override
//	protected String onGetUrl() {
//		return "http://www.questionablecontent.net";
//	}
//
//	@Override
//	protected int onGetNewerId(int id) {
//
//		if (getNewestId() > id) {
//			id += 1;
//		}
//
//		return id;
//	}
//
//	@Override
//	protected int onGetNewestId() {
//
//		int latest = getOldestId();
//		String line = null;
//		try {
//			line = Utils.findOnPage(getUrl(), LATEST_REGEX);
//		} catch (MalformedURLException e1) {
//			e1.printStackTrace();
//			latest = _newestId;
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			latest = _newestId;
//		}
//
//		if (!Utils.isNullOrEmpty(line)) {
//
//			Matcher matcher = Pattern.compile(LATEST_REGEX).matcher(line);
//			String st_latest = null;
//			if (matcher.lookingAt())
//			{
//				st_latest = matcher.group(1);
//			}
//
//			Utils.tryParse(st_latest, latest);
//
//			try {
//				latest = Integer.parseInt(st_latest);
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//		return latest;
//	}
//
//	@Override
//	protected int onGetOlderId(int id) {
//
//		if (getOldestId() < id) {
//			id -= 1;
//		}
//
//		return id;
//	}
//
//	@Override
//	protected int onGetOldestId() {
//		return 1;
//	}
//
//	@Override
//	protected int onMoveId(int count, int id) {
//		id += count;
//
//		/// TODO: check bounds here.
//
//		//if (id <= )
//
//		return id;
//	}
//
//	@Override
//	protected String onGetPageDescription(int id) {
//		return String.format("%s %s", getName(), id);
//	}
//
//	@Override
//	protected String onGetPageName(int id) {
//		return String.format("%s %s", getName(), id);
//	}
//
//	@Override
//	protected String onGetFileName(int id) {
//		return getName() + "-" + id + ".png";
//	}
//
//	@Override
//	protected int onParseFileName(String filename) {
//
//		int index = -1;
//		filename = filename.replaceAll("^" + getName() + "-", "");
//		filename = filename.replaceAll("[.][a-zA-Z]+$", "");
//
//		try {
//			index = Integer.parseInt(filename);
//		} catch (NumberFormatException e) {
//
//		}
//
//		return index;
//	}
//
//	@Override
//	public Object clone() {
//		return new QuestionableContentImageLot(this);
//	}
//
//}
