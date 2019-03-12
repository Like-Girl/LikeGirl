/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package cn.likegirl.common.core.io;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * 配置文件加载（Boot）
 * @author ThinkGem
 * @version 2018-10-16
 */
public class PropertyLoader implements org.springframework.boot.env.PropertySourceLoader{
	
	private static boolean isLoadPropertySource = false;
	
	@Override
	public String[] getFileExtensions() {
		return new String[] { "properties", "yml" };
	}

	@Override
	public PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
		return null;
	}
}
