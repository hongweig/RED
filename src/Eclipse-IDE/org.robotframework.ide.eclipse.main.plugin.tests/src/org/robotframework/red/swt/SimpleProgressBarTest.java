/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.red.swt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.robotframework.red.graphics.ColorsManager;
import org.robotframework.red.junit.jupiter.FreshShell;
import org.robotframework.red.junit.jupiter.FreshShellExtension;

@ExtendWith(FreshShellExtension.class)
public class SimpleProgressBarTest {

    @FreshShell
    public Shell shell;

    @Test
    public void newlyCreatedProgressBarHaveZeroProgressAndBarOfBackgroundColor() {
        final SimpleProgressBar bar = new SimpleProgressBar(shell);
        bar.setBackground(ColorsManager.getColor(100, 110, 120));

        assertThat(bar.getProgress()).isCloseTo(0.0, withinPercentage(0.001));
        assertThat(bar.getBarColor().getRGB()).isEqualTo(new RGB(100, 110, 120));
    }

    @Test
    public void progressBarChangesBarColorAsRequested() {
        final SimpleProgressBar bar = new SimpleProgressBar(shell);
        bar.setBarColor(ColorsManager.getColor(200, 190, 180));

        assertThat(bar.getBarColor().getRGB()).isEqualTo(new RGB(200, 190, 180));
    }

    @Test
    public void progressBarHaveProperProgress_whenProgressIsReported() {
        final SimpleProgressBar bar = new SimpleProgressBar(shell);
        bar.setProgress(4, 10);

        assertThat(bar.getProgress()).isCloseTo(0.4, withinPercentage(0.001));
    }
}
