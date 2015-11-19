/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.core.testData.model.table.testCases.mapping;

import java.util.List;
import java.util.Stack;

import org.robotframework.ide.core.testData.model.FilePosition;
import org.robotframework.ide.core.testData.model.RobotFileOutput;
import org.robotframework.ide.core.testData.model.table.testCases.TestCase;
import org.robotframework.ide.core.testData.model.table.testCases.TestCaseTags;
import org.robotframework.ide.core.testData.text.read.IRobotTokenType;
import org.robotframework.ide.core.testData.text.read.ParsingState;
import org.robotframework.ide.core.testData.text.read.RobotLine;
import org.robotframework.ide.core.testData.text.read.recognizer.RobotToken;
import org.robotframework.ide.core.testData.text.read.recognizer.RobotTokenType;


public class TestCaseTagsMapper extends ATestCaseSettingDeclarationMapper {

    public TestCaseTagsMapper() {
        super(RobotTokenType.TEST_CASE_SETTING_TAGS_DECLARATION);
    }


    @Override
    public RobotToken map(final RobotLine currentLine,
            final Stack<ParsingState> processingState,
            final RobotFileOutput robotFileOutput, final RobotToken rt, final FilePosition fp,
            final String text) {
        final List<IRobotTokenType> types = rt.getTypes();
        types.add(0, RobotTokenType.TEST_CASE_SETTING_TAGS_DECLARATION);
        rt.setText(text);
        rt.setRaw(text);

        final TestCase testCase = finder.findOrCreateNearestTestCase(currentLine,
                processingState, robotFileOutput, rt, fp);
        if (testCase.getTags().isEmpty()) {
            final TestCaseTags tags = new TestCaseTags(rt);
            testCase.addTag(tags);
        }
        processingState.push(ParsingState.TEST_CASE_SETTING_TAGS);

        return rt;
    }
}
