package com.company.onboarding.view.formsample;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "FormSampleView", layout = MainView.class)
@ViewController("FormSampleView")
@ViewDescriptor("form-sample-view.xml")
public class FormSampleView extends StandardView {
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private InstanceContainer<User> userDc;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = dataManager.load(User.class).query("e.username = ?1", "admin").one();
        userDc.setItem(user);
    }
}