package domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JIE WU
 * @create 2018-04-01
 * @desc tauser用户
 **/
public class UserDomain {
    private Long userId;// 用户id
    private String name;// 用户名称
    private String sex;// 性别，默认0，无性别
    private String loginId;// 登录id
    private String password;// 密码
    private String passwordDefaultNumber;// 口令错误次数
    private String isLock;// 锁定标志 0没有锁定
    private String effective;// 有效标志 0代表有效
    private String createUser;// 默认1
    private String directOrgId;// 所属组织机构
    private String typeFlag;// 类标识 默认1
    private String aac147;// 证件号码
    private String aac058;//证件类型 1
    private String userDerive;// 用户来源(01:内网经办人员,02:互联网注册用户)
    private String aac057;// 手机号码

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordDefaultNumber() {
        return passwordDefaultNumber;
    }

    public void setPasswordDefaultNumber(String passwordDefaultNumber) {
        this.passwordDefaultNumber = passwordDefaultNumber;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDirectOrgId() {
        return directOrgId;
    }

    public void setDirectOrgId(String directOrgId) {
        this.directOrgId = directOrgId;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }

    public String getAac147() {
        return aac147;
    }

    public void setAac147(String aac147) {
        this.aac147 = aac147;
    }

    public String getAac058() {
        return aac058;
    }

    public void setAac058(String aac058) {
        this.aac058 = aac058;
    }

    public String getUserDerive() {
        return userDerive;
    }

    public void setUserDerive(String userDerive) {
        this.userDerive = userDerive;
    }

    public String getAac057() {
        return aac057;
    }

    public void setAac057(String aac057) {
        this.aac057 = aac057;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("userId", getUserId());
        returnMap.put("name", getName());
        returnMap.put("sex", getSex());
        returnMap.put("loginId", getLoginId());
        returnMap.put("password", getPassword());
        returnMap.put("passwordDefaultNumber", getPasswordDefaultNumber());
        returnMap.put("isLock", getIsLock());
        returnMap.put("effective", getEffective());
        returnMap.put("createUser", getCreateUser());
        returnMap.put("directOrgId", getDirectOrgId());
        returnMap.put("typeFlag", getTypeFlag());
        returnMap.put("aac147", getAac147());
        returnMap.put("aac058", getAac058());
        returnMap.put("userDerive", getUserDerive());
        returnMap.put("aac057", getAac057());
        return returnMap;
    }
}
