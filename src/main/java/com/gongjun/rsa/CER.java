/*
package com.gongjun.rsa;

*/
/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 16:23 2021/6/23
 *//*

import cn.hutool.core.io.FileUtil;
import com.kinggrid.swxa.MyContentSigner;
import com.kinggrid.swxa.SwxaSM2Utils;
import com.sansec.jce.provider.JCESM2PublicKey;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;
import java.security.*;
import java.util.Calendar;
import java.util.Date;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import com.utils.Base64;
import java.security.*;
import java.io.*;
import java.security.cert.*;
import java.security.cert.Certificate;


*/
/**
 * Created by wangbeibei on 2019-4-17.
 * 注意x500NameBuilder.ans.1相关api
 *//*

public class CER {
    */
/**
     * 创建cer证书
     *//*

    public byte[]  createCer( int keyIndex) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("SM2", "SwxaJCE");
        kpg.initialize(keyIndex << 16);
        KeyPair kp = kpg.genKeyPair();
        JCESM2PublicKey publicKey = (JCESM2PublicKey) kp.getPublic();
        byte[] publicKeyData = publicKey.getEncoded();

        X500NameBuilder x500NameBuilder = new X500NameBuilder();
        x500NameBuilder.addRDN(BCStyle.CN, "测试");
        x500NameBuilder.addRDN(BCStyle.C, "CN");
        x500NameBuilder.addRDN(BCStyle.O, "王贝贝");
        x500NameBuilder.addRDN(BCStyle.OU, "乌鲁木齐");
        x500NameBuilder.addRDN(BCStyle.L, "新疆");

        X500Name x500Name = x500NameBuilder.build();
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());

        Calendar nowTime = Calendar.getInstance();
        Date notBefore = nowTime.getTime();
        nowTime.add(Calendar.YEAR, 3);
        Date notAfter = nowTime.getTime();

        ASN1Sequence primitive = (ASN1Sequence) DERSequence.fromByteArray(publicKeyData);
        SubjectPublicKeyInfo publicKeyInfo = new SubjectPublicKeyInfo(primitive);

        X509v3CertificateBuilder x509v3CertificateBuilder = new X509v3CertificateBuilder(x500Name, serial, notBefore,
                notAfter, x500Name, publicKeyInfo);

        // 基本约束
        BasicConstraints basicConstraints = new BasicConstraints(0);
        x509v3CertificateBuilder.addExtension(Extension.basicConstraints, false, basicConstraints);

        // 密钥用法
        DERBitString keyUsage = new DERBitString(192);
        x509v3CertificateBuilder.addExtension(Extension.keyUsage, false, keyUsage);

        // 头
        String str = "04C0";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(Hex.decode(str));
        messageDigest.update(publicKeyInfo.getEncoded());
        byte[] digest = messageDigest.digest();

        DEROctetString subjectKeyIdentifier = new DEROctetString(digest);
        x509v3CertificateBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);

        X509CertificateHolder certificateHolder = x509v3CertificateBuilder.build(new MyContentSigner());
        return certificateHolder.getEncoded();
    }




    */
/**
     * 解析cer证书
     *//*

    public static  void analysisCer(String  url) throws Exception {
        CertificateFactory cf;
        try {
            // 获取工厂实例
            cf = CertificateFactory.getInstance("X.509");
            // 用文件流读入证书
            FileInputStream in=new  FileInputStream(url);
            // 生成证书
            Certificate c=cf.generateCertificate(in);
            X509Certificate t=(X509Certificate)c;
            in.close();
            String s=c.toString();
            System.out.println("输出证书信息:\n"+s);
            System.out.println();
            System.out.println();
            System.out.println("版本号:"+t.getVersion());
            System.out.println("序列号:"+t.getSerialNumber().toString(16));
            System.out.println("签发者："+t.getIssuerDN());
            System.out.println("有效起始日期："+t.getNotBefore());
            System.out.println("有效终止日期："+t.getNotAfter());
            System.out.println("主体名："+t.getSubjectDN());
            System.out.println("签名算法："+t.getSigAlgName());
            System.out.println("签名："+t.getSignature().toString());
            PublicKey pk=t.getPublicKey();
            byte [] pkenc=pk.getEncoded();
            System.out.println("公钥:");
            for(int  i=0;i<pkenc.length;i++)System.out.print(pkenc[i]+",");
            System.out.println();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    */
/**
     * 创建证书
     * @throws Exception
     *//*

    public static void createCer() throws Exception {
        analysisCer("E:\\word\\王贝贝\\cert\\demo.cer");
        String bytes=SwxaSM2Utils.getIndexOfCSR(3,"cn");
        byte[] bytes1=bytes.getBytes();
        System.out.println(bytes);
        File P10Cert=new File("E:\\word\\goldgrid\\cert\\p10demo.csr");
        FileUtil.writeBytes(bytes1, P10Cert);
        byte[] cerByte=  SwxaSM2Utils.getIndexOfCer(3);
        File cerFile=new File("E:\\word\\goldgrid\\cert\\p10demo.cer");
        FileUtil.writeBytes(cerByte,cerFile);
    }

    */
/**
     * 解析证书
     *//*


    public static void anyP10() throws Exception {
        File P10Cert=new File("E:\\word\\goldgrid\\cert\\p10demo.csr");
        String P10= FileUtil.readString(P10Cert,"UTF-8");
        String contxtString= P10.substring(35,417);
        System.out.println(P10);
        System.out.println(contxtString);
        String contxtString="请自行更替";
        byte[] bytes= Base64.createBase64().decode(contxtString);
        PKCS10CertificationRequest Pkcs10=new PKCS10CertificationRequest(bytes);
        //主体
        X500Name  x500Name=  Pkcs10.getSubject();
        SubjectPublicKeyInfo subjectPublicKeyInfo=  Pkcs10.getSubjectPublicKeyInfo();
        //公钥
        String publicKeyData= subjectPublicKeyInfo.getPublicKeyData().getString();
        AlgorithmIdentifier algorithmIdentifier= Pkcs10.getSignatureAlgorithm();
        Pkcs10.getEncoded();
        Attribute[]  attributes=  Pkcs10.getAttributes();
        CertificationRequest certificationRequest=    Pkcs10.toASN1Structure();
        String Signature= certificationRequest.getSignature().getString();
        System.out.println(Signature);
        for (int i = 0; i <attributes.length ; i++) {
            System.out.println(attributes[i]);
        }
        System.out.println(x500Name.toString());
        System.out.println(publicKeyData);

    }
}*/
