package com.jqdi.smssender.core.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlUtil {
	private XmlUtil() {
	}

	public static <T> T toEntity(String xmlString, Class<T> clazz) {
		if (xmlString == null) {
			return null;
		}

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			@SuppressWarnings("unchecked")
			T t = (T) unmarshaller.unmarshal(new StringReader(xmlString));

			return t;
		} catch (JAXBException e) {
			log.error("JAXBException error", e);
		}
		return null;
	}
}
