/**
 * 公共JavaScript函数库
 * 包含多个页面共用的功能
 */

// 密码显示/隐藏切换功能
function initTogglePassword() {
  $(".toggle-password").off('click').on('click', function() {
    const $input = $(this).closest('.input-group').find('input');
    const $icon = $(this).find("i");

    // 切换输入类型
    const type = $input.attr("type") === "password" ? "text" : "password";
    $input.attr("type", type);

    // 切换图标
    $icon.toggleClass("zmdi-eye zmdi-eye-off");

    // 视觉反馈
    $(this).toggleClass("active");
  });
}

// 页面加载完成后初始化公共组件
$(document).ready(function() {
  // 初始化密码切换按钮
  initTogglePassword();

  // 这里可以添加其他需要在页面加载时初始化的公共功能
});

// 刷新用户列表
function refreshUserList() {
  // 获取用户列表的容器
  const userList = $(".user-list");
  const currentUsername = sessionStorage.getItem('username');

  // 发起请求获取用户列表
  $.ajax({
    url: "/users?currentUsername=" + currentUsername,
    method: "GET",
    success: function(data) {
      console.log(data.data);
      console.log("currentUsername:" + currentUsername);

      // 清空现有用户列表（保留header部分）
      userList.empty();

      // 更新在线人数显示
      $(".sidebar-header").text(`在线用户 (${data.data.length})`);

      // 重新渲染用户列表
      $.each(data.data, function(index, user) {
        const isCurrentUser = user.username === currentUsername;
        const activeTimeText = user.lastVisitTimeStr || '刚刚活跃';

        const userItem = $(`
          <li class="user-item ${isCurrentUser ? 'current-user' : ''}">
            <div class="user-avatar">
              ${user.username.charAt(0)}
            </div>
            <div class="user-info">
              <div class="user-name">
                ${user.username}${isCurrentUser ? ' (我)' : ''}
              </div>
              <div class="user-active-time">
                最近活跃：${activeTimeText}
              </div>
            </div>
          </li>
        `);

        // 如果用户有头像URL，使用头像代替文字
        if (user.headpic) {
          userItem.find('.user-avatar').html(`<img src="${user.headpic}" alt="${user.username}" style="width:100%;height:100%;border-radius:50%;object-fit:cover;">`);
        }

        userList.append(userItem);
      });
    },
    error: function(xhr, status, error) {
      console.error("获取用户列表出错:", error);
      // 显示错误信息
      userList.html('<li style="color:#666;padding:10px;">无法加载用户列表，请刷新重试</li>');
    }
  });
}


/**
 * [WebSocket] -- START
 */
// 将WebSocket初始化移到全局范围，只初始化一次
let stompClient = null;

// 初始化 WebSocket 连接
function initWebSocket() {
  if (!stompClient) {
    // 创建一个 SockJS 客户端实例
    const socket = new SockJS('/ws/chat');
    // 使用 Stomp.js 封装 SockJS
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
      console.log('WebSocket connected');
    }, function (error) {
      console.error('WebSocket connection error:', error);
    });
  }
  return stompClient;
}
/**
 * [WebSocket] -- END
 */


// 提取公共的渲染函数
function renderMessage(message, isCurrentUser) {
  const messageClass = isCurrentUser ? 'sent' : 'received';

  // 图片消息处理
  let contentHtml = message.message;
  if (message.message && message.message.startsWith('[图片]')) {
    const imageUrl = message.message.substring(4); // 去掉"[图片]"前缀
    contentHtml = `
            <div class="image-message">
                <img src="${imageUrl}" class="message-image"
                     onerror="this.onerror=null;this.parentElement.innerHTML='[图片加载失败]'">
            </div>
        `;
  }

  return $(`
        <div class="message ${messageClass}">
            <img class="user-avatar" src="${message.headpic || 'default-avatar.png'}"
                 alt="${message.username}"
                 onerror="this.src='default-avatar.png'">
            <div class="message-content-container">
                <div class="message-info">
                    <span class="message-sender">${message.username}${isCurrentUser ? ' (我)' : ''}</span>
                    <span class="message-time">${message.sendTimeStr}</span>
                </div>
                <div class="message-content">
                    ${contentHtml}
                </div>
            </div>
        </div>
    `);
}

function checkToken() {
  const token = sessionStorage.getItem('token');
  if (!token) {
    // 如果token为空，跳转到登录页面
    alert("token已被清除，请重新登录！");
    window.location.href = "/login.html";
  }

  // 创建 FormData 对象
  const formData = new FormData();
  formData.append("token", token);

  $.ajax({
    url: "/checkToken", // 后端接口地址
    method: "POST",
    data: formData,
    processData: false, // 告诉 jQuery 不要去处理发送的数据
    contentType: false, // 告诉 jQuery 不要去设置 Content-Type 请求头
    success: function (data) {
      console.log(data.data);

    },
    error: function (xhr, status, error) {
      const response = JSON.parse(xhr.responseText);
      alert(response.msg); //token过期
      // 清除 sessionStorage 中的所有项
      window.location.href = "/login.html";
      sessionStorage.clear();
    }
  });
}
