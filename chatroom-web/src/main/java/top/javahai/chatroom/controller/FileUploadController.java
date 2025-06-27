package top.javahai.chatroom.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.javahai.chatroom.service.CloudinaryService;
import top.javahai.chatroom.utils.KqRespEntity;

@RestController
public class FileUploadController {

  private final CloudinaryService cloudinaryService;

  @Autowired
  public FileUploadController(CloudinaryService cloudinaryService) {
    this.cloudinaryService = cloudinaryService;
  }

  @PostMapping("/upload")
  public KqRespEntity uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      return KqRespEntity.success(cloudinaryService.uploadFileWithAutoDeleteTag(file));
    } catch (IOException e) {
      e.printStackTrace();
      return KqRespEntity.badRequest("上传失败: " + e.getMessage());
    }
  }
}
