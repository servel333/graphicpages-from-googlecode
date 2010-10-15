package org.nateperry.graphicpages;

import org.nateperry.graphicpages.mvc.view.ShowImageView;

/**
 * Globals
 */
public class G {

	public static final ePreferredFileLocationSetting PREFERRED_FILE_LOCATION = 
		ePreferredFileLocationSetting.INTERNAL;

	public static final String PACKAGE_NAME = 
		"org.nateperry.graphicpages";

	public static final String EXTERNAL_DATA_FOLDER = 
		"/Android/data/" + PACKAGE_NAME + "/files/";

	public static final int DOWNLOAD_BUFFER_SIZE = 1024;

//	public static final String SETTINGS_FILE = "GraphicPagesSettings";
//
//	public static final String LOT_SETTING_KEY = "lot";
//
//	public static final String ID_SETTING_KEY = "id";

	public static ShowImageView mainView = null;

}
