package top.javahai.chatroom.controller;

import cn.hutool.core.map.MapUtil;
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
  public KqRespEntity uploadFile(@RequestParam("image") MultipartFile file, @RequestParam String token) {
    try {
      return KqRespEntity.success(MapUtil.of("imageUrl", cloudinaryService.uploadFileWithAutoDeleteTag(file, token)));
    } catch (IOException e) {
      e.printStackTrace();
      return KqRespEntity.badRequest("上传失败: " + e.getMessage());
    }
  }
}
