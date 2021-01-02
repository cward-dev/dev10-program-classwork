import learn.sf.data.PanelFileRepository;
import learn.sf.domain.PanelService;
import learn.sf.ui.Controller;
import learn.sf.ui.View;

import java.time.Year;
import java.time.ZoneId;

public class App {

    public static void main(String[] args) {
        PanelFileRepository repository = new PanelFileRepository("./data/solar-panels.csv");
        PanelService service = new PanelService(repository);
        View view = new View();

        Controller controller = new Controller(service, view);
        controller.run();
    }

}
