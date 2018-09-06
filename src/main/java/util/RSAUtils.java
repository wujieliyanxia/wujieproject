package util;

import com.yinhai.bcpcs.exceptions.ClientException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;

public class RSAUtils {
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static PrivateKey pk;

    public RSAUtils() {
    }

    private static void loadPrivateKey() throws ClientException {
        pk = getPrivateKey();
    }

    private static InputStream getResourceAsStream(String var0) throws ClientException {
        InputStream var1 = null;
        ClassLoader var2 = RSAUtils.class.getClassLoader();
        if (var2 != null) {
            var1 = var2.getResourceAsStream(var0);
        }

        if (var1 == null) {
            var1 = ClassLoader.getSystemResourceAsStream(var0);
        }

        if (var1 == null) {
            throw new ClientException("请将密钥文件bcp.keystore放到工程classpath目录！");
        } else {
            return var1;
        }
    }

    public static PrivateKey getPrivateKey() throws ClientException {
        Object var0 = null;
        PrivateKey var1 = null;

        PEMReader var3;
        try {
            InputStream var2 = getResourceAsStream("bcp.keystore");
            if (var2 != null) {
                Security.addProvider(new BouncyCastleProvider());
                var3 = new PEMReader(new InputStreamReader(var2));
                KeyPair var4 = (KeyPair) var3.readObject();
                var1 = var4.getPrivate();
                var3.close();
                return var1;
            }

            var3 = null;
        } catch (Exception var13) {
            var13.printStackTrace();
            throw new ClientException(var13.getMessage());
        } finally {
            if (var0 != null) {
                try {
                    ((ObjectInputStream) var0).close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

        return null;
    }

    public static String sign(String var0, PrivateKey var1) throws ClientException {
        Security.addProvider(new BouncyCastleProvider());

        try {
            Signature var2 = Signature.getInstance("SHA1WithRSA");
            var2.initSign(var1);
            var2.update(var0.getBytes("UTF-8"));
            byte[] var3 = var2.sign();
            return Hex.encodeHexString(var3);
        } catch (Exception var4) {
            throw new ClientException("数据签名失败!");
        }
    }

    public static String sign(String var0) throws ClientException {
        if (pk == null) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++pk为null");
            throw new ClientException("加载密钥文件失败！");
        } else {
            return sign(var0, pk);
        }
    }

    static {
        try {
            loadPrivateKey();
        } catch (ClientException var1) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++loadPrivateKey方法出错");
            var1.printStackTrace();
        }

    }
}
