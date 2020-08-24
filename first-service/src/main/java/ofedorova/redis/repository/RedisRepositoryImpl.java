package ofedorova.redis.repository;

import ofedorova.redis.model.Movie;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private static final String KEY = "Movie";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<Object, Object> findAllMovies() {
        return hashOperations.entries(KEY);
    }

    @Override
    public void add(Movie movie) {
        hashOperations.put(KEY, movie.getId(), movie.getName());
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }

    @Override
    public Movie findMovie(String id) {
        return new Movie(id, (String) hashOperations.get(KEY, id));
    }
}
