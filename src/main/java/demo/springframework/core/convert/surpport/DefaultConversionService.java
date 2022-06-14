package demo.springframework.core.convert.surpport;

import demo.springframework.core.convert.converter.ConverterRegistry;

public class DefaultConversionService extends GenericConvertionService{

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }
}
