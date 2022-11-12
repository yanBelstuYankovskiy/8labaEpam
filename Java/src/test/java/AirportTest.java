import Planes.experimentalPlane;
import Planes.militaryPlane;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import Planes.militaryPlane;
import Planes.PassengerPlane;
import Planes.Plane;

import java.util.Arrays;
import java.util.List;

public class AirportTest {
    private final static List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new militaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new militaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new militaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new militaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new militaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new militaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new experimentalPlane("Bell X-14", 277, 482, 500, ExperimentalTypes.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new experimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalTypes.VTOL, ClassificationLevel.TOP_SECRET)
    );

    private static final PassengerPlane planeWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);

    @Test
    public void GetTransportMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<militaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();

        // проверка на то, что все самолеты в списке transportMilitaryPlanes - это военные самолеты
        Assert.assertTrue(transportMilitaryPlanes.stream().allMatch(militaryPlane -> militaryPlane.getType() == MilitaryType.TRANSPORT));
    }

    @Test
    public void GetPassengerPlaneWithMaxCapacity() {
        System.out.println("TEST testGetPassengerPlaneWithMaxCapacity started!");
        Airport airport = new Airport(planes);
        PassengerPlane expectedPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
         // проверка на то, что самолет с максимальной вместимостью - это самолет с максимальной вместимостью
        Assert.assertEquals(planeWithMaxPassengerCapacity, expectedPlaneWithMaxPassengersCapacity);
    }

    @Test
    public void CheckIfSortByMaxLoadCapacityWorksCorrectly() {
        Airport airport = new Airport(planes);
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();

        boolean nextPlaneMaxLoadCapacityIsHigherThanCurrent = true;
        Plane nextPlane = null;
        Plane currentPlane = null;
        for (int i = 0; i < planesSortedByMaxLoadCapacity.size() - 1; i++) {
            currentPlane = planesSortedByMaxLoadCapacity.get(i);
            nextPlane = planesSortedByMaxLoadCapacity.get(i + 1);
            if (currentPlane.getMinLoadCapacity() > nextPlane.getMinLoadCapacity()) {
                nextPlaneMaxLoadCapacityIsHigherThanCurrent = false;
                break;
            }
        }

        // проверка на то, что самолеты отсортированы по убыванию вместимости
        Assert.assertTrue(currentPlane.getMinLoadCapacity() <= nextPlane.getMinLoadCapacity());
    }

    @Test
    public void HasAtLeastOneBomberInMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List<militaryPlane> bomberMilitaryPlanes = airport.getBomberMilitaryPlanes();
        // проверка на то, что есть хотя бы один бомбардировщик
        Assert.assertTrue(bomberMilitaryPlanes.stream().anyMatch(militaryPlane -> militaryPlane.getType() == MilitaryType.BOMBER));
    }

    @Test
    public void ExperimentalPlanesHasClassificationLevelHigherThanUnclassified(){
        Airport airport = new Airport(planes);
        List<experimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean hasUnclassifiedPlanes = false;
        for(experimentalPlane experimentalPlane : experimentalPlanes){
            if(experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED){
                hasUnclassifiedPlanes = true;
                break;
            }
        }
        // проверка на то, что все экспериментальные самолеты имеют классификацию выше, чем UNCLASSIFIED
        Assert.assertTrue(experimentalPlanes.stream().allMatch(experimentalPlane -> experimentalPlane.getClassificationLevel() != ClassificationLevel.UNCLASSIFIED));
    }
}
