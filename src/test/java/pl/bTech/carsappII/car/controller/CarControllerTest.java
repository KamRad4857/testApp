package pl.bTech.carsappII.car.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.bTech.carsappII.brands.controler.BrandController;
import pl.bTech.carsappII.brands.entity.BrandEntity;
import pl.bTech.carsappII.brands.repository.BrandRepository;
import pl.bTech.carsappII.brands.sevices.BrandService;
import pl.bTech.carsappII.car.repository.CarRepository;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.color.repository.ColorRepository;
import pl.bTech.carsappII.color.sevices.ColorService;
import pl.bTech.carsappII.model.controller.ModelController;
import pl.bTech.carsappII.model.entity.ModelEntity;
import pl.bTech.carsappII.model.repository.ModelRepository;
import pl.bTech.carsappII.model.service.ModelService;
import pl.bTech.carsappII.utilities.GeneralConfig;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class CarControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private ModelController modelController;
    private CarController carController;
    private BrandController brandController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.carController = this.wac.getBean(CarController.class);
        this.modelController = this.wac.getBean(ModelController.class);
        this.brandController = this.wac.getBean(BrandController.class);
        this.wac.getBean(CarRepository.class).deleteAll();
        this.wac.getBean(ModelRepository.class).deleteAll();
        this.wac.getBean(BrandRepository.class).deleteAll();
        this.wac.getBean(ColorRepository.class).deleteAll();
    }

    @Test
    public void controllerTest() throws IOException {
        BrandEntity testBrand = new BrandEntity();
        testBrand.setName("Ford");
        this.wac.getBean(BrandService.class).add(testBrand);

        ModelEntity testModel = new ModelEntity();
        testModel.setName("Mustang");
        testModel.setBrand(testBrand);
        this.wac.getBean(ModelService.class).add(testModel);

        ColorEntity testColor = new ColorEntity();
        testColor.setName("Red");
        this.wac.getBean(ColorService.class).add(testColor);

        String[] testNames = {"First", "Second", "Third", "Fourth", "Fifth"};

        for (String testName : testNames) {
            carController.addCarAction(
                    String.format("{\"name\":\"%s\"," +
                                    "\"price\":1.2," +
                                    "\"model\":{\"id\":%d,\"name\":\"Mustang\"," +
                                    "\"brand\":{\"id\":%d,\"name\":\"Ford\"}}," +
                                    "\"color\":{\"id\":%d,\"name\":\"Pink\"}}",
                            testName,
                            testModel.getId(),
                            testBrand.getId(),
                            testColor.getId()));
        }

        String carEntitiesJsonString = carController.getAllCarsAction();

        for (String testName : testNames) {
            assertTrue(carEntitiesJsonString.contains(testName));
        }

        JsonArray carEntityJsonArray = (JsonArray) new JsonParser().parse(carEntitiesJsonString);

        assertEquals(testNames.length, carEntityJsonArray.size());

        int idToDelete = carEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();

        carController.deleteCarByIdAction(idToDelete);
        carEntitiesJsonString = carController.getAllCarsAction();
        carEntityJsonArray = (JsonArray) new JsonParser().parse(carEntitiesJsonString);

        assertEquals(testNames.length - 1, carEntityJsonArray.size());

        int idToEdit = carEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();

        BrandEntity testBrand2 = new BrandEntity();
        testBrand2.setName("Chrysler");
        this.wac.getBean(BrandService.class).add(testBrand2);

        ModelEntity testModel2 = new ModelEntity();
        testModel2.setName("New Yorker");
        testModel2.setBrand(testBrand2);
        this.wac.getBean(ModelService.class).add(testModel2);

        ColorEntity testColor2 = new ColorEntity();
        testColor2.setName("Black");
        this.wac.getBean(ColorService.class).add(testColor2);

        String testName = "Sixth";

        carController.editCarAction(
                idToEdit,
                String.format("{\"id\":%d, \"name\":\"%s\"," +
                                "\"price\":1.2," +
                                "\"model\":{\"id\":%d,\"name\":\"%s\"," +
                                "\"brand\":{\"id\":%d,\"name\":\"%s\"}}," +
                                "\"color\":{\"id\":%d,\"name\":\"%s\"}}",
                        idToEdit, testName,
                        testModel2.getId(), testModel2.getName(),
                        testBrand2.getId(), testBrand2.getName(),
                        testColor2.getId(), testColor2.getName())
        );

        carEntitiesJsonString = carController.getAllCarsAction();

        assertTrue(carEntitiesJsonString.contains(testName));
        assertTrue(carEntitiesJsonString.contains(testBrand2.getName()));
        assertTrue(carEntitiesJsonString.contains(testModel2.getName()));
        assertTrue(carEntitiesJsonString.contains(testColor2.getName()));
    }

    @Configuration
    public static class CarConfiguration extends GeneralConfig {
    }
}
