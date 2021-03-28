package models;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(JpaCarRepository.class)
public interface CarRepository {

    CompletionStage<Car> add(Car car);

    CompletionStage<Stream<Car>> list();

    CompletionStage<Integer> delete(Long id);

    CompletionStage<Car> getCar(Long id);

    CompletionStage<Car> updateCar(Car car);

}
