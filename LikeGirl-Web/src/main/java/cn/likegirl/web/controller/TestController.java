package cn.likegirl.web.controller;

import cn.likegirl.common.core.util.DateUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: TestController
 * @description: TODO
 * @date 2019/3/11 14:42
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String get(){
        Calendar calendar = DateUtil.getCalendar(new Date());
        return "hello,word; " + calendar.getTimeInMillis();
    }
}
