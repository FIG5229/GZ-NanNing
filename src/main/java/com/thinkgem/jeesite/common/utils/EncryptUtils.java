package com.thinkgem.jeesite.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.commons.codec.Charsets;

public  abstract class EncryptUtils {

    public static String encrypt3DES(String body , String key){
        byte[] SecureKey = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue(),key.getBytes(Charsets.UTF_8)).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.DESede, SecureKey);
        byte[] encrypt = aes.encrypt(body);
        return Base64.encode(encrypt);
    }

    public static String decrypt3DES(String body , String key){
        byte[] SecureKey = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue(),key.getBytes(Charsets.UTF_8)).getEncoded();
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DESede, SecureKey);
        byte[] encrypt = des.decrypt(Base64.decode(body));
        return new String(encrypt, Charsets.UTF_8);
    }
}
