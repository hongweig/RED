package org.robotframework.ide.eclipse.main.plugin.tableeditor;

import org.robotframework.ide.eclipse.main.plugin.RobotElement;
import org.robotframework.ide.eclipse.main.plugin.RobotSuiteFile;
import org.robotframework.ide.eclipse.main.plugin.RobotSuiteFileSection;

public interface ISectionEditorPart {

    void updateOnActivation();

    com.google.common.base.Optional<RobotElement> provideSection(RobotSuiteFile suiteModel);

    boolean isPartFor(RobotSuiteFileSection section);

    void revealElement(RobotElement robotElement);

    void setFocus();

}
