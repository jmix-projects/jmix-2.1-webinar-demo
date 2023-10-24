package com.company.onboarding.view.genre;

import com.company.onboarding.entity.Genre;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;

@Route(value = "genres", layout = MainView.class)
@ViewController("Genre.list")
@ViewDescriptor("genre-list-view.xml")
@LookupComponent("genresDataGrid")
@DialogMode(width = "64em")
public class GenreListView extends StandardListView<Genre> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Genre> genresDc;

    @ViewComponent
    private InstanceContainer<Genre> genreDc;

    @ViewComponent
    private InstanceLoader<Genre> genreDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private JmixButton saveBtn;

    @ViewComponent
    private JmixButton cancelBtn;

    @Subscribe
    public void onInit(final InitEvent event) {
        updateControls(false);
    }

    @Subscribe("genresDataGrid.create")
    public void onGenresDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Genre entity = dataContext.create(Genre.class);
        genreDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("genresDataGrid.edit")
    public void onGenresDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe(id = "saveBtn", subject = "clickListener")
    public void onSaveBtnClick(ClickEvent<JmixButton> event) {
        dataContext.save();
        genresDc.replaceItem(genreDc.getItem());
        updateControls(false);
    }

    @Subscribe(id = "cancelBtn", subject = "clickListener")
    public void onCancelBtnClick(ClickEvent<JmixButton> event) {
        dataContext.clear();
        genreDl.load();
        updateControls(false);
    }

    @Subscribe(id = "genresDc", target = Target.DATA_CONTAINER)
    public void onGenresDcItemChange(final InstanceContainer.ItemChangeEvent<Genre> event) {
        Genre entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            genreDl.setEntityId(entity.getId());
            genreDl.load();
        } else {
            genreDl.setEntityId(null);
            genreDc.setItem(null);
        }
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        saveBtn.setEnabled(editing);
        cancelBtn.setEnabled(editing);
        listLayout.setEnabled(!editing);
    }
}