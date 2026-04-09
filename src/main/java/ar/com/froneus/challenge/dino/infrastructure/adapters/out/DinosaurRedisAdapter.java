package ar.com.froneus.challenge.dino.infrastructure.adapters.out;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ar.com.froneus.challenge.dino.application.ports.out.IEvictDinosaurFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaurByIdFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.IFindDinosaursFromCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaurByIdInCachePort;
import ar.com.froneus.challenge.dino.application.ports.out.ISaveDinosaursInCachePort;
import ar.com.froneus.challenge.dino.domain.model.Dinosaur;
import ar.com.froneus.challenge.dino.application.query.DinosaurFilter;

@Repository
public class DinosaurRedisAdapter implements IFindDinosaursFromCachePort, ISaveDinosaursInCachePort,
        IFindDinosaurByIdFromCachePort, ISaveDinosaurByIdInCachePort, IEvictDinosaurFromCachePort {

    private static final String KEY_PREFIX = "dinosaurs:";
    private static final String KEY_PREFIX_BY_ID = "dinosaur:";
    private static final Duration TTL = Duration.ofMinutes(5);

    private final RedisTemplate<String, Object> redisTemplate;

    public DinosaurRedisAdapter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<List<Dinosaur>> findAll(DinosaurFilter filter) {
        String key = buildKey(filter);
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached instanceof List<?> list) {
            return Optional.of((List<Dinosaur>) list);
        }
        return Optional.empty();
    }

    @Override
    public void save(DinosaurFilter filter, List<Dinosaur> dinosaurs) {
        String key = buildKey(filter);
        redisTemplate.opsForValue().set(key, new java.util.ArrayList<>(dinosaurs), TTL);
    }

    @Override
    public Optional<Dinosaur> findById(Long id) {
        Object cached = redisTemplate.opsForValue().get(KEY_PREFIX_BY_ID + id);
        if (cached instanceof Dinosaur dinosaur) {
            return Optional.of(dinosaur);
        }
        return Optional.empty();
    }

    @Override
    public void save(Long id, Dinosaur dinosaur) {
        redisTemplate.opsForValue().set(KEY_PREFIX_BY_ID + id, dinosaur, TTL);
    }

    @Override
    public void evict(Long id) {
        redisTemplate.delete(KEY_PREFIX_BY_ID + id);
        evictLists();
    }

    @Override
    public void evictLists() {
        var listKeys = redisTemplate.keys(KEY_PREFIX + "*");
        if (listKeys != null && !listKeys.isEmpty()) {
            redisTemplate.delete(listKeys);
        }
    }

    private String buildKey(DinosaurFilter filter) {
        return KEY_PREFIX
                + "species=" + (filter.getSpecies() != null ? filter.getSpecies() : "")
                + "_df=" + (filter.getDiscoveryDateFrom() != null ? filter.getDiscoveryDateFrom() : "")
                + "_dt=" + (filter.getDiscoveryDateTo() != null ? filter.getDiscoveryDateTo() : "")
                + "_ef=" + (filter.getExtinctionDateFrom() != null ? filter.getExtinctionDateFrom() : "")
                + "_et=" + (filter.getExtinctionDateTo() != null ? filter.getExtinctionDateTo() : "")
                + "_l=" + filter.getLimit()
                + "_o=" + filter.getOffset();
    }
}
