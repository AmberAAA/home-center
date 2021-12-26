package top.anborong.home.component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.anborong.home.service.RestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.aliyun.tea.*;
import com.aliyun.alidns20150109.*;
import com.aliyun.alidns20150109.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;



@Component
public class GetIp {

    private static Logger logger = LoggerFactory.getLogger(GetIp.class);

    @Autowired
    private RestService restService;

    private static String accessKeyId = "LTAI5tATXtbGertn6ZgCu1B3";
    private static String accessKeySecret = "Pzz16vbivCRE5HrOH5bhF0PxgDmekf";
    private String recordId = "699689872921549824";
    private String RR = "srjc";

    private static com.aliyun.alidns20150109.Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "alidns.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.alidns20150109.Client(config);
    }


    public static List<String> getIps(String ipString){
        String regEx="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        List<String> ips = new ArrayList<String>();
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(ipString);
        while (m.find()) {
            String result = m.group();
            ips.add(result);
        }
        return ips;
    }

    public String getHostIp () {
        String body = restService.restTemplate.getForObject("http://cip.cc", String.class);
        List<String> ips = getIps(body);
        String ip = ips.get(0);
        logger.info("HOST IP IS: {}", ip);
        return ip;
    }

    public void setIp (String ip) {
        try {
            com.aliyun.alidns20150109.Client client = createClient();
            DescribeDomainRecordInfoRequest describeDomainRecordInfoRequest = new DescribeDomainRecordInfoRequest()
                    .setRecordId(recordId);
            DescribeDomainRecordInfoResponse recordInfo = client.describeDomainRecordInfo(describeDomainRecordInfoRequest);
            if (recordInfo.body.getValue().equals(ip)) {
                logger.debug("IP NOT CHANGE, NO NEED UPDATE");
            } else {
                logger.info("IP CHANGED");
                UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest()
                        .setRecordId(recordId)
                        .setRR(RR)
                        .setType("A")
                        .setValue(ip)
                        .setTTL(600L);
                UpdateDomainRecordResponse record = client.updateDomainRecord(updateDomainRecordRequest);
                logger.info("IP UPDATE SUCCESS: record id {}, ip {}", record.getBody().getRecordId(), ip);
            }
        } catch (Exception e) {
            logger.error("SetIp Fail: {}", e);
        }
    }



    @Scheduled(cron = "1 */1 * * * ?")
    public void ip () {
        String ip = getHostIp();
        setIp(ip);
    }
}
