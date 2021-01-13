package learn.house.ui.controllers;

import learn.house.domain.HostService;
import learn.house.ui.View;
import learn.house.ui.menu.HostMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerHosts {

    @Autowired
    private final HostService service;

    @Autowired
    private final View view;

    @Autowired
    public ControllerHosts(HostService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void runMenu() {
        HostMenuOption option;
        do {
            option = view.selectHostMenuOption();
            switch (option) {
                case VIEW_HOSTS:

                    break;
                case ADD_HOST:

                    break;
                case EDIT_HOST:

                    break;
                case INACTIVATE_HOST:

                    break;
            }
        } while (option != HostMenuOption.EXIT);
    }

}
