package https;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author JIE WU
 * @create 2018-05-07
 * @desc 爬取网站的证书
 **/
public class HttpsCertGenetator {
    // 爬取网页证书
    public static String generateCert(String httpsUrl, String certBase64) {
        // 判断是否https
        if (StringUtils.isBlank(httpsUrl) || (!httpsUrl.startsWith("https") && !httpsUrl.startsWith("https4"))) {
            throw new IllegalArgumentException("不是https地址！");
        }
        // https://139.215.206.92:9222/services/ESBDockGovHallServer?wsdl变成
        // 139.215.206.92:9222/services/ESBDockGovHallServer?wsdl
        String tempUrl = httpsUrl.replace("https://", "").replace("https4://", "");
        int separatorIndex = tempUrl.indexOf("/");
        String hostAndPort = separatorIndex == -1 ? tempUrl : tempUrl.substring(0, separatorIndex);
        // 139.215.206.92:9222
        String[] splitArray = hostAndPort.split("[:]");
        String host = "";
        //默认ssl端口是443
        int port = 443;
        if (splitArray.length == 2) {
            // 139.215.206.92
            host = splitArray[0];
            // 9222
            port = Integer.valueOf(splitArray[1]);
        } else {
            host = splitArray[0];
        }
        // java中cacerts证书库的默认密码changeit
        char[] passphrase = "changeit".toCharArray();
        // 文件名139.215.206.92_9222.cert
        String certFileName = (splitArray.length == 2 ? splitArray[0] + "_" + splitArray[1] : splitArray[0]) + ".cert";
        // 获取完整项目路径+WEB-INF/certs
        File certsDir = new File("D:\\ideaWorkspace\\wujieproject\\src\\main\\webapp\\WEB-INF\\certs");
        if (!certsDir.exists()) {
            certsDir.mkdirs();
        }
        //首次装载要创建证书文件
        File certFile = new File(certsDir, certFileName);
        if (certFile.isFile()) {
            // 有证书的话，返回证书路径
            return certFile.getAbsolutePath();
        }
        // 不为空的话
        if (StringUtils.isNotBlank(certBase64)) {
            try {
                OutputStream outputStream = new FileOutputStream(certFile);
                IOUtils.write(Base64.decodeBase64(certBase64), outputStream);
                IOUtils.closeQuietly(outputStream);
            } catch (IOException e) {
                throw new IllegalArgumentException("生成证书失败！");
            }
            return certFile.getAbsolutePath();
        } else {
            // 读取放在C:/Program Files/Java/jdk1.8.0_144/jre/lib/security/jssecacerts/cacerts路径下的证书库
            File dir = new File(System.getProperty("java.home") + File.separatorChar
                    + "lib" + File.separatorChar + "security");
            certFile = new File(dir, "jssecacerts");
            if (!certFile.isFile()) {
                certFile = new File(dir, "cacerts");
            }

            try {
                System.out.println("Loading KeyStore " + certFile + "...");
                InputStream in = new FileInputStream(certFile);
                // 放入KeyStore中
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                ks.load(in, passphrase);
                in.close();

                SSLContext context = SSLContext.getInstance("TLS");
                TrustManagerFactory tmf =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(ks);
                X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
                SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
                context.init(null, new TrustManager[]{tm}, null);
                SSLSocketFactory factory = context.getSocketFactory();

                System.out.println("Opening connection to " + host + ":" + port + "...");
                SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
                socket.setSoTimeout(10000);
                try {
                    System.out.println("Starting SSL handshake...");
                    socket.startHandshake();
                    socket.close();
                    System.out.println("No errors, certificate is already trusted");
                } catch (SSLException e) {
                    e.printStackTrace();
                    System.out.println("first get certificate");
                }

                X509Certificate[] chain = tm.chain;
                if (chain == null) {
                    throw new IllegalArgumentException("无法获取服务器端证书！");
                }
                System.out.println("Server sent " + chain.length + " certificate(s):");
                MessageDigest sha1 = MessageDigest.getInstance("SHA1");
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                for (int i = 0; i < chain.length; i++) {
                    X509Certificate cert = chain[i];
                    System.out.println
                            (" " + (i + 1) + " Subject " + cert.getSubjectDN());
                    System.out.println("   Issuer  " + cert.getIssuerDN());
                    sha1.update(cert.getEncoded());
                    System.out.println("   sha1    " + toHexString(sha1.digest()));
                    md5.update(cert.getEncoded());
                    System.out.println("   md5     " + toHexString(md5.digest()));
                }
                int k = 0;
                X509Certificate cert = chain[k];
                String alias = host + "-" + (k + 1);
                ks.setCertificateEntry(alias, cert);
                File outFile = new File(certsDir, certFileName);
                OutputStream out = new FileOutputStream(outFile);
                ks.store(out, passphrase);
                out.close();
                return outFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("获取证书失败->" + e.getMessage());
            }
        }
    }

    public static String generateCert(String httpsUrl) {
        return generateCert(httpsUrl, null);
    }


    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
        for (int b : bytes) {
            b &= 0xff;
            sb.append(HEXDIGITS[b >> 4]);
            sb.append(HEXDIGITS[b & 15]);
            sb.append(' ');
        }
        return sb.toString();
    }

    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm;
        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }
}
