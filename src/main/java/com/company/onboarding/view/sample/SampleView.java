package com.company.onboarding.view.sample;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Route(value = "SampleView", layout = MainView.class)
@ViewController("SampleView")
@ViewDescriptor("sample-view.xml")
public class SampleView extends StandardView {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @ViewComponent
    private Span timerOutput;

    private int count;

    @Subscribe(id = "authBtn", subject = "clickListener")
    public void onAuthBtnClick(final ClickEvent<JmixButton> event) {
        List<String> list = currentAuthentication.getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        System.out.println(list);
    }

    @Subscribe("timer")
    public void onTimerTimerAction(final Timer.TimerActionEvent event) {
        timerOutput.setText("TimerActionEvent received " + (++count) + " times");
    }
}