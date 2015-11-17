/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.source.hyperlinks;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.robotframework.ide.core.testData.text.read.recognizer.RobotToken;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.model.RobotVariable;
import org.robotframework.ide.eclipse.main.plugin.model.locators.ContinueDecision;
import org.robotframework.ide.eclipse.main.plugin.model.locators.VariableDefinitionLocator;
import org.robotframework.ide.eclipse.main.plugin.model.locators.VariableDefinitionLocator.VariableDetector;
import org.robotframework.ide.eclipse.main.plugin.project.RobotProjectConfig.ReferencedVariableFile;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.source.DocumentUtilities;

import com.google.common.base.Optional;


/**
 * @author Michal Anglart
 *
 */
public class HyperlinkToVariablesDetector implements IHyperlinkDetector {

    private final RobotSuiteFile suiteFile;

    public HyperlinkToVariablesDetector(final RobotSuiteFile suiteFile) {
        this.suiteFile = suiteFile;
    }

    @Override
    public IHyperlink[] detectHyperlinks(final ITextViewer textViewer, final IRegion region, final boolean canShowMultipleHyperlinks) {
        try {
            final Optional<IRegion> variableRegion = DocumentUtilities.findVariable(textViewer.getDocument(),
                    region.getOffset());
            if (!variableRegion.isPresent()) {
                return null;
            }
            final IRegion fromRegion = variableRegion.get();
            final String fullVariableName = textViewer.getDocument().get(fromRegion.getOffset(),
                    fromRegion.getLength());

            final List<IHyperlink> hyperlinks = newArrayList();
            new VariableDefinitionLocator(suiteFile.getFile()).locateVariableDefinitionWithLocalScope(
                    createDetector(textViewer, fromRegion, fullVariableName, hyperlinks),
                    region.getOffset());
            return hyperlinks.isEmpty() ? null : hyperlinks.toArray(new IHyperlink[0]);
        } catch (final BadLocationException e) {
            return null;
        }
    }

    private VariableDetector createDetector(final ITextViewer textViewer, final IRegion fromRegion,
            final String fullVariableName, final List<IHyperlink> hyperlinks) {
        final String variableName = fullVariableName.substring(2, fullVariableName.length() -1);
        return new VariableDetector() {

            @Override
            public ContinueDecision variableDetected(final RobotSuiteFile file, final RobotVariable variable) {
                if (variable.getName().equals(variableName)) {
                    final Position position = variable.getDefinitionPosition();
                    final IRegion destination = new Region(position.getOffset(), position.getLength());

                    final IHyperlink definitionHyperlink = file == suiteFile
                            ? new RegionsHyperlink(textViewer, fromRegion, destination)
                            : new DifferentFileHyperlink(fromRegion, file.getFile(), destination);
                    hyperlinks.add(definitionHyperlink);
                    return ContinueDecision.STOP;
                } else {
                    return ContinueDecision.CONTINUE;
                }
            }

            @Override
            public ContinueDecision localVariableDetected(final RobotSuiteFile file,
                    final RobotToken variableToken) {
                if (variableToken.getText().toString().startsWith(fullVariableName)) {
                    final IRegion destination = new Region(variableToken.getStartOffset(),
                            variableToken.getText().length());
                    hyperlinks.add(new RegionsHyperlink(textViewer, fromRegion, destination));
                    return ContinueDecision.STOP;
                } else {
                    return ContinueDecision.CONTINUE;
                }
            }

            @Override
            public ContinueDecision globalVariableDetected(final String name, final Object value) {
                // we don't want to do anything if variable is global
                return ContinueDecision.CONTINUE;
            }

            @Override
            public ContinueDecision varFileVariableDetected(final ReferencedVariableFile file,
                    final String variableName, final Object value) {
                // we don't want to do anything if variable is defined in Variable file
                return ContinueDecision.CONTINUE;
            }
        };
    }

}
