package io.cc.cache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nhsoft.lsd
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CcCacheEntry <V>{
    private V value;
}
