package com.sbplatform.core.jackson;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sbplatform.core.jackson.helper.ThreadJacksonMixInHolder;

public class SbMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	private ObjectMapper objectMapper = new ObjectMapper();
	private boolean prefixJson = false;

	/** 
	 * <br> 
	 * 2013-9-27 下午4:10:28 
	 * 
	 * @see org.springframework.http.converter.json.MappingJacksonHttpMessageConverter#writeInternal(Object, 
	 * org.springframework.http.HttpOutputMessage) 
	 */
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		// 判断是否需要重写objectMapper  
		ObjectMapper objectMapper = this.objectMapper;// 本地化ObjectMapper，防止方法级别的ObjectMapper改变全局ObjectMapper  
		if (ThreadJacksonMixInHolder.isContainsMixIn()) {
			objectMapper = ThreadJacksonMixInHolder.builderMapper();
		}

		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);

		if (objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
			jsonGenerator.useDefaultPrettyPrinter();
		}

		try {
			if (this.prefixJson) {
				jsonGenerator.writeRaw("{} && ");
			}
			objectMapper.writeValue(jsonGenerator, object);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	public boolean isPrefixJson() {
		return prefixJson;
	}

	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}

}