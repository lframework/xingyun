var MAGIC_EDITOR_CONFIG = {
  title: '在线开发平台',
  header: {
    skin: false,    // 屏蔽皮肤按钮
    document: false,    // 屏蔽文档按钮
    repo: false,    // 屏蔽gitee和github
    qqGroup: false  // 屏蔽加入QQ群
  },
  getMagicTokenValue: function(){
    return this.getCookie('X-Auth-Token');
  },
  getCookie: function(name) {
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
      var cookie = cookies[i].trim().split('=');
      if (cookie[0] === name) {
        return decodeURIComponent(cookie[1]);
      }
    }
  }
}