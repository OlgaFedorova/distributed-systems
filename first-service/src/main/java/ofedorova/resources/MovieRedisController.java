package ofedorova.resources;

import ofedorova.redis.model.Movie;
import ofedorova.redis.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/movie-redis")
public class MovieRedisController {

    private static final Logger logger = LoggerFactory.getLogger(MovieRedisController.class);

    private final RedisRepository redisRepository;

    public MovieRedisController(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @GetMapping
    public Map<Object, Object> findAllMovies() {
        logger.info("Find all movies");
        return redisRepository.findAllMovies();
    }

    @PostMapping
    public void add(@RequestBody Movie movie) {
        logger.info("Add movie: {}", movie);
        redisRepository.add(movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")String id) {
        logger.info("Delete movie by id {}", id);
        redisRepository.delete(id);
    }

    @GetMapping("/{id}")
    public Movie findMovie(@PathVariable("id") String id) {
        logger.info("Find movie by id {}", id);
        return redisRepository.findMovie(id);
    }
}
