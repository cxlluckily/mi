package com.shankephone.mi.common.enumeration;

/**
 * ContentType枚举 枚举
 *
 * @author 司徒彬
 * @date 2017-04-28 11:28
 */
public enum ContentTypeEnum
{
	
	application_json("application/json"), application_x_www_form_urlencoded("application/x-www-form-urlencoded"), text_xml("text/xml"),other("other");
	
	private String value;
	
	ContentTypeEnum(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}
