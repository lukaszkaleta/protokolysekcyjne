package com.ra.dissection.protocol.service.support;

import com.ra.dissection.protocol.domain.common.Idable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lukasz Kaleta
 * @since 08.06.2014 17:26 GMT+1
 */
@Component
public class CollectionSupport {

    public <T extends Idable> Map<Long, T> convert(List<T> list) {
        Map<Long, T> map = new HashMap<Long, T>();
        for (T t : list) {
            map.put(t.getId(), t);
        }
        return map;
    }
}
