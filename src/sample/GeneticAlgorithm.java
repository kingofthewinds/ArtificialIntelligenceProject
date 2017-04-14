package sample;

import java.util.List;

/**
 * Created by lucas on 14/04/17.
 */
public class GeneticAlgorithm {

    private List<Network> population;

    public GeneticAlgorithm() {

    }

    public void breedPopulation(List<Car> cars) {
        for (Car car : cars) {
            population.add(car.getNetwork());
        }

    }

    public void generateNewPopulation() {

    }

    private void createNewGenome() {


    }

    private void crossBreed() {

    }

    private void mutate() {

    }
}
