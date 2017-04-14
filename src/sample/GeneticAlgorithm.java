package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 14/04/17.
 */
public class GeneticAlgorithm {

    private List<Network> population;

    public GeneticAlgorithm() {
        this.population = new ArrayList<>();
    }

    public void breedPopulation(List<Car> cars) {
        System.out.println("Breeding");
        for (Car car : cars) {
            population.add(car.getNetwork());
        }

    }

    public static List<Car> generateNewPopulation(int nbrPopulation, double scale, Controller controller) {
        List<Car> cars= new ArrayList<>();
        for (int i=0; i< nbrPopulation; i++) {
            Car car = new Car(1*scale+scale/2,scale/2, 0, scale, controller);
            cars.add(car);
        }
        return cars;

    }

    private void createNewGenome() {


    }

    private void crossBreed() {

    }

    private void mutate() {

    }
}
