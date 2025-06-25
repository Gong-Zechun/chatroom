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

function refreshUserList() {
  // 获取用户列表的容器
  const userList = $("#user-list");
  const userCountContainer = $("#users-total-num");
  const currentUsername = sessionStorage.getItem('username');

  // 判断用户名是否为空
  if (!currentUsername) {
    // 如果用户名为空，跳转到登录页面
    window.location.href = "/login.html";
    return; // 添加return防止继续执行
  }

  // 发起请求获取用户列表
  $.ajax({
    url: "/users?currentUsername=" + currentUsername, // 后端接口地址
    method: "GET",
    success: function (data) {
      console.log(data.data);
      console.log("currentUsername:" + currentUsername);

      // 清空用户数量显示并更新
      userCountContainer.empty().append("成员列表（" + data.data.length + "人）");
      // 清空现有用户列表（保留header部分）
      userList.find("li:not(.header)").remove();

      // 重新渲染用户列表
      $.each(data.data, function (index, user) {
        const userItem = $("<li></li>").addClass("online");

        const hoverAction = $("<div></div>").addClass("hover_action");
        // 			hoverAction.html(`
        //   <button type="button" class="btn btn-link text-info"><i class="zmdi zmdi-eye"></i></button>
        //   <button type="button" class="btn btn-link text-warning"><i class="zmdi zmdi-star"></i></button>
        //   <button type="button" class="btn btn-link text-danger"><i class="zmdi zmdi-delete"></i></button>
        // `);
        userItem.append(hoverAction);

        const userCard = $("<a></a>").addClass("card");
        userCard.html(`
          <div class="card-body">
            <div class="media">
              <div class="avatar me-3">
                <span class="status rounded-circle"></span>
                <img class="avatar rounded-circle" src="${user.headpic}" alt="avatar">
              </div>
              <div class="media-body overflow-hidden">
                <div class="d-flex align-items-center mb-1">
                  <h6 class="text-truncate mb-0 me-auto">${user.username}</h6>
<!--                  <p class="small text-muted text-nowrap ms-4 mb-0">${user.lastMessageTime}</p>-->
                </div>
<!--                这个地方以后可以放个性签名-->
                      <div class="text-truncate">最近活跃：${user.lastVisitTimeStr}</div>
              </div>
            </div>
          </div>
        `);
        userItem.append(userCard);
        userList.append(userItem);
      });
    },
    error: function (xhr, status, error) {
      console.error("There was a problem with the fetch operation:", error);
    }
  });
}
