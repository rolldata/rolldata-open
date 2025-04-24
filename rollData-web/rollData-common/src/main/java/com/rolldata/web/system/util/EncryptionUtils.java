package com.rolldata.web.system.util;

import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.SysPropertiesUtil;
import com.rolldata.web.system.pojo.UserDetailedJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public class EncryptionUtils {

    private static final Logger log = LogManager.getLogger(EncryptionUtils.class);

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String SECRET_KEY = "40nYN4GEXF3Yxk10";

    private static final String SEEYON_REST_PASSWORD;

    private static final String USER = SysPropertiesUtil.getConfig("seeyon.rest.user");

    static {
        String configPassword = SysPropertiesUtil.getConfig("seeyon.rest.password");
        SEEYON_REST_PASSWORD = createMD5(USER, configPassword);
    }

    public static String encrypt(String userMd5Pwd, String input) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String input) throws Exception {

        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encryptedBytes = Base64.getUrlDecoder().decode(input);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void userAuth(String tn) throws Exception {

        String data = EncryptionUtils.decrypt(tn);
        UserDetailedJson userInfo = JsonUtil.fromJson(data, UserDetailedJson.class);
        userAuth(userInfo);
    }

    public static void userAuth(UserDetailedJson userInfo) throws Exception {

        Objects.requireNonNull(userInfo, "认证失败,接口参数错误");
        StringUtil.requireNonNull(USER, "配置文件未配置集成用户");
        StringUtil.requireNonNull(SEEYON_REST_PASSWORD, "配置文件未配置集成用户密码");
        if (!USER.equals(userInfo.getUserCde()) ||
            !SEEYON_REST_PASSWORD.equals(userInfo.getPassword())) {
            throw new Exception("认证失败,用户或密码匹配错误");
        }
    }

    public static void seeyonCallBack(UserDetailedJson userInfo, String userCode) throws Exception {

        String tokenUrl = userInfo.getTokenUrl();
        String error = "签证验证失败";
        StringUtil.requireNonNull(userCode, error);
        try {
            String address = SysPropertiesUtil.getConfig("seeyon.rest.project.address");
            String oaUserCode = restTemplate.getForObject(address + tokenUrl, String.class);
            if (null == oaUserCode ||
                !userCode.equals(oaUserCode.trim())) {
                throw new Exception(error);
            }
        } catch (RestClientException e) {
            throw new Exception("OA地址调用失败", e);
        }
    }

    public static UserDetailedJson getUserInfo(String tn) throws Exception {

        StringUtil.requireNonNull(tn, "用户认证参数为空");
        String data = EncryptionUtils.decrypt(tn);
        UserDetailedJson userInfo = JsonUtil.fromJson(data, UserDetailedJson.class);
        return userInfo;
    }

    public static UserDetailedJson validUser(String tn) throws Exception {

        UserDetailedJson userInfo = getUserInfo(tn);
        try {
            EncryptionUtils.userAuth(userInfo);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("认证报错提示:", e);
        }
        return userInfo;
    }

     public static String createMD5(String inStr, String seed) {

        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(seed.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest(inStr.getBytes());
            outStr = bytetoString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return outStr;
    }

    public static String bytetoString(byte[] digest) {

        StringBuilder str = new StringBuilder();
        String tempStr = "";
        for(int i = 0; i < digest.length; ++i) {
            tempStr = Integer.toHexString(digest[i] & 255);
            if (tempStr.length() == 1) {
                str.append("0").append(tempStr);
            } else {
                str.append(tempStr);
            }
        }
        return str.toString().toLowerCase();
    }

    public static void main(String[] args) throws Exception {

        String plainText = "{\"userCde\":\"rolldataanalysis\",\"password\":\"6d6270b76202f22a713e88ff0534451c\"," +
                " \"tokenUrl\":\"/thirdpartyController.do?ticket=557356004028596295\"}";
        String encryptedText = encrypt("userMd", plainText);
        System.out.println("Encrypted Text: " + encryptedText);

//        String decryptedText = decrypt(encryptedText);
        String decryptedText = decrypt("1");
        System.out.println("Decrypted Text: " + decryptedText);

        System.out.println(encrypt("", "guxiaokai"));
    }
}
