/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.db.navigation;

import org.assertj.core.api.Assertions;
import org.assertj.db.api.ChangeAssert;
import org.assertj.db.api.ChangeRowAssert;
import org.assertj.db.api.ChangesAssert;
import org.assertj.db.common.AbstractTest;
import org.assertj.db.common.NeedReload;
import org.assertj.db.type.Change;
import org.assertj.db.type.Changes;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.assertj.db.api.Assertions.assertThat;

/**
 * Tests on {@link org.assertj.db.navigation.ToRowFromChange} class :
 * {@link org.assertj.db.navigation.ToRowFromChange#rowAtEndPoint()} method.
 *
 * @author Régis Pouiller
 *
 */
public class ToRowFromChange_RowAtEndPoint_Test extends AbstractTest {

  /**
   * This method tests the {@code rowAtEndPoint} navigation method.
   */
  @Test
  @NeedReload
  public void test_row_at_end_point() throws Exception {
    Changes changes = new Changes(source).setStartPointNow();
    updateChangesForTests();
    changes.setEndPointNow();

    Field fieldPosition = ChangeAssert.class.getDeclaredField("rowPosition");
    fieldPosition.setAccessible(true);
    Field fieldRowAssert = PositionWithPoints.class.getDeclaredField("instanceAtEndPoint");
    fieldRowAssert.setAccessible(true);
    Field fieldRowFromChange = Change.class.getDeclaredField("rowAtEndPoint");
    fieldRowFromChange.setAccessible(true);
    Field fieldRowFromAssert = ChangeRowAssert.class.getDeclaredField("row");
    fieldRowFromAssert.setAccessible(true);

    ChangesAssert changesAssert = assertThat(changes);

    ChangeAssert changeCreationAssert = changesAssert.change();
    PositionWithPoints positionCreation = (PositionWithPoints) fieldPosition.get(changeCreationAssert);
    Assertions.assertThat(fieldRowAssert.get(positionCreation)).isNull();
    ChangeRowAssert changeCreationRowAssert = changeCreationAssert.rowAtEndPoint();
    Assertions.assertThat(fieldRowAssert.get(positionCreation)).isNotNull();
    ChangeRowAssert changeCreationRowAssertBis = changeCreationRowAssert.rowAtEndPoint();
    Assertions.assertThat(changeCreationRowAssert).isSameAs(changeCreationRowAssertBis);
    Assertions.assertThat(fieldRowFromAssert.get(changeCreationRowAssert)).isEqualTo(
            fieldRowFromChange.get(changes.getChangesList().get(0)));

    ChangeAssert changeModificationAssert = changesAssert.change(3);
    PositionWithPoints positionModification = (PositionWithPoints) fieldPosition.get(changeModificationAssert);
    Assertions.assertThat(fieldRowAssert.get(positionModification)).isNull();
    ChangeRowAssert changeModificationRowAssert = changeModificationAssert.rowAtEndPoint();
    Assertions.assertThat(fieldRowAssert.get(positionModification)).isNotNull();
    ChangeRowAssert changeModificationRowAssertBis = changeModificationRowAssert.rowAtEndPoint();
    Assertions.assertThat(changeModificationRowAssert).isSameAs(changeModificationRowAssertBis);
    Assertions.assertThat(fieldRowFromAssert.get(changeModificationRowAssert)).isEqualTo(
            fieldRowFromChange.get(changes.getChangesList().get(3)));

    ChangeAssert changeDeletionAssert = changesAssert.change(6);
    PositionWithPoints positionDeletion = (PositionWithPoints) fieldPosition.get(changeDeletionAssert);
    Assertions.assertThat(fieldRowAssert.get(positionDeletion)).isNull();
    ChangeRowAssert changeDeletionRowAssert = changeDeletionAssert.rowAtEndPoint();
    Assertions.assertThat(fieldRowAssert.get(positionDeletion)).isNotNull();
    ChangeRowAssert changeDeletionRowAssertBis = changeDeletionRowAssert.rowAtEndPoint();
    Assertions.assertThat(changeDeletionRowAssert).isSameAs(changeDeletionRowAssertBis);
    Assertions.assertThat(fieldRowFromAssert.get(changeDeletionRowAssert)).isNull();
  }
}
