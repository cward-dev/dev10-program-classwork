package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.domain.HostService;
import learn.house.ui.View;
import learn.house.ui.menu.HostMenuOption;
import learn.house.ui.menu.MainMenuOption;

public class ControllerHosts {

    private final HostService service;

    private final View view;

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
