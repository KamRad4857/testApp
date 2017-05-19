package pl.bTech.carsappII.model.controller;

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
import pl.bTech.carsappII.model.repository.ModelRepository;
import pl.bTech.carsappII.utilities.GeneralConfig;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class ModelControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private ModelController modelController;
    private BrandController brandController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.modelController = this.wac.getBean(ModelController.class);
        this.brandController = this.wac.getBean(BrandController.class);
        this.wac.getBean(ModelRepository.class).deleteAll();
        this.wac.getBean(BrandRepository.class).deleteAll();
    }

    @Test
    public void controllerTest() throws Exception {
        BrandEntity testBrand = new BrandEntity();
        testBrand.setName("Opel");
        this.wac.getBean(BrandService.class).add(testBrand);

        String[] testNames = {"Astra", "Vectra", "Zafira", "Omega"};

        for (String testName : testNames) {
            modelController.addModelAction(String.format("{\"name\":\"%s\",\"brand\":%s}", testName, testBrand.getId()));
        }

        String modelEntitiesJsonString = modelController.getAllModelsAction();

        assertTrue(modelEntitiesJsonString.contains("Opel"));

        for (String testName : testNames) {
            assertTrue(modelEntitiesJsonString.contains(testName));
        }

        JsonArray modelEntityJsonArray = (JsonArray) new JsonParser().parse(modelEntitiesJsonString);

        assertEquals(testNames.length, modelEntityJsonArray.size());
        int idToDelete = modelEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();

        modelController.deleteModelByIdAction(idToDelete);

        modelEntitiesJsonString = modelController.getAllModelsAction();

        modelEntityJsonArray = (JsonArray) new JsonParser().parse(modelEntitiesJsonString);

        assertEquals(testNames.length - 1, modelEntityJsonArray.size());

        int idToEdit = modelEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();

        String testEditName = "Senator";
        String editedElementJsonString = modelController.editModelAction(
                idToEdit,
                String.format(
                        "{\"id\":%s,\"name\":\"%s\", \"brand\":%s}",
                        idToEdit,
                        testEditName,
                        testBrand.getId()
                )
        );

        List<BrandEntity> brandEntities = this.wac.getBean(BrandRepository.class).findAll();

        assertTrue(editedElementJsonString.contains(String.valueOf(idToEdit)));
        assertTrue(editedElementJsonString.contains(testEditName));

        for (BrandEntity brandEntity : brandEntities) {
            if (brandEntity.getId() == idToEdit)
                assertTrue(brandEntity.getName().equals(testEditName));
        }
    }

    @Configuration
    public static class ModelConfiguration extends GeneralConfig {
    }
}
