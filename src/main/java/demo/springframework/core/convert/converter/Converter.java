package demo.springframework.core.convert.converter;

/**
 * 类型转换处理接口
 * @param <T>
 * @param <S>
 */
public interface Converter <S,T>{
    T convert(S source);
}
