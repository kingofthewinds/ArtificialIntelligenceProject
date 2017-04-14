package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 14/04/17.
 */
public class GeneticAlgorithm {

    private List<Network> population;

    private static final double MUTATION_RATE = 0.15;
    private static final double MAX_PERTURBATION = 0.3;

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


    private void crossBreed() {

    }

    private void mutate(Network brain) {
        for (Matrix weights : brain.getWeights()) {

            for (int i = 0; i<weights.M; i++) {
                for (int j = 0; j < weights.N; j++) {
                    if (Math.random() < MUTATION_RATE) {
                        double newValue = weights.get(i, j) + Math.random() * MAX_PERTURBATION;
                        weights.set(i, j, newValue);
                    }
                }
            }
        }
    }
}
