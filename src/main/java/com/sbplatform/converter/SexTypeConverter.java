package com.sbplatform.converter;

import org.springframework.core.convert.converter.Converter;

import com.sbplatform.type.SexType;

public class SexTypeConverter implements Converter<String, SexType> {

	@Override
	public SexType convert(String source) {
		String value = source.trim();
		if ("".equals(value)) {
			return null;
		}
		return SexType.valueOf(Integer.parseInt(source));
	}

}
