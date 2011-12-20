package com.alexgilleran.icesoap.example.model;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

@XMLObject("//TeamInfo")
public class TeamInfo {
	/*
	 * <m:tTeamInfo> 
	 * 		<m:iId>116</m:iId> <m:sName>Algeria</m:sName>
	 * 		<m:sCountryFlag>http://footballpool.dataaccess.eu/images/flags/dz.gif</m:sCountryFlag>
	 * 		<m:sWikipediaURL>http://en.wikipedia.org/wiki/Algeria_national_football_team</m:sWikipediaURL>
	 * </m:tTeamInfo>
	 */
	
	@XMLField("iId")
	private long id;
	
	@XMLField("sName")
	private String name;

	@XMLField("sCountryFlag")
	private String countryFlag;
	
	@XMLField("sWikipediaURL")
	private String wikipediaUrl;

}
