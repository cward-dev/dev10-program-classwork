package learn.memories;

import learn.memories.ui.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan // 2. Tells Spring to scan all classes in this package or its children.
@PropertySource("classpath:data.properties")
public class App {

    public static void main(String[] args) {

        /* Using Original Spring method
        ApplicationContext context = new ClassPathXmlApplicationContext("dependency-configuration.xml");

        Controller controller = context.getBean(Controller.class);
        // Run the app!
        controller.run();
         */

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        // 3. The context works the same as the XML context.
        Controller controller = context.getBean(Controller.class);
        // Run the app!
        controller.run();
    }

}