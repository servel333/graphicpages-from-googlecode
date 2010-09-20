package org.nateperry.graphicpages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionableContentWebComic extends WebComic {

	//public static final String QC_IMAGE_NAME = R.string.app_full_name + "/" + ID_NAME + "/%s";
	//public static final String QC_IMAGE_DESCRIPTION = NAME + " comic number %s";
	public static final String LATEST_REGEX = ".*http://www[.]questionablecontent[.]net/comics/([0-9]+)[.]png.*";

	@Override
	public String OnGetIdName() {
		return "QuestionableContent";
	}

	@Override
	public String OnGetName() {
		return "Questionable Content";
	}
	
	@Override
	public String OnGetPageUrl(int id) {
		return String.format("http://www.questionablecontent.net/comics/%s.png", id);
	}

	@Override
	public String OnGetUrl() {
		return "http://www.questionablecontent.net";
	}

	@Override
	public int OnGetNewerId(int id) {

		if (GetNewestId() > id) {
			id += 1;
		}
		
		return id;
	}

	@Override
	public int OnGetNewestId() {
		
		int latest = GetOldestId();
		String line = Utils.FindOnPage_Safe(GetUrl(), LATEST_REGEX);
		
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
	public int OnGetOlderId(int id) {
		
		if (GetOldestId() < id) {
			id -= 1;
		}
		
		return id;
	}

	@Override
	public int OnGetOldestId() {
		return 1;
	}

	@Override
	public String OnGetPageDescription(int id) {
		return String.format("%s %s", GetName(), id);
	}

	@Override
	public String OnGetPageName(int id) {
		return String.format("%s %s", GetName(), id);
	}
	

	@Override
	protected String OnGetFileName(int id) {
		return this.GetName() + "-" + id + ".png";
	}

}
