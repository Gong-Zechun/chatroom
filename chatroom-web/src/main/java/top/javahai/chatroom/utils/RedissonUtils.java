package top.javahai.chatroom.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedissonUtils {
  @Autowired
  RedissonClient redissonClient;

  // ========== String 操作 ==========
  public void set(String key, Object value) {
    RBucket<Object> bucket = redissonClient.getBucket(key);
    bucket.set(value);
  }

  public Object get(String key) {
    RBucket<Object> bucket = redissonClient.getBucket(key);
    return bucket.get();
  }

  public void delete(String key) {
    RBucket<Object> bucket = redissonClient.getBucket(key);
    bucket.delete();
  }

  public void expire(String key, long timeout, TimeUnit timeUnit) {
    RBucket<Object> bucket = redissonClient.getBucket(key);
    bucket.expire(timeout, timeUnit);
  }

  // ========== List 操作 ==========
  public void addToList(String key, Object... values) {
    RList<Object> list = redissonClient.getList(key);
    list.add(values);
  }

  public List<Object> getList(String key) {
    RList<Object> list = redissonClient.getList(key);
    return new ArrayList<>(list);
  }

  public void removeFromList(String key, Object value) {
    RList<Object> list = redissonClient.getList(key);
    list.remove(value);
  }

  // ========== Set 操作 ==========
  public void addToSet(String key, Object... values) {
    RSet<Object> set = redissonClient.getSet(key);
    set.add(values);
  }

  public Set<Object> getSet(String key) {
    RSet<Object> set = redissonClient.getSet(key);
    return new HashSet<>(set);
  }

  public void removeFromSet(String key, Object value) {
    RSet<Object> set = redissonClient.getSet(key);
    set.remove(value);
  }

  // ========== Hash 操作 ==========
  public void putToHash(String key, String hashKey, Object value) {
    RMap<String, Object> map = redissonClient.getMap(key);
    map.put(hashKey, value);
  }

  public Object getFromHash(String key, String hashKey) {
    RMap<String, Object> map = redissonClient.getMap(key);
    return map.get(hashKey);
  }

  public Map<String, Object> getHash(String key) {
    RMap<String, Object> map = redissonClient.getMap(key);
    return new HashMap<>(map);
  }

  public void removeFromHash(String key, String hashKey) {
    RMap<String, Object> map = redissonClient.getMap(key);
    map.remove(hashKey);
  }

  // ========== Sorted Set 操作 ==========
  public void addToSortedSet(String key, Object value, double score) {
    RScoredSortedSet<Object> sortedSet = redissonClient.getScoredSortedSet(key);
    sortedSet.add(score, value);
  }

  public Collection<Object> getSortedSet(String key) {
    RScoredSortedSet<Object> sortedSet = redissonClient.getScoredSortedSet(key);
    return sortedSet.readAll();
  }

  public Collection<Object> getSortedSet(String key, int start, int end, boolean desc) {
    RScoredSortedSet<Object> sortedSet = redissonClient.getScoredSortedSet(key);
    if (desc) {
      return sortedSet.valueRangeReversed(start, end);
    }
    return sortedSet.valueRange(start, end);
  }

  public void removeFromSortedSet(String key, Object value) {
    RScoredSortedSet<Object> sortedSet = redissonClient.getScoredSortedSet(key);
    sortedSet.remove(value);
  }

  // ========== 分布式锁操作 ==========
  public RLock getLock(String lockName) {
    return redissonClient.getLock(lockName);
  }

  public <T> T executeWithLock(String lockName, long waitTime, long leaseTime, Supplier<T> supplier) {
    RLock lock = getLock(lockName);
    try {
      if (lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {
        return supplier.get();
      } else {
        throw new RuntimeException("Failed to acquire lock: " + lockName);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Thread interrupted while trying to acquire lock: " + lockName, e);
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }
}
