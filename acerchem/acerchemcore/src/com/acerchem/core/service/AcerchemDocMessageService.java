package com.acerchem.core.service;

import java.io.InputStream;
import java.util.List;

import com.acerchem.core.model.AcerchemDocMessageModel;

public interface AcerchemDocMessageService {

	AcerchemDocMessageModel addDocMessage(InputStream masterDataStream, String filename, String mimeType,String author,String title);
	void delDocMessage(String articeCode);
	List<AcerchemDocMessageModel> getDocMessageList();
}
