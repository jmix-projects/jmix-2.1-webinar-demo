package com.company.onboarding.view.book;

import com.company.onboarding.entity.Book;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "books/:id", layout = MainView.class)
@ViewController("Book.detail")
@ViewDescriptor("book-detail-view.xml")
@EditedEntityContainer("bookDc")
public class BookDetailView extends StandardDetailView<Book> {

    @Subscribe
    public void onInit(InitEvent event) {

    }

}