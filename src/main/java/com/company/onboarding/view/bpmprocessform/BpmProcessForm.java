package com.company.onboarding.view.bpmprocessform;


import com.company.onboarding.entity.Book;
import com.company.onboarding.entity.Genre;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ProcessForm(outcomes = {
        @Outcome(id = "submit"),
        @Outcome(id = "reject")
})
@Route(value = "BpmProcessForm", layout = MainView.class)
@ViewController("BpmProcessForm")
@ViewDescriptor("bpm-process-form.xml")
public class BpmProcessForm extends StandardView {

    @Autowired
    private ProcessFormContext processFormContext;

    @ProcessVariable
    @ViewComponent
    private EntityPicker<Book> book;

    @ProcessVariable
    @ViewComponent
    private EntityPicker<Genre> genre;

    @ProcessVariable
    @ViewComponent
    private TypedTextField<String> title;

    @Subscribe("submitBtn")
    protected void onSubmitBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("submit")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("rejectBtn")
    protected void onRejectBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("reject")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }
}