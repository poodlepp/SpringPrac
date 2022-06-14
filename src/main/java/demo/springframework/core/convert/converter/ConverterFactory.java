package demo.springframework.core.convert.converter;

/**
 * 转换器工厂
 * S锁定，根据T获取对应的转换器（T是R子类）
 * @param <S>
 * @param <R>
 */
public interface ConverterFactory<S,R> {
    <T extends R> Converter<S,T> getConverter(Class<T> targetType);
}
