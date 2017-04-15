package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 14/04/17.
 */
public class GeneticAlgorithm {

    private List<Network> population;
    public  int populationSize;
    private final double scale;
    private final Controller controller;
    private static final double MUTATION_RATE = 0.15;
    private static final double MAX_PERTURBATION = 0.3;

    public GeneticAlgorithm(int populationSize, double scale, Controller controller) {
        this.population = new ArrayList<>();
        this.populationSize = populationSize;
        this.scale = scale;
        this.controller = controller;
    }



    public static List<Car> generateNewPopulation(int nbrPopulation, double scale, Controller controller) {
        List<Car> cars= new ArrayList<>();
        for (int i=0; i< nbrPopulation; i++) {
            Car car = new Car(1*scale+scale/2,scale/2, 0, scale, controller);
            cars.add(car);
        }
        return cars;

    }

    public List<Car> breedPopulation(List<Car> bestCars,int iteration) {
        System.out.println("Breeding");
        double randomRate = (double)iteration/100;
        if (randomRate >= 0.20)
        {
            randomRate = 0.20;
        }
        population.clear();
        for (Car car : bestCars) {
            population.add(car.getNetwork());
        }

        List<Network> newPopulation = new ArrayList<>();

        Network bestDude = this.population.get(0);
        newPopulation.add(new Network(bestDude));
        mutate(bestDude);
        newPopulation.add(bestDude); // Keep best car
        for (int i = 0; i < population.size(); i++) {
            for (int j = i+1; j < population.size(); j++) {
                if (newPopulation.size() < (0.75+randomRate)*populationSize) {
                    crossBreed(population.get(i), population.get(j), newPopulation);
                }
                else {
                    break;
                }
            }
        }
        System.out.println("Population from previous generation :" + newPopulation.size());

        // Add some random guys
        int MHidden = bestDude.getWeightsHiddenLayer().M;
        int NHidden = bestDude.getWeightsHiddenLayer().N;
        int MOutput = bestDude.getWeightsOutputLayer().M;
        int NOutput = bestDude.getWeightsOutputLayer().N;

        while (newPopulation.size() < populationSize) {
            newPopulation.add(generateRandomNetwork(MHidden, NHidden, MOutput, NOutput));
        }
        System.out.println("pop " + newPopulation.size());

        return createCarsFromNetworks(newPopulation);
    }

    private List<Car> createCarsFromNetworks(List<Network> population) {
        List<Car> cars= new ArrayList<>();
        for (Network brain : population) {
            Car car = new Car(1*scale+scale/2,scale/2, 0, scale, controller);
            car.setBrain(brain);
            cars.add(car);
        }
        return cars;
    }

    private Network generateRandomNetwork(int MHidden, int NHidden, int MOutput, int NOutput) {
        Matrix weightsHiddenLayer = Matrix.random(MHidden,NHidden);
        Matrix weightsOutputLayer = Matrix.random(MOutput,NOutput);
        Network brain = new Network(weightsHiddenLayer, weightsOutputLayer);
        return brain;
    }



    private void crossBreed(Network parent1, Network parent2, List<Network> newPopulation ) {

        Matrix weightsHiddenParent1 = parent1.getWeightsHiddenLayer();
        Matrix weightsHiddenParent2 = parent2.getWeightsHiddenLayer();

        int MHidden = weightsHiddenParent1.M; // Parent1 and parent2 have the same size
        int NHidden = weightsHiddenParent1.N;

        Matrix weightsHiddenBaby1 = new Matrix(MHidden,NHidden);
        Matrix weightsHiddenBaby2 = new Matrix(MHidden,NHidden);

        crossOver(weightsHiddenParent1, weightsHiddenParent2, weightsHiddenBaby1, weightsHiddenBaby2);

        Matrix weightsOutputParent1 = parent1.getWeightsOutputLayer();
        Matrix weightsOutputParent2 = parent2.getWeightsOutputLayer();

        int MOutput = weightsOutputParent1.M; // Parent1 and parent2 have the same size
        int NOutput = weightsOutputParent1.N;

        Matrix weightsOutputBaby1 = new Matrix(MOutput,NOutput);
        Matrix weightsOutputBaby2 = new Matrix(MOutput,NOutput);
        crossOver(weightsOutputParent1, weightsOutputParent2, weightsOutputBaby1, weightsOutputBaby2);

        Network baby1 = new Network(weightsHiddenBaby1, weightsOutputBaby1);
        this.mutate(baby1);
        newPopulation.add(baby1);
        Network baby2 = new Network(weightsHiddenBaby2, weightsOutputBaby2);
        this.mutate(baby2);
        newPopulation.add(baby2);

    }

    private void crossOver(Matrix m1, Matrix m2, Matrix baby1, Matrix baby2) {
        int M = m1.M;
        int N = m1.N;

        int crossPoint = (int) Math.random() * N; // Select a random cross over point
        for (int i = 0; i< M; i++) {
            for (int j = 0; j < crossPoint; j++) {
                baby1.set(i,j, m1.get(i, j));
                baby2.set(i,j, m2.get(i,j));
            }
            for (int j = crossPoint; j < N; j++) {
                try {
                    baby1.set(i, j, m2.get(i, j));
                    baby2.set(i, j, m1.get(i, j));
                }catch (Exception e){
                    System.out.print("");
                }
            }
        }

    }

    private void mutate(Network brain) {
        for (Matrix weights : brain.getWeights()) {

            for (int i = 0; i<weights.M; i++) {
                for (int j = 0; j < weights.N; j++) {
                    if (Math.random() < MUTATION_RATE) {
                        double newValue = weights.get(i, j) + (Math.random()*2-1) * MAX_PERTURBATION;
                        weights.set(i, j, newValue);
                    }
                }
            }
        }
    }
}
