package demo.springframework.core.convert;

import com.sun.istack.internal.Nullable;

public interface ConversionService {
    boolean canConverter(@Nullable Class<?> source, Class<?> target);

    <T> T converter(Object source,Class<T> targetType);
}
