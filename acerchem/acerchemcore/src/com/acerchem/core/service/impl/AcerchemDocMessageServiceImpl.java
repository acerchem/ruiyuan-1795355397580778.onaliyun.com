package com.acerchem.core.service.impl;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Required;

import com.acerchem.core.dao.AcerchemDocMessageDao;
import com.acerchem.core.model.AcerchemDocMessageModel;
import com.acerchem.core.service.AcerchemDocMessageService;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.contents.ContentCatalogModel;
import de.hybris.platform.core.model.media.MediaFolderModel;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;

public class AcerchemDocMessageServiceImpl	 implements AcerchemDocMessageService {

	private String docMessageMediaFolderName;
	@Resource
	private AcerchemDocMessageDao acerchemDocMessageDao;
	
	@Resource
	private MediaService mediaService;
	@Resource
	private CatalogVersionService catalogVersionService;
	@Resource
	private CatalogService catalogService;
	@Resource
	private ModelService modelService;
	
	@Override
	public AcerchemDocMessageModel addDocMessage(final InputStream masterDataStream, final String filename, final String mimeType,final String author,final String title) {
		final AcerchemDocMessageModel docMessage = modelService.create(AcerchemDocMessageModel.class);
		docMessage.setTitle(title);
		docMessage.setAuthor(author);
		docMessage.setAliyunUrl("doc-message");
		docMessage.setCode(UUID.randomUUID().toString());
		docMessage.setMime(mimeType);
		docMessage.setRealFileName(filename);
		docMessage.setCatalogVersion(getCatalogVersion());
		docMessage.setArticleCode(UUID.randomUUID().toString());
		
		modelService.save(docMessage);

		mediaService.setStreamForMedia(docMessage, masterDataStream, filename, mimeType, getDocMessageMediaFolder());
		
		return docMessage;
	}
	
//	public AcerchemDocMessageModel addDocMessage(final InputStream masterDataStream, final String filename,final String author,final String title) {
//		
//	}
	
	
	protected MediaFolderModel getDocMessageMediaFolder()
	{
		final MediaFolderModel folder = mediaService.getFolder(getDocMessageMediaFolderName());
		return folder;
	}

	@Override
	public void delDocMessage(final String articeCode) {
		final List<AcerchemDocMessageModel> list = acerchemDocMessageDao.getDocMessageList(articeCode);
		
		if(CollectionUtils.isNotEmpty(list)){
			for(final AcerchemDocMessageModel doc:list){
				modelService.remove(doc);
			}
		}

	}

	@Override
	public List<AcerchemDocMessageModel> getDocMessageList(String searching) {
		// TODO Auto-generated method stub
		return acerchemDocMessageDao.getDocMessageAllList(searching);
	}
/*	@Override
	public List<AcerchemDocMessageModel> getDocMessageList() {
		// TODO Auto-generated method stub
		return acerchemDocMessageDao.getDocMessageAllList();
	}*/
	
	
	protected CatalogVersionModel getCatalogVersion()
	{
		CatalogVersionModel catalogVersion = catalogService.getDefaultCatalog() == null ? null : catalogService
				.getDefaultCatalog().getActiveCatalogVersion();
		if (catalogVersion == null)
		{
			final Collection<CatalogVersionModel> catalogs = catalogVersionService.getSessionCatalogVersions();
			for (final CatalogVersionModel cvm : catalogs)
			{
				if (cvm.getCatalog() instanceof ContentCatalogModel)
				{
					catalogVersion = cvm;
					break;
				}
			}
		}

		return catalogVersion;
	}

	public String getDocMessageMediaFolderName() {
		return docMessageMediaFolderName;
	}

	@Required
	public void setDocMessageMediaFolderName(final String docMessageMediaFolderName) {
		this.docMessageMediaFolderName = docMessageMediaFolderName;
	}

}
