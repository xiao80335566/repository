package xydproject.pluginutil.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xiaojun on 2017/5/27.
 * Object 对象格式的请求结果，
 */

public abstract class ObjectCallback<T> implements Callback<T> {
    public Class<T> clazz;
    
    public ObjectCallback() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        clazz = (Class<T>) params[0];
    }
}
