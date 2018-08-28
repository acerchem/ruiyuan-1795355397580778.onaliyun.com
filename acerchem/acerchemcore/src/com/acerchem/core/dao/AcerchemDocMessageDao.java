package com.acerchem.core.dao;

import java.util.List;

import com.acerchem.core.model.AcerchemDocMessageModel;

public interface AcerchemDocMessageDao {

	List<AcerchemDocMessageModel> getDocMessageList(String acerchemCode);
	List<AcerchemDocMessageModel> getDocMessageAllList();
}
