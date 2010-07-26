package org.nateperry.graphicpages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionableContentWebComic extends WebComic {

	//public static final String QC_IMAGE_NAME = R.string.app_full_name + "/" + ID_NAME + "/%s";
	//public static final String QC_IMAGE_DESCRIPTION = NAME + " comic number %s";
	public static final String LATEST_REGEX = ".*http://www[.]questionablecontent[.]net/comics/([0-9]+)[.]png.*";

	@Override
	public String IdName() {
		return "QuestionableContent";
	}

	@Override
	public String Name() {
		return "Questionable Content";
	}
	
	@Override
	public String PageUrl(int id) {
		return String.format("http://www.questionablecontent.net/comics/%s.png", id);
	}

	@Override
	public String Url() {
		return "http://www.questionablecontent.net";
	}

	//@Override
	//public String UriName() {
	//	return null;
	//}

	@Override
	public int NewerId(int id) {

		if (NewestId() > id) {
			id += 1;
		}
		
		return id;
	}

	@Override
	public int NewestId() {
		
		int latest = OldestId();
		String line = Utils.FindOnPage_Safe(Url(), LATEST_REGEX);
		
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
	public int OlderId(int id) {
		
		if (OldestId() < id) {
			id -= 1;
		}
		
		return id;
	}

	@Override
	public int OldestId() {
		return 1;
	}


	@Override
	public String PageDescription(int id) {
		return String.format("%s %s", Name(), id);
	}

	@Override
	public String PageName(int id) {
		return String.format("%s %s", Name(), id);
	}

}
