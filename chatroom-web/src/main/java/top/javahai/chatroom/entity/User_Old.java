package top.javahai.chatroom.entity;



/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-06-16 12:08:01
 */
public class User_Old {

    private Integer id;
    /**
    * 登录账号
    */
    private String username;
    /**
    * 昵称
    */
    private String nickname;
    /**
    * 密码
    */
    private String password;
    /**
    * 用户头像
    */
    private String userProfile;
    /**
    * 用户状态id
    */
    private Integer userStateId;
    /**
    * 是否可用
    */
    private Boolean isEnabled;
    /**
    * 是否被锁定
    */
    private Boolean isLocked;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public Integer getUserStateId() {
        return userStateId;
    }

    public void setUserStateId(Integer userStateId) {
        this.userStateId = userStateId;
    }


    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", userProfile='" + userProfile + '\'' +
                ", userStateId=" + userStateId +
                ", isEnabled=" + isEnabled +
                ", isLocked=" + isLocked +
                '}';
    }
}
