package demo.springframework.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Set;

public interface GenericConverter {

    Set<ConvertiblePair> getConvertibleTypes();

    Object convert(Object source, Class sourceType, Class targetType);

    final class ConvertiblePair{
        private final Class<?> sourceType;
        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType,"sourceType can not be null");
            Assert.notNull(targetType,"targetType can not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public Class<?> getTargetType() {
            return targetType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            ConvertiblePair that = (ConvertiblePair) o;

            return this.sourceType.equals(that.sourceType) && this.targetType.equals(that.sourceType);
        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }
}
