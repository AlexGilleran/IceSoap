package com.alexgilleran.icesoap.example.model;

import com.alexgilleran.icesoap.annotation.SOAPField;
import com.alexgilleran.icesoap.annotation.SOAPObject;

@SOAPObject("//TeamInfo")
public class TeamInfo {
	/*
	 * <m:tTeamInfo> 
	 * 		<m:iId>116</m:iId> <m:sName>Algeria</m:sName>
	 * 		<m:sCountryFlag>http://footballpool.dataaccess.eu/images/flags/dz.gif</m:sCountryFlag>
	 * 		<m:sWikipediaURL>http://en.wikipedia.org/wiki/Algeria_national_football_team</m:sWikipediaURL>
	 * </m:tTeamInfo>
	 */
	
	@SOAPField("iId")
	private long id;
	
	@SOAPField("sName")
	private String name;

	@SOAPField("sCountryFlag")
	private String countryFlag;
	
	@SOAPField("sWikipediaURL")
	private String wikipediaUrl;

}
