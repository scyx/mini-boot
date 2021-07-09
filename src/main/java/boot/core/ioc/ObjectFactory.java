package boot.core.ioc;

/**
 * @author cyx
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject();
}
