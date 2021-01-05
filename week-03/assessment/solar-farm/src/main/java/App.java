import learn.sf.data.PanelFileRepository;
import learn.sf.domain.PanelService;
import learn.sf.ui.Controller;
import learn.sf.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        /* Without Spring Dependency Injection
        PanelFileRepository repository = new PanelFileRepository("./data/solar-panels.csv");
        PanelService service = new PanelService(repository);
        View view = new View();

        Controller controller = new Controller(service, view);
        controller.run();
        */

        ApplicationContext context = new ClassPathXmlApplicationContext("dependency-configuration.xml");

        Controller controller = context.getBean("controller", Controller.class);

        controller.run();
    }

}
