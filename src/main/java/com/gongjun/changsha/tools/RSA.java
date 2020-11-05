package com.gongjun.changsha.tools;

import lombok.extern.slf4j.Slf4j;
import net.iharder.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:RSA加密算法
 * @Author: GongJun
 * @Date: Created in 10:14 2020/11/5
 */
@Slf4j
public class RSA {
    private final String RSA_ALGORITHM = "RSA";
    private final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private final String RSA_PUBLIC_KEY = "RSAPublicKey";
    private final String RSA_PRIVATE_KEY = "RSAPrivateKey";
    //获取秘钥
    public String encodeBase64(byte[] binaryData) {
        return Base64.encodeBytes(binaryData);
    }

    public byte[] decodeBase64(String encoded) throws IOException {
        return Base64.decode(encoded);
    }


    //RSA算法是由私钥、公钥对数据进行操作，所以我们首先得先创建出一对密钥
    /**
     * 获取RSA算法私钥、公钥
     */
    public Map<String, Object> getKey() throws NoSuchAlgorithmException {
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGen.initialize(1024);//1024代表密钥二进制位数
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    //对外抛出两个方法用来从Map里面取值，当然也可以直接通过Key取值
    /**
     * 获取公钥
     */
    public String getPublicKey(Map<String, Object> map) {
        Key key = (Key) map.get(RSA_PUBLIC_KEY);
        return encodeBase64(key.getEncoded());
    }

    /**
     * 获取私钥
     */
    public String getPrivateKey(Map<String, Object> map) {
        Key key = (Key) map.get(RSA_PRIVATE_KEY);
        return encodeBase64(key.getEncoded());
    }

    //私钥加密
    /**
     * 使用私钥对数据进行加密
     */
    public byte[] encryptPrivateKey(byte[] binaryData, String privateKey) throws Exception {
        byte[] keyBytes = decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        Key priKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        return cipher.doFinal(binaryData);
    }

    //公钥加密
    /**
     * 使用公钥对数据进行加密
     */
    public byte[] encryptPublicKey(byte[] binaryData, String publicKey) throws Exception {
        byte[] keyBytes = decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        Key pubKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(binaryData);
    }
    //私钥解密
    /**
     * 使用私钥对数据进行解密
     */
    public byte[] decryptPrivateKey(byte[] binaryData, String privateKey) throws Exception {
        byte[] keyBytes = decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        Key priKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(binaryData);
    }
    //公钥解密
    /**
     * 使用公钥对数据进行解密
     */
    public byte[] decryptPublicKey(byte[] binaryData, String publicKey) throws Exception {
        byte[] keyBytes = decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        Key pubKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(binaryData);
    }
    //私钥签名
    /**
     * 使用私钥对数据进行签名
     */
    public String sign(byte[] binaryData, String privateKey) throws Exception {
        byte[] keyBytes = decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(binaryData);
        return encodeBase64(signature.sign());
    }
    // 公钥验证签名

    /**
     * 使用公钥对数据签名进行验证
     */
    public boolean verify(byte[] binaryData, String publicKey, String sign) throws Exception {
        byte[] keyBytes = decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(binaryData);
        return signature.verify(decodeBase64(sign));
    }


    @Test
    public void test() throws Exception{
        Map<String,Object> key = this.getKey();
        log.info("公钥({}):{}",RSA_PUBLIC_KEY,key.get(RSA_PUBLIC_KEY));
        log.info("私钥({}):{}",RSA_PRIVATE_KEY,key.get(RSA_PRIVATE_KEY));
    }
}
