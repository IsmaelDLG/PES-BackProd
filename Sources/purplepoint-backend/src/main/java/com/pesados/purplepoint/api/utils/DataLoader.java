package com.pesados.purplepoint.api.utils;

import com.pesados.purplepoint.api.PurplePointApplication;
import com.pesados.purplepoint.api.model.alarm.Alarm;
import com.pesados.purplepoint.api.model.alarm.AlarmService;
import com.pesados.purplepoint.api.model.definition.Definition;
import com.pesados.purplepoint.api.model.definition.DefinitionService;
import com.pesados.purplepoint.api.model.device.Device;
import com.pesados.purplepoint.api.model.device.DeviceService;
import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.location.LocationService;
import com.pesados.purplepoint.api.model.report.Report;
import com.pesados.purplepoint.api.model.report.ReportService;
import com.pesados.purplepoint.api.model.user.User;
import com.pesados.purplepoint.api.model.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
	private static final Logger logger = LoggerFactory.getLogger(PurplePointApplication.class);

   /*
   // Peta si guardamos imagenes tan grandes.
   @Bean
  CommandLineRunner initImageDatabase(ImageService service) throws IOException {
      logger.info("Finding resource \"sample.jpg\"");
      Resource resource = new ClassPathResource("sample.jpg");
      logger.info("Converting resource \"sample.jpg\" to byte array");
      InputStream inputStream = resource.getInputStream();
      byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
      logger.info("Saving image in \"sample.jpg\" into DB");

	  return args -> {
      logger.info("Preloading " + service.saveImage(new Image("sample.jpg","image/jpg", Base64.getEncoder().encodeToString(bdata))));
    };
  }
*/

    @Bean
  CommandLineRunner initUserDatabase(UserService service) {
	  
    return args -> {
      logger.info("Preloading " + service.saveUser(new User("test", "test1","isma@gmail.com", "1234", "others")));
      logger.info("Preloading " + service.saveUser(new User("Bilbo Baggins", "Bilbo1","testmail1@gmail.com", "1234", "female")));
      logger.info("Preloading " + service.saveUser(new User("Frodo Baggins","Frodo1" , "testmail2@gmail.com", "5678", "male")));
    };
  }
    
    @Bean
    CommandLineRunner initMapDatabase(ReportService service, UserService serviceHelper) {
      User usr = serviceHelper.saveUser(new User());
      return args -> {
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.447615, (float)2.224420, (float)100, (float)0)), usr)));
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.415026309656994, (float)2.151603698730469, (float)-1, (float)-1)), usr)));
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.425597961179896, (float) 2.1958923339843754, (float)-1, (float)-1)), usr)));
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.39993938849367, (float)2.151260375976563, (float)-1, (float)-1)), usr)));
      };
    }


    @Bean
    CommandLineRunner initLocationDatabase(LocationService service) {
        return args -> {
            logger.info("Preloading " + service.saveLocation(new Location((float)41.447612, (float)2.224417, (float)100, (float)0)));
            logger.info("Preloading " + service.saveLocation(new Location((float)41.447379, (float)2.226842, (float)100, (float)0)));
            logger.info("Preloading " + service.saveLocation(new Location((float)21.160510, (float)-86.842466, (float)100, (float)0)));
            logger.info("Preloading " + service.saveLocation(new Location((float)41.402899, (float)2.121561, (float)100, (float)0)));
        };
    }

    @Bean
    CommandLineRunner initAlarmDatabase(AlarmService service) {
        return args -> {
            // Alarma "Mi Casa"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)41.447612, (float)2.224417, (float)100, (float)0))));
            // Alarma "bar"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)41.447379, (float)2.226842, (float)100, (float)0))));
            // Alarma "bar"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)21.160510, (float)-86.842466, (float)100, (float)0))));
            // Alarma "bar"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)41.402899, (float)2.121561, (float)100, (float)0))));
        };
    }

    @Bean
    CommandLineRunner initDeviceDatabase(DeviceService service) {
        return args -> {
            // Location "Mi Casa"
            logger.info("Preloading " + service.saveDevice(new Device("cUp2XzhPe9MysvfL3J4f9O:APA91bFHVzjQGftg1Ae-kSibDwIWflLm73D-KpVzwJscLvnZrF8oyda9dKDGP3zI8YgroOyFpExbdakK_7bHWr7pzHpVSbtOtASm1dJzjUMcjfsoNyOkYSs5uUcVaoIVbL8qI90B6m0H", new Location((float)41.447612, (float)2.224417, 100, 0), new User())));
            // Location "bar"
            logger.info("Preloading " + service.saveDevice(new Device("2", new Location((float)41.447379, (float)2.226842, (float)100, (float)0), new User())));
            // Location "china"
            logger.info("Preloading " + service.saveDevice(new Device("3", new Location((float)21.160510, (float)-86.842466, (float)100, (float)0), new User())));
            // Location "otrositio"
            logger.info("Preloading " + service.saveDevice(new Device("4", new Location((float)41.402899, (float)2.121561, (float)100, (float)0), new User())));
        };
    }

    @Bean
    CommandLineRunner initDefinitionDatabase(DefinitionService service) {

        return args -> {
            logger.info("Preloading " + service.saveDefinition(new Definition("Harassment", "Sexual harassment is any form of unwelcome sexual behaviour that’s offensive, humiliating or intimidating.", "Many girls are victims of sexual harassment and violence inside and outside of school.", "en")));
            logger.info("Preloading " + service.saveDefinition(new Definition("Acoso", "El acoso sexual es cualquier forma de comportamiento sexual no deseado que es ofensivo, humillante o intimidante", "Muchas niñas son víctimas de acoso sexual y violencia dentro y fuera de la escuela.", "esp")));
            logger.info("Preloading " + service.saveDefinition(new Definition("Violencia de genero", "La violencia de género es un tipo de violencia física o psicológica ejercida contra cualquier persona o grupo de personas sobre la base de su orientación o identidad sexual, sexo o genero que impacta de manera negativa en su identidad y bienestar social, físico, psicológico o económico.", "El 2019 termina con la cifra más alta de asesinatos por violencia de género desde 2015 en España", "esp")));
            logger.info("Preloading " + service.saveDefinition(new Definition("Gender-based violance", "Gender-based violence is a type of physical or psychological violence against any person or group of persons on the basis of their sexual orientation or identity, sex or gender that negatively impacts on their social, physical, psychological or economic identity and well-being.", "2019 ends with the highest number of gender-based violence murders since 2015 in Spain", "en")));
        };
    }
}