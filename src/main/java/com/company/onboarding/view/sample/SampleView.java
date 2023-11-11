package com.company.onboarding.view.sample;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.layer.VectorLayer;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.layer.Layer;
import io.jmix.mapsflowui.kit.component.model.source.AbstractVectorSource;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Route(value = "SampleView", layout = MainView.class)
@ViewController("SampleView")
@ViewDescriptor("sample-view.xml")
public class SampleView extends StandardView {

    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private InstanceContainer<User> userDc;

    @ViewComponent
    private GeoMap map;

    @ViewComponent
    private Span timerOutput;

    private int count;

    @Subscribe("timer")
    public void onTimerTimerAction(final Timer.TimerActionEvent event) {
        timerOutput.setText("TimerActionEvent received " + (++count) + " times");
    }

    @Subscribe
    public void onInit(InitEvent event) {
        VectorLayer vectorLayer = map.getLayer("pointsLayer");
        VectorSource vectorSource = vectorLayer.getSource();
        vectorSource.addFeature(new MarkerFeature(GeometryUtils.createPoint(12.496176, 41.902695)));
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = dataManager.load(User.class).query("e.username = ?1", "admin").one();
        userDc.setItem(user);
    }

}