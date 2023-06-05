package cn.wx.elephant.core.idwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.SystemClock;

/**
 * @author weixin 2021/3/17 3:13 下午
 */
public class SnowflakeId implements IdWorker {

    private  static final Logger logger = LoggerFactory.getLogger(SnowflakeId.class);

    /** 初始偏移时间戳 */
    private static final long OFFSET = 1546300800L;

    /** 机器id (0~15 保留 16~31作为备份机器) */
    private  long WORKER_ID;
    /** 机器id所占位数 (5bit, 支持最大机器数 2^7 = 128)*/
    private static final long WORKER_ID_BITS = 7L;
    /** 自增序列所占位数 (14bit, 支持最大每秒生成 2^14 = 16384) */
    private static final long SEQUENCE_ID_BITS = 14L;
    /** 机器id偏移位数 */
    private static final long WORKER_SHIFT_BITS = SEQUENCE_ID_BITS;
    /** 自增序列偏移位数 */
    private static final long OFFSET_SHIFT_BITS = SEQUENCE_ID_BITS + WORKER_ID_BITS;
    /** 机器标识最大值 (2^7- 1 = 127) */
    private static final long WORKER_ID_MAX = ((1 << WORKER_ID_BITS) - 1);
    /** 备份机器ID开始位置 (2^7 / 2 = 64) */
    private static final long BACK_WORKER_ID_BEGIN = (1 << WORKER_ID_BITS) >> 1;
    /** 自增序列最大值 (2^14 - 1 = 16384) */
    private static final long SEQUENCE_MAX = (1 << SEQUENCE_ID_BITS) - 1;
    /** 发生时间回拨时容忍的最大回拨时间 (秒) */
    private static final long BACK_TIME_MAX = 1L;

    /** 上次生成ID的时间戳 (秒) */
    private static long lastTimestamp = 0L;
    /** 当前秒内序列 (2^14)*/
    private static long sequence = 0L;
    /** 备份机器上次生成ID的时间戳 (秒) */
    private static long lastTimestampBak = 0L;
    /** 备份机器当前秒内序列 (2^14)*/
    private static long sequenceBak = 0L;


    public SnowflakeId() {
        WORKER_ID = 1;
    }


    /**
     * 获取自增序列
     * @return long
     */
    public synchronized long nextId() {
        return nextId(SystemClock.now() / 1000);
    }

    /**
     * 主机器自增序列
     * @param timestamp 当前Unix时间戳
     * @return long
     */
    private  synchronized long nextId(long timestamp) {
        // 时钟回拨检查
        if (timestamp < lastTimestamp) {
            // 发生时钟回拨
            logger.error("时钟回拨, 启用备份机器ID: now: [{}] last: [{}]", timestamp, lastTimestamp);
            return nextIdBackup(timestamp);
        }

        // 开始下一秒
        if (timestamp != lastTimestamp) {
            lastTimestamp = timestamp;
            sequence = 0L;
        }
        if (0L == (++sequence & SEQUENCE_MAX)) {
            // 秒内序列用尽
            logger.error("秒内[{}]序列用尽, 启用备份机器ID序列", timestamp);
            sequence--;
            return nextIdBackup(timestamp);
        }

        return ((timestamp - OFFSET) << OFFSET_SHIFT_BITS) | (WORKER_ID << WORKER_SHIFT_BITS) | sequence;
    }

    /**
     * 备份机器自增序列
     * @param timestamp timestamp 当前Unix时间戳
     * @return long
     */
    private  long nextIdBackup(long timestamp) {
        if (timestamp < lastTimestampBak) {
            if (lastTimestampBak - SystemClock.now() / 1000 <= BACK_TIME_MAX) {
                timestamp = lastTimestampBak;
            } else {
                throw new RuntimeException(String.format("时钟回拨: now: [%d] last: [%d]", timestamp, lastTimestampBak));
            }
        }

        if (timestamp != lastTimestampBak) {
            lastTimestampBak = timestamp;
            sequenceBak = 0L;
        }

        if (0L == (++sequenceBak & SEQUENCE_MAX)) {
            // 秒内序列用尽
            logger.error("秒内[{}]序列用尽, 备份机器ID借取下一秒序列", timestamp);
            return nextIdBackup(timestamp + 1);
        }

        return ((timestamp - OFFSET) << OFFSET_SHIFT_BITS) | ((WORKER_ID ^ BACK_WORKER_ID_BEGIN) << WORKER_SHIFT_BITS) | sequenceBak;
    }
}
