package cn.likegirl.common.core.map;

import cn.likegirl.common.core.utils.ExceptionUtils;
import cn.likegirl.common.core.util.InstanceUtil;
import cn.likegirl.common.core.util.PropertiesUtil;
import cn.likegirl.common.core.web.http.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @since 2018年6月14日 下午3:48:25
 */
public class GaodeMapUtil {
    private static final Logger logger = LogManager.getLogger();

    /** 根据地址获取坐标 */
    public static Map<String, Object> getCoordinateByAddress(String address, String city) {
        String url = PropertiesUtil.getString("gaode.map.url");
        String key = PropertiesUtil.getString("gaode.map.key");
        Map<String, Object> map = InstanceUtil.newHashMap();

        try {
            url = MessageFormat.format(url, key, URLEncoder.encode(address, "UTF-8"), URLEncoder.encode(city, "UTF-8"));
            String result = HttpClientUtils.get(url);
            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray pois = (JSONArray)jsonObject.get("pois");
            if (pois.size() > 0) {
                JSONObject jobj = (JSONObject)pois.get(0);
                String location = jobj.getString("location");
                String[] locs = location.split(",");
                map.put("xIndex", locs[0]);
                map.put("yIndex", locs[1]);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtils.getStackTraceAsString(e));
        }
        return map;
    }
}
