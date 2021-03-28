package models;



import play.db.jpa.JPAApi;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;


import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JpaCarRepository implements CarRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;
    private static final String DELETE_QUERY = "delete from Car c where c.id = :id";
    private static final String LIST_QUERY = "select c from Car c";
    private static final String GET_CAR_QUERY = "select c from Car c where c.id = :id";
;



    @Inject
    public JpaCarRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Car> add(Car car) {
        return supplyAsync(() -> wrap(entityManager -> insert(entityManager, car)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Car>> list() {
        return supplyAsync(() -> wrap(this::list), executionContext);
    }

    @Override
    public CompletionStage<Integer> delete(Long id) {
        return supplyAsync(() -> wrap(entityManager -> delete(entityManager, id)), executionContext);
    }

    @Override
    public CompletionStage<Car> getCar(Long id) {
        return supplyAsync(() -> wrap(entityManager -> getOneCar(entityManager, id)), executionContext);
    }

    @Override
    public CompletionStage<Car> updateCar(Car car) {
        return supplyAsync(() -> wrap(entityManager -> update(entityManager, car)), executionContext);
    }

    private Car update(EntityManager entityManager, Car car) {
        Car carFromDb = entityManager.merge(car);
        return carFromDb;
    }


    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Car getOneCar(EntityManager entityManager, Long id) {
        TypedQuery<Car> query = entityManager.createQuery(GET_CAR_QUERY, Car.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    private Car insert(EntityManager entityManager, Car car) {
        entityManager.persist(car);
        return car;
    }

    private Integer delete(EntityManager entityManager, Long id) {
        Query query = entityManager.createQuery(GET_CAR_QUERY);
        query.setParameter("id", id);
        Car carFromDb = (Car) query.getSingleResult();
        Query deleteQuery = entityManager.createQuery(DELETE_QUERY);
        deleteQuery.setParameter("id", id);
        return deleteQuery.executeUpdate();
    }

    private Stream<Car> list(EntityManager entityManager) {
        List<Car> cars = entityManager.createQuery(LIST_QUERY, Car.class).getResultList();
        return cars.stream();
    }



}
