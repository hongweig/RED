/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.rf.ide.core.testData.text.read.recognizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.rf.ide.core.testData.text.read.recognizer.header.SettingsTableHeaderRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.DefaultTagsRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.ForceTagsRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.LibraryAliasRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.LibraryDeclarationRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.MetadataRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.ResourceDeclarationRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.SettingDocumentationRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.SuiteSetupRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.SuiteTeardownRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.TestSetupRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.TestTeardownRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.TestTemplateRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.TestTimeoutRecognizer;
import org.rf.ide.core.testData.text.read.recognizer.settings.VariableDeclarationRecognizer;


public class SettingsRecognizersProvider {

    private static final List<ATokenRecognizer> recognized = Arrays.asList(
            new SettingsTableHeaderRecognizer(), new HashCommentRecognizer(),
            new PreviousLineContinueRecognizer(),
            new LibraryDeclarationRecognizer(), new LibraryAliasRecognizer(),
            new VariableDeclarationRecognizer(),
            new ResourceDeclarationRecognizer(),

            new SettingDocumentationRecognizer(), new MetadataRecognizer(),
            new SuiteSetupRecognizer(), new SuiteTeardownRecognizer(),
            new ForceTagsRecognizer(), new DefaultTagsRecognizer(),
            new TestSetupRecognizer(), new TestTeardownRecognizer(),
            new TestTemplateRecognizer(), new TestTimeoutRecognizer());


    public List<ATokenRecognizer> getRecognizers() {
        final List<ATokenRecognizer> recognizersProvided = new ArrayList<>();
        synchronized (recognized) {
            for (final ATokenRecognizer rec : recognized) {
                recognizersProvided.add(rec.newInstance());
            }
        }
        return recognizersProvided;
    }
}
