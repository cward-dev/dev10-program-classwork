package learn.venus;

import learn.venus.data.OrbiterFileRepository;
import learn.venus.data.OrbiterRepository;
import learn.venus.domain.OrbiterService;
import learn.venus.ui.Controller;
import learn.venus.ui.View;

public class App {

    // Repository is a dependency of service, service is depo of controller, and view is depo of controller
    public static void main(String[] args) {
        OrbiterFileRepository repository =
                new OrbiterFileRepository("./data/orbiters.csv");

        OrbiterService service = new OrbiterService(repository);

        View view = new View();

        Controller controller = new Controller(service, view);
        controller.run();
    }
}
