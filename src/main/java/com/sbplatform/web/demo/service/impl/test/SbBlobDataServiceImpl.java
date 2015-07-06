package com.sbplatform.web.demo.service.impl.test;

import java.io.IOException;
import java.sql.Blob;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.hibernate.LobHelper;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.web.demo.entity.test.SbBlobDataEntity;
import com.sbplatform.web.demo.service.test.SbBlobDataServiceI;

@Service("sbBlobDataService")
@Transactional
public class SbBlobDataServiceImpl extends CommonServiceImpl implements SbBlobDataServiceI {
	
	public void saveObj(String documentTitle, MultipartFile file) {
		SbBlobDataEntity obj = new SbBlobDataEntity();
		LobHelper lobHelper = commonDao.getSession().getLobHelper();
		Blob data;
		try {
			data = lobHelper.createBlob(file.getInputStream(), 0);
			obj.setAttachmentcontent(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		obj.setAttachmenttitle(documentTitle);
		String sFileName = file.getOriginalFilename();
		int iPos = sFileName.lastIndexOf('.');
		if (iPos >= 0) {
			obj.setExtend(sFileName.substring(iPos+1));
		}
		super.save(obj);
	}
}