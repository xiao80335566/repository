package xydproject.pluginutil.net;

/**
 * Created by xiaojun on 2017/5/27.
 * 返回结果接口
 */
public interface Callback<T> {

    /**
     * 请求成功
     */
    int SUCCESS = 0;

    /**
     * 网络异常
     */
    int NETWORK_CONNECT_ERROR = 0x1;

    /**
     * 服务器拒绝访问
     */

    int SERVER_REJECT = 0X2;

    /**
     * 数据解析错误
     */
    int PARSER_ERROR = 0X3;

    /**
     * 请求超时
     */
    int REQUEST_TIME_OUT = 0X4;

    /**
     * 已取消
     */
    int CANCELLED = 0X5;

    /**
     * 登錄超时/cookie过期
     */
    public static int Login_time_out = 0x6;

    /**
     * 其他错误
     */
    int OTHER_ERROR = 0X7;

    /**
     * @param status  状态值
     * @param message 错误信息
     * @param result  数据支持（Object，String，JsonObject）
     */
    void onResponse(int status, String message, T result);
}
