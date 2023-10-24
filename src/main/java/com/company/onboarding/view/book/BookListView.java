package com.company.onboarding.view.book;

import com.company.onboarding.entity.Book;

import com.company.onboarding.entity.Genre;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.annotation.Comment;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.facet.SettingsFacet;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import net.datafaker.Faker;
import net.datafaker.sequence.FakeSequence;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "books", layout = MainView.class)
@ViewController("Book.list")
@ViewDescriptor("book-list-view.xml")
@LookupComponent("booksDataGrid")
@DialogMode(width = "64em")
public class BookListView extends StandardListView<Book> {
    @Autowired
    private DataManager dataManager;

    private List<Genre> genres;

    @ViewComponent
    private CollectionLoader<Book> booksDl;
    @ViewComponent
    private SettingsFacet settings;
    @ViewComponent
    private JmixCheckbox testCheckbox;

    @Subscribe(id = "generateBtn", subject = "clickListener")
    public void onGenerateBtnClick(final ClickEvent<JmixButton> event) {
        Faker faker = new Faker();

        for (int i = 0; i < 140; i++) {
            Book book = dataManager.create(Book.class);
            book.setTitle(faker.book().title());
            book.setAuthor(faker.book().author());
            book.setPublisher(faker.book().publisher());
            book.setGenre(getRandomGenre());
            dataManager.save(book);
        }

        booksDl.load();
    }

    private Genre getRandomGenre() {
        if (genres == null) {
            genres = dataManager.load(Genre.class).all().list();
            if (genres.isEmpty()) {
                Faker faker = new Faker();
                for (int i = 0; i < 21; i++) {
                    Genre genre = dataManager.create(Genre.class);
                    genre.setName(faker.book().genre());
                    genres.add(dataManager.save(genre));
                }
            }
        }
        return genres.get(RandomUtils.nextInt(0, 21));
    }

    @Install(to = "settings", subject = "applySettingsDelegate")
    private void settingsApplySettingsDelegate(final SettingsFacet.SettingsContext settingsContext) {
        settings.applySettings();
        Optional<Boolean> value = settingsContext.getViewSettings().getBoolean("testCheckbox", "value");
        testCheckbox.setValue(value.orElse(Boolean.FALSE));

    }

    @Install(to = "settings", subject = "saveSettingsDelegate")
    private void settingsSaveSettingsDelegate(final SettingsFacet.SettingsContext settingsContext) {
        settingsContext.getViewSettings().put("testCheckbox", "value", testCheckbox.getValue());
        settings.saveSettings();
    }
}
