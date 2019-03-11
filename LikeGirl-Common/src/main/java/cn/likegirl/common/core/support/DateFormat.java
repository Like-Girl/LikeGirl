package cn.likegirl.common.core.support;

import cn.likegirl.common.core.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间格式化扩展
 *
 * @author ShenHuaJie
 * @since 2017年6月30日 下午7:40:00
 */
@SuppressWarnings("serial")
public class DateFormat extends SimpleDateFormat {

    public DateFormat(String pattern) {
        super(pattern);
    }

    @Override
    public Date parse(String source) throws ParseException {
        return DateUtil.stringToDate(source);
    }
}
