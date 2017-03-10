/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.red.viewers;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.robotframework.ide.eclipse.main.plugin.RedPlugin;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public class Selections {

    public static final String SELECTION = "selection";

    @SuppressWarnings("unchecked")
    public static <T> T[] getElementsArray(final IStructuredSelection selection, final Class<T> elementsClass) {
        final List<?> selectionAsList = selection.toList();
        return newArrayList(Iterables.filter(selectionAsList, elementsClass))
                .toArray((T[]) Array.newInstance(elementsClass, 0));
    }

    public static <T> List<T> getElements(final IStructuredSelection selection, final Class<T> elementsClass) {
        return newArrayList(Iterables.filter(selection.toList(), elementsClass));
    }

    public static <T> List<T> getAdaptableElements(final IStructuredSelection selection, final Class<T> elementsClass) {
        final List<?> selectionAsList = selection.toList();
        return newArrayList(Iterables.filter(transform(selectionAsList, toObjectOfClassUsingAdapters(elementsClass)),
                Predicates.notNull()));
    }

    public static <T> T getSingleElement(final IStructuredSelection selection, final Class<T> elementsClass) {
        final List<T> elements = getElements(selection, elementsClass);
        if (elements.size() == 1) {
            return elements.get(0);
        }
        throw new IllegalArgumentException("Given selection should contain only one element of class "
                + elementsClass.getName() + ", but have " + elements.size() + " instead");
    }

    public static <T> Optional<T> getOptionalFirstElement(final IStructuredSelection selection,
            final Class<T> elementsClass) {
        final List<T> elements = getElements(selection, elementsClass);
        if (!elements.isEmpty()) {
            return Optional.of(elements.get(0));
        }
        return Optional.empty();
    }

    private static <T> Function<Object, T> toObjectOfClassUsingAdapters(final Class<T> elementsClass) {
        return new Function<Object, T>() {

            @Override
            public T apply(final Object obj) {
                return RedPlugin.getAdapter(obj, elementsClass);
            }
        };
    }
}
