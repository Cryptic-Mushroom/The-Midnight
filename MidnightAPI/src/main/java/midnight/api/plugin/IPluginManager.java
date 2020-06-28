package midnight.api.plugin;

import java.util.List;

public interface IPluginManager {
    <T> List<T> getAllOfType(Class<T> type);
}
