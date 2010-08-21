package org.nateperry.graphicpages;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionableContentImageLot extends ImageLot {

	//public static final String QC_IMAGE_NAME = R.string.app_full_name + "/" + ID_NAME + "/%s";
	//public static final String QC_IMAGE_DESCRIPTION = NAME + " comic number %s";
	protected static final String LATEST_REGEX = ".*http://www[.]questionablecontent[.]net/comics/([0-9]+)[.]png.*";
	
	public QuestionableContentImageLot() {
		super();
	}
	
	public QuestionableContentImageLot(QuestionableContentImageLot comic) {
		super(comic);
	}
	
	@Override
	public String onGetIdName() {
		return "QuestionableContent";
	}

	@Override
	public String onGetName() {
		return "Questionable Content";
	}
	
	@Override
	public String onGetPageUrl(int id) {
		return String.format("http://www.questionablecontent.net/comics/%s.png", id);
	}

	@Override
	public String onGetUrl() {
		return "http://www.questionablecontent.net";
	}

	@Override
	public int onGetNewerId(int id) {

		if (getNewestId() > id) {
			id += 1;
		}
		
		return id;
	}

	@Override
	public int onGetNewestId() {
		
		int latest = getOldestId();
		String line = null;
		try {
			line = Utils.FindOnPage(getUrl(), LATEST_REGEX);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			latest = _newestId;
		} catch (IOException e1) {
			e1.printStackTrace();
			latest = _newestId;
		}
		
		if (!Utils.IsNullOrEmpty(line)) {
			
			Matcher matcher = Pattern.compile(LATEST_REGEX).matcher(line);
			String st_latest = null;
			if (matcher.lookingAt())
			{
				st_latest = matcher.group(1);
			}
			
			Utils.TryParse(st_latest, latest);
			
			try {
				latest = Integer.parseInt(st_latest);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
		}
		
		return latest;
	}

	@Override
	public int onGetOlderId(int id) {
		
		if (getOldestId() < id) {
			id -= 1;
		}
		
		return id;
	}

	@Override
	public int onGetOldestId() {
		return 1;
	}

	@Override
	protected int onMoveId(int count, int id) {
		id += count;
		
		/// TODO: check bounds here.
		
		//if (id <= )
		
		return id;
	}

	@Override
	public String onGetPageDescription(int id) {
		return String.format("%s %s", getName(), id);
	}

	@Override
	public String onGetPageName(int id) {
		return String.format("%s %s", getName(), id);
	}
	
	@Override
	protected String onGetFileName(int id) {
		return getName() + "-" + id + ".png";
	}

	@Override
	protected int onParseFileName(String filename) {
		
		int index = -1;
		filename = filename.replaceAll("^" + getName() + "-", "");
		filename = filename.replaceAll("[.][a-zA-Z]+$", "");
		
		try {
			index = Integer.parseInt(filename);
		} catch (NumberFormatException e) {
			
		}
		
		return index;
	}

	@Override
	public Object clone() {
		return new QuestionableContentImageLot(this);
	}

}
