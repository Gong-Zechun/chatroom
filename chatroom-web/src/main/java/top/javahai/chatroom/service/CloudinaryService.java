package top.javahai.chatroom.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import top.javahai.chatroom.exception.KqException;

@Service
public class CloudinaryService {

  private final Cloudinary cloudinary;

  @Autowired
  public CloudinaryService(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  public String uploadFile(MultipartFile file) throws IOException {
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    if (uploadResult.get("url") == null) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "图片上传失败！");
    }
    return uploadResult.get("url").toString();
  }

  public String uploadFileWithAutoDeleteTag(MultipartFile file) throws IOException {
    Map options = ObjectUtils.asMap(
      "tags", "auto-delete-7d",
      "context", "timestamp=" + (System.currentTimeMillis() / 1000)
    );
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
    if (uploadResult.get("url") == null) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "图片上传失败！");
    }
    return (String) uploadResult.get("url");
  }

  public Map deleteFile(String publicId) throws IOException {
    return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
  }
}
