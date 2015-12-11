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
import org.assertj.db.api.ChangesAssert;
import org.assertj.db.common.AbstractTest;
import org.assertj.db.common.NeedReload;
import org.assertj.db.exception.AssertJDBException;
import org.assertj.db.type.ChangeType;
import org.assertj.db.type.Changes;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests on {@link ToChange} class :
 * {@link ToChange#change()} method.
 *
 * @author Régis Pouiller
 *
 */
public class ToChange_Change_Test extends AbstractTest {

  /**
   * This method tests the {@code change} navigation method.
   */
  @Test
  @NeedReload
  public void test_change() throws Exception {
    Changes changes = new Changes(source).setStartPointNow();
    updateChangesForTests();
    changes.setEndPointNow();

    Field fieldList = Changes.class.getDeclaredField("changesList");
    fieldList.setAccessible(true);
    Field fieldIndex = ChangesAssert.class.getDeclaredField("indexNextChangeMap");
    fieldIndex.setAccessible(true);
    Field fieldChange = ChangeAssert.class.getDeclaredField("change");
    fieldChange.setAccessible(true);

    ChangesAssert changesAssert = assertThat(changes);
    Map<ChangeType, Map<String, Integer>> map = (Map<ChangeType, Map<String, Integer>>)fieldIndex.get(changesAssert);
    assertThat(map).hasSize(0);
    assertThat(map.get(null)).isNull();
    ChangeAssert changeAssert0 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(1);
    ChangeAssert changeAssert1 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(2);
    ChangeAssert changeAssert2 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(3);
    ChangeAssert changeAssert3 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(4);
    ChangeAssert changeAssert4 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(5);
    ChangeAssert changeAssert5 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(6);
    ChangeAssert changeAssert6 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(7);
    ChangeAssert changeAssert7 = changesAssert.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(8);
    try {
      changesAssert.change();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      Assertions.assertThat(e.getMessage()).isEqualTo("Index 8 out of the limits [0, 8[");
    }

    ChangesAssert changesAssertBis = assertThat(changes);
    map = (Map<ChangeType, Map<String, Integer>>)fieldIndex.get(changesAssertBis);
    assertThat(map).hasSize(0);
    assertThat(map.get(null)).isNull();
    ChangeAssert changeAssertBis0 = changesAssertBis.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(1);
    ChangeAssert changeAssertBis1 = changeAssertBis0.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(2);
    ChangeAssert changeAssertBis2 = changeAssertBis1.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(3);
    ChangeAssert changeAssertBis3 = changeAssertBis2.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(4);
    ChangeAssert changeAssertBis4 = changeAssertBis3.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(5);
    ChangeAssert changeAssertBis5 = changeAssertBis4.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(6);
    ChangeAssert changeAssertBis6 = changeAssertBis5.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(7);
    ChangeAssert changeAssertBis7 = changeAssertBis6.change();
    assertThat(map).hasSize(1);
    assertThat(map.get(null)).hasSize(1);
    assertThat(map.get(null).get(null)).isEqualTo(8);
    try {
      changeAssertBis7.change();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      Assertions.assertThat(e.getMessage()).isEqualTo("Index 8 out of the limits [0, 8[");
    }

    List<Changes> changesList = (List<Changes>) fieldList.get(changes);
    assertThat(fieldChange.get(changeAssert0)).isSameAs(fieldChange.get(changeAssertBis0)).isSameAs(changesList.get(0));
    assertThat(fieldChange.get(changeAssert1)).isSameAs(fieldChange.get(changeAssertBis1)).isSameAs(changesList.get(1));
    assertThat(fieldChange.get(changeAssert2)).isSameAs(fieldChange.get(changeAssertBis2)).isSameAs(changesList.get(2));
    assertThat(fieldChange.get(changeAssert3)).isSameAs(fieldChange.get(changeAssertBis3)).isSameAs(changesList.get(3));
    assertThat(fieldChange.get(changeAssert4)).isSameAs(fieldChange.get(changeAssertBis4)).isSameAs(changesList.get(4));
    assertThat(fieldChange.get(changeAssert5)).isSameAs(fieldChange.get(changeAssertBis5)).isSameAs(changesList.get(5));
    assertThat(fieldChange.get(changeAssert6)).isSameAs(fieldChange.get(changeAssertBis6)).isSameAs(changesList.get(6));
    assertThat(fieldChange.get(changeAssert7)).isSameAs(fieldChange.get(changeAssertBis7)).isSameAs(changesList.get(7));
  }
}
