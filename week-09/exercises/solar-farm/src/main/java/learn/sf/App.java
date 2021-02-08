package learn.sf;

import learn.sf.data.PanelJdbcTemplateRepository;
import learn.sf.ui.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

//@ComponentScan
//@PropertySource("classpath:data.properties")
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        /* Manual DI
        PanelFileRepository repository = new PanelFileRepository("./data/solar-panels.csv");
        PanelService service = new PanelService(repository);
        View view = new View();

        Controller controller = new Controller(service, view);
        controller.run();
        */

        /* XML Spring DI
        ApplicationContext context = new ClassPathXmlApplicationContext("dependency-configuration.xml");
        Controller controller = context.getBean("controller", Controller.class);
        controller.run();
         */

        /* Annotation Spring DI
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        Controller controller = context.getBean(Controller.class);
        controller.run();
        */

        // SpringBootApplication
        SpringApplication.run(App.class, args);
    }
}


