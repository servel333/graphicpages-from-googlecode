package org.nateperry.graphicpages;

public class Globals {

	public enum PreferredFileLocationSetting {
		INTERNAL, EXTERNAL
	}

	public static final PreferredFileLocationSetting PREFERRED_FILE_LOCATION = 
		PreferredFileLocationSetting.INTERNAL;

	public static final String PACKAGE_NAME = 
		"org.nateperry.graphicpages";

	public static final String EXTERNAL_DATA_FOLDER = 
		"/Android/data/" + PACKAGE_NAME + "/files/";

	public static final String SETTINGS_FILE = "GraphicPagesSettings";

	public static final String LOT_SETTING_KEY = "lot";

	public static final String ID_SETTING_KEY = "id";

}
