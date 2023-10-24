package com.company.onboarding.view.step;

import com.company.onboarding.entity.Step;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.util.RemoveOperation;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "steps", layout = MainView.class)
@ViewController("Step.list")
@ViewDescriptor("step-list-view.xml")
@LookupComponent("stepsDataGrid")
@DialogMode(width = "50em", height = "37.5em")
public class StepListView extends StandardListView<Step> {

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private RemoveOperation removeOperation;
    @ViewComponent
    private CollectionContainer<Step> stepsDc;
    @Autowired
    private DataManager dataManager;

    @Supply(to = "stepsVirtualList", subject = "renderer")
    private Renderer<Step> stepsVirtualListRenderer() {
        return new ComponentRenderer<>(step -> {
            HorizontalLayout hbox = uiComponents.create(HorizontalLayout.class);
            hbox.addClassName("steps-list");
            hbox.setAlignItems(FlexComponent.Alignment.BASELINE);

            Span nameLabel = uiComponents.create(Span.class);
            nameLabel.setText("Name: " + step.getName());
            hbox.add(nameLabel);

            Span durationLabel = uiComponents.create(Span.class);
            durationLabel.setText("Duration: " + step.getDuration());
            hbox.add(durationLabel);

            Span spacer = uiComponents.create(Span.class);
            hbox.add(spacer);
            hbox.expand(spacer);

            Button editBtn = uiComponents.create(Button.class);
            editBtn.setText("Edit");
            editBtn.addClickListener(e ->
                    viewNavigators.detailView(Step.class)
                            .editEntity(step)
                            .navigate());
            hbox.add(editBtn);

            Button removeBtn = uiComponents.create(Button.class);
            removeBtn.setText("Remove");
            removeBtn.addClickListener(e ->
                    removeOperation.builder(Step.class, this)
                            .withItems(List.of(step))
                            .withContainer(stepsDc)
                            .remove());
            hbox.add(removeBtn);

            return hbox;
        });
    }

    @Subscribe(id = "createBtn", subject = "clickListener")
    public void onCreateBtnClick(ClickEvent<JmixButton> event) {
        viewNavigators.detailView(Step.class)
                .newEntity()
                .navigate();
    }

    @Subscribe(id = "generateBtn", subject = "clickListener")
    public void onGenerateBtnClick(ClickEvent<JmixButton> event) {
        for (int i = 0; i < 120; i++) {
            Step step = dataManager.create(Step.class);
            step.setName("Step " + i);
            step.setDuration(10);
            step.setSortValue(100 + i);
            dataManager.save(step);
        }
    }
}