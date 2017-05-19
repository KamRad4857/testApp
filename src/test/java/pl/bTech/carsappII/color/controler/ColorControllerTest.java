package pl.bTech.carsappII.color.controler;

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
import pl.bTech.carsappII.car.repository.CarRepository;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.color.repository.ColorRepository;
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
public class ColorControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private ColorController colorController;
    private ColorRepository colorRepository;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.colorController = this.wac.getBean(ColorController.class);
        this.colorRepository = this.wac.getBean(ColorRepository.class);
        this.wac.getBean(CarRepository.class).deleteAll();
        colorRepository.deleteAll();
    }

    @Test
    public void testAddingElements() throws IOException, JSONException {
        String[] testNames = {"Red", "Blue", "Green"};

        for (String name : testNames) {
            colorController.addColorAction(String.format("{name:\"%s\"}", name));
        }

        String colorEntityJsonString = colorController.getAllColorsAction();

        for (String name : testNames) {
            assertTrue(colorEntityJsonString.contains(name));
        }

        JsonArray colorEntityJsonArray = (JsonArray) new JsonParser().parse(colorEntityJsonString);
        assertEquals(testNames.length, colorEntityJsonArray.size());

        int idToDelete = colorEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();

        colorController.deleteColorByIdAction(idToDelete);

        colorEntityJsonString = colorController.getAllColorsAction();
        colorEntityJsonArray = (JsonArray) new JsonParser().parse(colorEntityJsonString);

        assertEquals(testNames.length - 1, colorEntityJsonArray.size());

        int idToEdit = colorEntityJsonArray.get(0).getAsJsonObject().get("id").getAsInt();
        String testNameToEdit = "Pink";
        String editElementJsonString = colorController.editColorAction(
                idToEdit,
                String.format("{id:\"%d\",name:\"%s\"}", idToEdit, testNameToEdit)
        );

        assertTrue(editElementJsonString.contains(String.valueOf(idToEdit)));
        assertTrue(editElementJsonString.contains(testNameToEdit));

        List<ColorEntity> colorEntities = colorRepository.findAll();

        for (ColorEntity colorEntity : colorEntities) {
            if (colorEntity.getId() == idToEdit) {
                assertEquals(testNameToEdit, colorEntity.getName());
            }
        }
    }

    @Configuration
    public static class BrandConfig extends GeneralConfig {
    }
}
