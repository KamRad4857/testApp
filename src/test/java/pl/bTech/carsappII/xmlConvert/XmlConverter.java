package pl.bTech.carsappII.xmlConvert;

import pl.bTech.carsappII.brands.entity.BrandEntity;
import pl.bTech.carsappII.car.entity.CarEntity;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.model.entity.ModelEntity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by kam.jar.radek@gmail.com
 */
public class XmlConverter {


    public static void main(String[] args) {
        CarEntity carEntity = new CarEntity();
        BrandEntity brandEntity = new BrandEntity();
        ColorEntity colorEntity = new ColorEntity();
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setBrand(brandEntity);
        carEntity.setColor(colorEntity);
        carEntity.setModel(modelEntity);
        carEntity.setPrice(123);
        carEntity.setName("testName");

        try {

            File file = new File("C:\\Users\\zgret\\Desktop\\testXmlCarEntity2.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(CarEntity.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(carEntity, file);
            jaxbMarshaller.marshal(carEntity, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}

