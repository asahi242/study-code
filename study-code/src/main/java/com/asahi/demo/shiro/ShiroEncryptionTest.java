package com.asahi.demo.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import java.security.Key;

/**
 *  shiro编码加密
 */
public class ShiroEncryptionTest {
    public static void main(String[] args) {

        String str = "hello";
        System.out.println("base64 编码/解码操作：");
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
        System.out.println(base64Encoded+" "+str2);

        System.out.println("16进制字符串编码/解码操作：");
        String hexEncoded = Hex.encodeToString(str.getBytes());
        String str3 = new String(Hex.decode(hexEncoded.getBytes()));
        System.out.println(hexEncoded+" "+str3);

        System.out.println("使用md5生成相应的散列数据：");
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();
        System.out.println(str+"-"+salt+"： "+md5);

        System.out.println("使用sha256生成相应的散列数据：");
        String sha256 = new Sha256Hash(str, salt).toString();
        System.out.println(str+"-"+salt+"： "+sha256);

        System.out.println("shiro提供通用的散列支持：");
        String simpHash = new SimpleHash("SHA-256", str, salt).toString();
        System.out.println(str+"-"+salt+"： "+simpHash);

        System.out.println("使用shiro提供的HashService：");
        DefaultHashService hashService = new DefaultHashService();//默认算法 SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123"));//私盐 默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐 默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐，默认就这个
        hashService.setHashIterations(1);//生成Hash值得迭代次数
        HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(str))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(str+": "+hex);

        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String hex1 = randomNumberGenerator.nextBytes().toHex();
        System.out.println(str+": "+hex1);

        System.out.println("对称式加密 AES算法实现：");
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);//设置key长度
        //生成key
        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        //加密
        String encrpText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String aes = new String(aesCipherService.decrypt(Hex.decode(encrpText), key.getEncoded()).getBytes());
        System.out.println(encrpText+" "+aes);

        System.out.println("生成密码散列值：");
        String algorithmName = "md5";
        String username = "wu";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;
        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(password+"  "+encodedPassword);
    }
}
