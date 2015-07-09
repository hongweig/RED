package org.robotframework.ide.core.testData.text.context.recognizer.settingTable;

import org.robotframework.ide.core.testData.text.lexer.RobotWordType;


public class TestTimeoutDeclaration extends ASettingTableElementRecognizer {

    public TestTimeoutDeclaration() {
        super(SettingTableRobotContextType.TABLE_SETTINGS_TEST_TIMEOUT,
                createWithAllAsMandatory(RobotWordType.TEST_WORD,
                        RobotWordType.TIMEOUT_WORD));
    }
}
