package com.dcorn.api.file;

import com.dcorn.api.utils.handler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final IFileService FILE_SERVICE;


    public FileController(IFileService file_service) {
        FILE_SERVICE = file_service;
    }

    @PostMapping("/{id}/uploadFile")
    public ResponseHandler uploadFile(@PathVariable("id") int id, @RequestParam("file") MultipartFile file) {
        return new ResponseHandler(HttpStatus.OK, FILE_SERVICE.storeImage(file, id));
    }

    @PostMapping("/{id}/uploadFiles")
    public ResponseHandler uploadMultiplyFiles(@PathVariable("id") int id, @RequestParam("file") MultipartFile[] files) {
        return new ResponseHandler(HttpStatus.OK, FILE_SERVICE.storeImages(files, id));
    }

    @DeleteMapping("/{id}/deleteFile")
    public ResponseHandler RemoveFile() {
        return null;
    }

    @DeleteMapping("/{id}/deleteAllFiles")
    public ResponseHandler RemoveAllFiles() {
        return null;
    }
}
