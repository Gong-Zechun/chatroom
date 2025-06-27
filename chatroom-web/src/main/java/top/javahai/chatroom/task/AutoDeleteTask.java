package top.javahai.chatroom.task;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.Calendar;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoDeleteTask {

  private final Cloudinary cloudinary;

  public AutoDeleteTask(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  // 定时删除旧文件
  @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点执行
  public void deleteOldFiles() throws Exception {
    // 删除7天前的auto-delete-7d标签文件
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, -7);
    long timestamp = calendar.getTimeInMillis() / 1000;

    // 查找并删除
    Map result = cloudinary.api().deleteResourcesByTag(
      "auto-delete-7d",
      ObjectUtils.asMap(
        "keep_original", false,
        "invalidate", true,
        "context", "timestamp<" + timestamp
      )
    );
  }

}
