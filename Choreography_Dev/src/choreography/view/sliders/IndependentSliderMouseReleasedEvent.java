/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */

package choreography.view.sliders;

import choreography.io.FCWLib;
import choreography.model.cannon.CannonEnum;
import choreography.model.fcw.FCW;
import choreography.view.ChoreographyController;
import choreography.view.music.MusicPaneController;
import choreography.model.timeline.Timeline;
import choreography.view.sim.FountainSimController;
import choreography.view.timeline.TimelineController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author elementsking
 */
public class IndependentSliderMouseReleasedEvent implements EventHandler<MouseEvent>{
    
    private final CannonEnum cannonType;
    private final IndependentCannonSliderChangeListener icscl;
    
    /**
     *
     * @param type
     * @param icscl
     */
    public IndependentSliderMouseReleasedEvent(CannonEnum type, IndependentCannonSliderChangeListener icscl) {
        this.cannonType = type;
        this.icscl = icscl;
    }

    @Override
    public void handle(MouseEvent event) {
//        ChoreographyController.getInstance().stopSliderTimer();
//        ChoreographyController.getInstance().stopTimelineTimer();
        String[] actions = new String[]{Integer.toString(icscl.getLastNumber()), cannonType.name()};
        System.out.println(cannonType);
        FCW f = FCWLib.getInstance().getFCW(cannonType.name(), actions);
        System.out.println(f.toString());
        TimelineController.getInstance().getTimeline().setWaterFcwAtPoint(
                MusicPaneController.getInstance().getTenthsTime(), f);
        FountainSimController.getInstance().acceptFcw(f);
    }
}
 