package cn.wx.elephant.core.idwork;

/**
 * @author weixin 2023/6/2 4:02 PM
 */
public class IdWorkHandler {

    private volatile static IdWorker idWorker = null;

    public static IdWorker getInstance(){
        if(idWorker == null){
            synchronized (IdWorker.class){
                if(idWorker == null){
                    idWorker = new SnowflakeId();
                }
            }
        }
        return idWorker;
    }
}
