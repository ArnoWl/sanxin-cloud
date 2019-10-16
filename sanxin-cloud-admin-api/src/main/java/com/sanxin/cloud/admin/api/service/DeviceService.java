package com.sanxin.cloud.admin.api.service;

import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.config.redis.RedisUtils;
import com.sanxin.cloud.dto.BTerminalVo;
import com.sanxin.cloud.entity.BDevice;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.BDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author xiaoky
 * @date 2019-10-14
 */
@Service
public class DeviceService {
    @Autowired
    private BDeviceService bDeviceService;
    /**
     * 查询充电宝详情信息
     * @param deviceId
     * @return
     */
    public Map<String, Object> getDeviceTerminalDetail(Integer deviceId) {
        BDevice device = bDeviceService.getById(deviceId);
        if (device == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        Integer size = device.getAllPort();
        Integer cellSize = 0;
        Integer cell = 0;
        try {
            if (size <= 8) {
                cellSize = 2;
                cell = size/cellSize + size % cellSize;
            } else {
                cellSize = 4;
                cell = size/cellSize + size % cellSize;
            }
        } catch (Exception e) {
            size = 0;
        }
        List<BTerminalVo> list = RedisUtils.getInstance().getTerminalByBoxId(device.getCode());
        List<BTerminalVo> returnList = new ArrayList<>();
        for (int i =0; i < size; i++) {
            final Integer slot = i + 1;
            BTerminalVo vo = null;
            Optional<BTerminalVo> optional = list.stream().filter(item -> Integer.parseInt(item.getSlot()) == slot).findFirst();
            if (optional.isPresent()) {
                // 存在
                vo = optional.get();
            }
            returnList.add(vo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("cell", cell);
        map.put("cellSize", cellSize);
        map.put("size", size);
        map.put("list", returnList);
        return map;
    }
}
