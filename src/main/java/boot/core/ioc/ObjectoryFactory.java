package boot.core.ioc;

/**
 * @author cyx
 */
@FunctionalInterface
public interface ObjectoryFactory<T> {
    T getObject();
}
