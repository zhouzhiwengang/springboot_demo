package com.zzg.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.NumberUtils;

public class ObjectToIntegerConverterFactory implements ConverterFactory<Integer, Integer> {

	public <T extends Integer> Converter<Integer, T> getConverter(Class<T> targetType) {
		// TODO Auto-generated method stub
		return new ObjectToInteger(targetType);
	}
	
	// 私有内部类：实现Converter接口。用泛型边界约束一类类型
		private static final class ObjectToInteger<T extends Integer> implements Converter<T, Integer> {

			private final Class<T> targetType;
			public ObjectToInteger(Class<T> targetType) {
				this.targetType = targetType;
			}


			public Integer convert(T source) {
				// TODO Auto-generated method stub
				if(source == null) {
					return 0;
				}
				return NumberUtils.parseNumber(String.valueOf(source), this.targetType);
			}
		}

}
