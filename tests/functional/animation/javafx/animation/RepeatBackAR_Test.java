/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package javafx.animation;

import javafx.util.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepeatBackAR_Test extends AnimationFunctionalTestBase {
    TestSet set = new TestSet(8);
    private Timeline tt;

    public RepeatBackAR_Test() {
        super("Test behavior of Timeline.repeatCount started backward with autoReverse=true");
    }

    @Before public void setUp() {
        set.timeline.setCycleCount(3);
        set.timeline.setAutoReverse(true);

        tt = new SimpleTimeline(
                new SimpleKeyFrame(0) {
                    @Override protected void action() {
                        set.timeline.playBackward();
                    }
                },
                new SimpleKeyFrame(3500) {
                    @Override protected void action() {
                        set.check(2, 2, false, 0, 0);

                        set.resetCount();
                        set.timeline.setCycleCount(4);
                        set.timeline.playBackward();
                    }
                },
                new SimpleKeyFrame(8000) {
                    @Override protected void action() {
                        set.check(2, 3, false, set.TIME_VALUE, set.TARGET_VALUE);

                        set.resetCount();
                        set.timeline.setCycleCount(Timeline.INDEFINITE);
                        set.timeline.playBackward();
                    }
                },
                new SimpleKeyFrame(12500) {
                    @Override protected void action() {
                        set.check(2, 3, true);
                    }
                }
        );
    }

    @Test public void test() {
        tt.playFromStart();
        delayFor(tt);
    }

    @After public void cleanUp() {
        set.timeline.stop();
    }
}
