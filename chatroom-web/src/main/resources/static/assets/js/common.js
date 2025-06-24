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
