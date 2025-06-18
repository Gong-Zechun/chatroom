package top.javahai.chatroom.service.impl;

import cn.hutool.core.map.MapUtil;
import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.javahai.chatroom.dao.GroupMsgContentDao;
import top.javahai.chatroom.entity.GroupMsgContent;
import top.javahai.chatroom.entity.RespPageBean;
import top.javahai.chatroom.newEntity.MessageV1;
import top.javahai.chatroom.service.GroupMsgContentService;
import top.javahai.chatroom.utils.RedissonUtils;

/**
 * (GroupMsgContent)表服务实现类
 *
 * @author makejava
 * @since 2020-06-17 10:51:13
 */
@Service("groupMsgContentService")
public class GroupMsgContentServiceImpl implements GroupMsgContentService {

  @Resource
  private GroupMsgContentDao groupMsgContentDao;

  @Autowired
  private RedissonUtils redissonUtils;

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  @Override
  public GroupMsgContent queryById(Integer id) {
    return this.groupMsgContentDao.queryById(id);
  }

  /**
   * 查询多条数据
   *
   * @param offset 查询起始位置
   * @param limit 查询条数
   * @return 对象列表
   */
  @Override
  public List<GroupMsgContent> queryAllByLimit(Integer offset, Integer limit) {
    return this.groupMsgContentDao.queryAllByLimit(offset, limit);
  }

  /**
   * 新增数据
   *
   * @param groupMsgContent 实例对象
   * @return 实例对象
   */
  @Override
  public GroupMsgContent insert(GroupMsgContent groupMsgContent) {
    this.groupMsgContentDao.insert(groupMsgContent);
    return groupMsgContent;
  }

  /**
   * 修改数据
   *
   * @param groupMsgContent 实例对象
   * @return 实例对象
   */
  @Override
  public GroupMsgContent update(GroupMsgContent groupMsgContent) {
    this.groupMsgContentDao.update(groupMsgContent);
    return this.queryById(groupMsgContent.getId());
  }

  /**
   * 通过主键删除数据
   *
   * @param id 主键
   * @return 是否成功
   */
  @Override
  public boolean deleteById(Integer id) {
    return this.groupMsgContentDao.deleteById(id) > 0;
  }

  @Override
  public RespPageBean getAllGroupMsgContentByPage(
    Integer page, Integer size, String nickname,
    Integer type, Date[] dateScope
  ) {
    if (page != null && size != null) {
      page = (page - 1) * size;
    }
    List<GroupMsgContent> allGroupMsgContentByPage = groupMsgContentDao.getAllGroupMsgContentByPage(
      page, size, nickname, type, dateScope);
    Long total = groupMsgContentDao.getTotal(nickname, type, dateScope);
    RespPageBean respPageBean = new RespPageBean();
    respPageBean.setData(allGroupMsgContentByPage);
    respPageBean.setTotal(total);
    return respPageBean;
  }

  @Override
  public Integer deleteGroupMsgContentByIds(Integer[] ids) {
    return groupMsgContentDao.deleteGroupMsgContentByIds(ids);
  }

  /**
   * 处理群聊记录的导出
   *
   * @param response
   * @throws IOException
   */
  @Override
  public void handleDownload(HttpServletResponse response) throws IOException {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    //设置文件信息 这里URLEncoder.encode可以防止中文乱码
    String fileName = URLEncoder.encode("群聊记录", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    RespPageBean allGroupMsgContentByPage = getAllGroupMsgContentByPage(null, null, null, null,
      null
    );
    List<GroupMsgContent> data = (List<GroupMsgContent>) allGroupMsgContentByPage.getData();
    //转化数据为用于Excel导出的格式
//        List<GroupMsgContentData> convertedData = data.stream().map(item -> {
//            try {
//                return GroupMsgContent.convertEntityToData(item);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            return new GroupMsgContentData();
//        }).collect(Collectors.toList());

    //写出数据到HttpServletResponse中
    //EasyExcel.write(response.getOutputStream(),GroupMsgContentData.class).sheet("sheet1").doWrite(convertedData);
    EasyExcel.write(response.getOutputStream(), GroupMsgContent.class).sheet("sheet1")
      .doWrite(data);
  }

  public List<MessageV1> getLimitGroupMsgs() {
    Map<String, Object> map = redissonUtils.getHash("groupMsgs");
    if (MapUtil.isEmpty(map)) {
      return Lists.newArrayList();
    }

    List<MessageV1> messageV1s = Lists.newArrayList();
    for (String messageId : map.keySet()) {
      messageV1s.add((MessageV1) map.get(messageId));
    }
    int size = messageV1s.size() > 100 ? 100 : messageV1s.size();
    return messageV1s.subList(0, size);
  }
}
