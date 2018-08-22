package me.silentdoer.demoshardbatisfenbiao.support.db.mybatis;

import com.google.code.shardbatis.strategy.ShardStrategy;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demoshardbatisfenbiao.api.model.TbUserSignedHistory;
import me.silentdoer.demoshardbatisfenbiao.support.util.PropertiesLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 6:15 PM
 */
@Slf4j
public class ShardingStrategyImpl implements ShardStrategy {

    /**
     * 分表数量配置
     */
    private Map<String, Integer> tableShardingCountConfig = new HashMap<>(8);

    /**
     * 签到历史表
     */
    private final String TB_USER_SIGNED_HISTORY = "tb_user_signed_history";

    public ShardingStrategyImpl() {
        PropertiesLoader loader = new PropertiesLoader("classpath:properties/param.properties");
        this.tableShardingCountConfig.put(TB_USER_SIGNED_HISTORY,
                                          loader.getInteger("SIGNED_HISTORY_SHARDING_COUNT"));
    }

    /**
     * 分表规则
     * 1、所有分表必须在shard_config.xml配置mapper方法，以及本类TABLE_SHARDING_COUNT_CONFIG中指定分表个数
     * 2、分表所有mapper对应的方法参数中必须包含参数userId
     * 1) 参数为Model对象（如：int update(UserVirtualAssetDetail record)），指定userId字段名
     * 2) 参数为非对象（如：int selectCountByFromType(@Param("userId") Integer userId, @Param("fromType")Integer fromType)），参数列表中必须包含"userId"
     * 3) 手动执行分表，可用参数shardingIndex
     */
    @Override
    public String getTargetTableName(String baseTableName, Object paramObj, String mapperId) {
        final String tableName = baseTableName;
        // 异常信息
        Supplier<String> errorThrowSupplier = () -> {
            try {
                String errorInfo = String.format("mapperId=%s, params=%s, table=%s", mapperId, paramObj, tableName);
                log.warn("【分表】-路由错误，用户ID不能为空，{}", errorInfo);
                throw new Exception("userId is null in sharding, " + errorInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
        Long userId;
        if (paramObj instanceof HashMap) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (HashMap<Object, Object>) paramObj;
            // 可以通过参数shardingIndex指定分表
            String shardingIndexStr = "shardingIndex";
            if (map.containsKey(shardingIndexStr)) {
                Integer shardingIndex = (Integer) map.get(shardingIndexStr);
                Integer count = tableShardingCountConfig.get(baseTableName);
                String formatTail = StringUtils.leftPad(String.valueOf(shardingIndex), String.valueOf(count - 1).length(), "0");
                return String.format("%s_%s", tableName, formatTail);
            }
            // 用户ID参数必须定义为userId
            String userIdFieldName = "userId";
            if (!map.containsKey(userIdFieldName)) {
                return errorThrowSupplier.get();
            }
            userId = Long.parseLong(String.valueOf(map.get(userIdFieldName)));
        } else {
            userId = getUserIdFromObject(paramObj);
        }
        if (userId == null) {
            return errorThrowSupplier.get();
        }
        return getShardingTable(tableName, userId);
    }

    /**
     * 外部用户批量处理，分表规则细化
     * 例：
     * 输入："t_user_virtual_asset_detail" (13,15,56,89)
     * 返回：map {1=[13, 15], 5=[56], 8=[89]}
     */
    public Map<Integer, List<Long>> getUserShardingMap(String tableName, List<Long> uidList) {
        Integer count = this.tableShardingCountConfig.get(tableName);
        if (count == null) {
            return null;
        }
        Map<Integer, List<Long>> map = new HashMap<>(8);
        for (int i = 0; i < count; i++) {
            final int currTail = i;
            List<Long> subUidList = uidList.stream()
                    .filter(uid -> getShardTableTail(count, uid) == currTail).collect(Collectors.toList());
            if (!subUidList.isEmpty()) {
                map.put(currTail, subUidList);
            }
        }
        return map;
    }

    /**
     * 根据用户ID获取某分表的下标
     */
    private Long getShardTableTail(Integer shardingCount, Long userId) {
        // XXX 分表路由规则
        return (userId / 10) % shardingCount;
    }

    /**
     * 分表规则：用户ID千位、百位数字按对应count取余，序号从0 ~ (count - 1)
     */
    private String getShardingTable(String baseTableName, Long userId) {
        if (!this.tableShardingCountConfig.containsKey(baseTableName)) {
            return null;
        }
        Integer count = this.tableShardingCountConfig.get(baseTableName);
        Long tail = getShardTableTail(count, userId);
        String formatTail = StringUtils.leftPad(String.valueOf(tail), String.valueOf(count - 1).length(), "0");
        return String.format("%s_%s", baseTableName, formatTail);
    }

    /**
     * 分表mapper不同参数对象转换userId
     */
    private Long getUserIdFromObject(Object paramObj) {
        Long userId = null;
        if (paramObj instanceof Long) {
            userId = (Long) paramObj;
        } else if (paramObj instanceof TbUserSignedHistory) {
            userId = ((TbUserSignedHistory) paramObj).getFdUserId();
        }
        return userId;
    }
}