package https;

import net.bytebuddy.asm.Advice;
import org.apache.cxf.Bus;
import org.apache.cxf.configuration.jsse.TLSClientParameters;

import java.lang.reflect.Method;

/**
 * @author licb
 * @create 2018-04-27 11:26
 **/
public class URLConnectionHTTPConduitAdviceInterceptor {
    @Advice.OnMethodEnter
    static void interceptor(@Advice.Origin Class clazz,
                            @Advice.This Object obj,
                            @Advice.FieldValue("bus") Bus bus,
                            @Advice.FieldValue("tlsClientParameters") TLSClientParameters tlsClientParametersValue

    ) throws Exception {
        TLSClientParameters tlsClientParameters = (TLSClientParameters) bus.getProperty("clientParameters");
        if (tlsClientParameters != null && tlsClientParametersValue == null) {
            try {
                Method setTlsClientParametersMethod = clazz.getMethod("setTlsClientParameters", TLSClientParameters.class);
                setTlsClientParametersMethod.invoke(obj, tlsClientParameters);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
