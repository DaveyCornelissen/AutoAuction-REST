package com.dcorn.api.file;

import com.dcorn.api.utils.CrudOperation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService extends CrudOperation<Image> {

    Image storeImage(MultipartFile file, int id);

    List<Image> storeImages(MultipartFile[] files, int id);
}
