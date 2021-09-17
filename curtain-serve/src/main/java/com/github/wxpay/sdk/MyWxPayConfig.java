package com.github.wxpay.sdk;

import java.io.InputStream;

/**
 * @Description 微信支付配置类
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
public class MyWxPayConfig extends WXPayConfig {

    /**
     * AppID
     * @return
     */
    @Override
    public String getAppID() {
        return "wx4xxxxxxx";
    }

    /**
     * 商户号
     * @return
     */
    @Override
    public String getMchID() {
        return "1589111117";
    }

    /**
     * 商户平台Key
     * @return
     */
    @Override
    public String getKey() {
        return "yDDDsDv6kFG1qXXX6jP";
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
}
