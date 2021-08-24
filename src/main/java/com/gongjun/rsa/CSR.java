/*
package com.gongjun.rsa;

*/
/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 16:18 2021/6/23
 *//*

import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;


import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.security.*;

*/
/**
 * Created by wangbeibei on 2019-4-17.
 * 注意PKCS10CertificationRequest类
 *//*

public class CSR {

    */
/**
     * 创建证书（p10)
     * @param keyIndex
     * @return
     * @throws Exception
     *//*

    public String createCsr(int keyIndex,String cn) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("SM2", "SwxaJCE");
        kpg.initialize(keyIndex << 16);
        KeyPair keyPair = kpg.genKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String sigAlg = "SM3WithSM2";
        String params = "CN=" + cn + ",O=王贝贝,L=乌鲁木齐,S=新疆,C=CN";
        X500Principal principal = new X500Principal(params);
        PKCS10CertificationRequest kpGen = new PKCS10CertificationRequest(sigAlg, principal, publicKey,
                new com.sansec.asn1.DERSet(), privateKey, null);
        byte[] c = kpGen.getEncoded();
        String csr = "-----BEGIN CERTIFICATE REQUEST-----\r\n";
        csr += new String(Base64.createBase64().encode(c));
        csr += "\r\n-----END CERTIFICATE REQUEST-----\r\n";
        return csr;
    }

    */
/**
     * 解析p10证书
     *//*

    public static void analysicCer(byte[] encoded) throws Exception {


        PKCS10CertificationRequest pkcs10CertificationRequest=new PKCS10CertificationRequest(encoded);
        //获取公钥
        SubjectPublicKeyInfo  subjectPublicKeyInfo=pkcs10CertificationRequest.getSubjectPublicKeyInfo();
        DERBitString publicKey= subjectPublicKeyInfo.getPublicKeyData();
        byte[] bytes= publicKey.getBytes();
        String publickey=   Base64.createBase64().encode(bytes);
        System.out.println("csr文件的公钥为:"+publickey);
        //获取主题
        X500Name subjectName= pkcs10CertificationRequest.getSubject();
        System.out.println("csr文件主体为:"+subjectName.toString());
    }
}*/
