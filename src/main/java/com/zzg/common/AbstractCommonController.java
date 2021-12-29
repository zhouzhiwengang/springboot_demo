package com.zzg.common;

import java.util.Map;

import org.springframework.core.convert.converter.ConverterFactory;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzg.common.converter.ObjectToIntegerConverterFactory;

public abstract class AbstractCommonController<T> {
	public static final String pageNo = "pageNo";
	public static final String pageSize = "pageSize";
	public Page<T> getPage(Map map){
		ConverterFactory<Integer, Integer> converterFactory = new ObjectToIntegerConverterFactory();
		Integer no = converterFactory.getConverter(Integer.class).convert(Integer.valueOf(String.valueOf(map.get(pageNo))));
		Integer size = converterFactory.getConverter(Integer.class).convert(Integer.valueOf(String.valueOf(map.get(pageSize))));
		return new Page<T>(no,size);
	}
	
	
}
