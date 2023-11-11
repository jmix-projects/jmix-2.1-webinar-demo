package com.company.onboarding.search;

import com.company.onboarding.entity.Book;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = Book.class)
public interface BookIndexDef {

    @AutoMappedField(includeProperties =
            {"title", "author", "publisher", "genre.name"})
    void bookMapping();
}
