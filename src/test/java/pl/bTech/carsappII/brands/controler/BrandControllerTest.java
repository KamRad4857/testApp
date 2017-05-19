package pl.bTech.carsappII.brands.controler;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.json.JSONException;
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
import pl.bTech.carsappII.brands.entity.BrandEntity;
import pl.bTech.carsappII.brands.repository.BrandRepository;
import pl.bTech.carsappII.car.repository.CarRepository;
import pl.bTech.carsappII.model.repository.ModelRepository;
import pl.bTech.carsappII.utilities.GeneralConfig;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class BrandControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private BrandController brandController;
    private BrandRepository brandRepository;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.brandController = this.wac.getBean(BrandController.class);
        this.brandRepository = this.wac.getBean(BrandRepository.class);
        this.wac.getBean(CarRepository.class).deleteAll();
        this.wac.getBean(ModelRepository.class).deleteAll();
        brandRepository.deleteAll();
    }

    @Test
    public void addingElementsTest() throws JSONException, IOException {

        String[] testNames = {"Ford", "Fiat", "Ferrari"};

        for (String name : testNames) {
            brandController.addBrandAction(String.format("{name:\"%s\"}", name));
        }
        String brandEntityJsonString = brandController.getAllBrandsAction();

        for (String name : testNames) {
            assertTrue(brandEntityJsonString.contains(name));
        }

        JsonArray brandEntityJsonArray = (JsonArray) new JsonParser().parse(brandEntityJsonString);
        assertEquals(brandEntityJsonArray.size(), testNames.length);

        int idToDelete = brandEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();

        brandController.deleteBrandByIdAction(idToDelete);

        brandEntityJsonString = brandController.getAllBrandsAction();
        brandEntityJsonArray = (JsonArray) new JsonParser().parse(brandEntityJsonString);

        assertEquals(testNames.length - 1, brandEntityJsonArray.size());

        int idToEdit = brandEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();
        String testEditName = "Opel";

        String editElementJsonString = brandController.editBrandAction(
                idToEdit,
                String.format("{id:\"%d\",name:\"%s\"}", idToEdit, testEditName)
        );

        List<BrandEntity> brandEntityList = brandRepository.findAll();

        assertTrue(editElementJsonString.contains(String.valueOf(idToEdit)));
        assertTrue(editElementJsonString.contains(testEditName));

        for (BrandEntity brandEntity : brandEntityList) {
            if (brandEntity.getId() == idToEdit) {
                assertEquals(testEditName, brandEntity.getName());
            }
        }
    }

    @Configuration
    public static class BrandConfig extends GeneralConfig {
    }
}
