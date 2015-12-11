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

import org.assertj.db.api.*;
import org.assertj.db.common.AbstractTest;
import org.assertj.db.common.NeedReload;
import org.assertj.db.exception.AssertJDBException;
import org.assertj.db.type.*;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests on {@link org.assertj.db.navigation.ToColumn} class :
 * {@link org.assertj.db.navigation.ToColumn#column()} method.
 *
 * @author Régis Pouiller
 *
 */
public class ToColumn_Column_Test extends AbstractTest {

  /**
   * This method tests the {@code column} navigation method.
   */
  @Test
  @NeedReload
  public void test_column_from_change() throws Exception {
    Changes changes = new Changes(source).setStartPointNow();
    updateChangesForTests();
    changes.setEndPointNow();

    Field fieldIndex = ChangeAssert.class.getDeclaredField("indexNextColumn");
    fieldIndex.setAccessible(true);
    Field fieldColumnName = ChangeColumnAssert.class.getDeclaredField("columnName");
    fieldColumnName.setAccessible(true);
    Field fieldValueAtStartPoint = ChangeColumnAssert.class.getDeclaredField("valueAtStartPoint");
    fieldValueAtStartPoint.setAccessible(true);
    Field fieldValueAtEndPoint = ChangeColumnAssert.class.getDeclaredField("valueAtEndPoint");
    fieldValueAtEndPoint.setAccessible(true);

    ChangesAssert changesAssert = assertThat(changes);
    ChangeAssert changeAssert = changesAssert.change();
    assertThat(fieldIndex.get(changeAssert)).isEqualTo(0);
    ChangeColumnAssert changeColumnAssert0 = changeAssert.column();
    assertThat(fieldIndex.get(changeAssert)).isEqualTo(1);
    ChangeColumnAssert changeColumnAssert1 = changeAssert.column();
    assertThat(fieldIndex.get(changeAssert)).isEqualTo(2);
    ChangeColumnAssert changeColumnAssert2 = changeAssert.column();
    assertThat(fieldIndex.get(changeAssert)).isEqualTo(3);
    ChangeColumnAssert changeColumnAssert3 = changeAssert.column();
    assertThat(fieldIndex.get(changeAssert)).isEqualTo(4);
    ChangeColumnAssert changeColumnAssert4 = changeAssert.column();
    assertThat(fieldIndex.get(changeAssert)).isEqualTo(5);
    try {
      changeAssert.column();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      assertThat(e.getMessage()).isEqualTo("Index 5 out of the limits [0, 5[");
    }

    ChangesAssert changesAssertBis = assertThat(changes);
    ChangeAssert changeAssertBis = changesAssertBis.change();
    assertThat(fieldIndex.get(changeAssertBis)).isEqualTo(0);
    ChangeColumnAssert changeColumnAssertBis0 = changeAssertBis.column();
    assertThat(fieldIndex.get(changeAssertBis)).isEqualTo(1);
    ChangeColumnAssert changeColumnAssertBis1 = changeColumnAssertBis0.column();
    assertThat(fieldIndex.get(changeAssertBis)).isEqualTo(2);
    ChangeColumnAssert changeColumnAssertBis2 = changeColumnAssertBis1.column();
    assertThat(fieldIndex.get(changeAssertBis)).isEqualTo(3);
    ChangeColumnAssert changeColumnAssertBis3 = changeColumnAssertBis2.column();
    assertThat(fieldIndex.get(changeAssertBis)).isEqualTo(4);
    ChangeColumnAssert changeColumnAssertBis4 = changeColumnAssertBis3.column();
    assertThat(fieldIndex.get(changeAssertBis)).isEqualTo(5);
    try {
      changeColumnAssertBis4.column();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      assertThat(e.getMessage()).isEqualTo("Index 5 out of the limits [0, 5[");
    }

    assertThat(fieldColumnName.get(changeColumnAssert0)).isEqualTo(fieldColumnName.get(changeColumnAssertBis0)).isEqualTo(
            "ID");
    assertThat(fieldColumnName.get(changeColumnAssert1)).isEqualTo(fieldColumnName.get(changeColumnAssertBis1)).isEqualTo(
            "NAME");
    assertThat(fieldColumnName.get(changeColumnAssert2)).isEqualTo(fieldColumnName.get(changeColumnAssertBis2)).isEqualTo(
            "FIRSTNAME");
    assertThat(fieldColumnName.get(changeColumnAssert3)).isEqualTo(fieldColumnName.get(changeColumnAssertBis3)).isEqualTo(
            "BIRTH");
    assertThat(fieldColumnName.get(changeColumnAssert4)).isEqualTo(fieldColumnName.get(changeColumnAssertBis4)).isEqualTo(
            "ACTOR_IMDB");

    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssert0)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssert1)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssert2)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssert3)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssert4)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssertBis0)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssertBis1)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssertBis2)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssertBis3)).getValue()).isNull();
    assertThat(((Value) fieldValueAtStartPoint.get(changeColumnAssertBis4)).getValue()).isNull();

    assertThat(((Value) fieldValueAtEndPoint.get(changeColumnAssert0)).getValue()).isEqualTo(
            ((Value) fieldValueAtEndPoint.get(changeColumnAssertBis0)).getValue()).isEqualTo(new BigDecimal("4"));
    assertThat(((Value) fieldValueAtEndPoint.get(changeColumnAssert1)).getValue()).isEqualTo(
            ((Value) fieldValueAtEndPoint.get(changeColumnAssertBis1)).getValue()).isEqualTo("Murray");
    assertThat(((Value) fieldValueAtEndPoint.get(changeColumnAssert2)).getValue()).isEqualTo(
            ((Value) fieldValueAtEndPoint.get(changeColumnAssertBis2)).getValue()).isEqualTo("Bill");
    assertThat(((Value) fieldValueAtEndPoint.get(changeColumnAssert3)).getValue()).isEqualTo(
            ((Value) fieldValueAtEndPoint.get(changeColumnAssertBis3)).getValue()).isEqualTo(
            Date.valueOf("1950-09-21"));
    assertThat(((Value) fieldValueAtEndPoint.get(changeColumnAssert4)).getValue()).isEqualTo(
            ((Value) fieldValueAtEndPoint.get(changeColumnAssertBis4)).getValue()).isEqualTo(
            UUID.fromString("30B443AE-C0C9-4790-9BEC-CE1380808435"));
  }

  /**
   * This method tests the {@code column} navigation method.
   */
  @Test
  public void test_column_from_table() throws Exception {
    Field fieldPosition = AbstractDbAssert.class.getDeclaredField("columnPosition");
    fieldPosition.setAccessible(true);
    Field fieldColumn = AbstractColumnAssert.class.getDeclaredField("column");
    fieldColumn.setAccessible(true);
    Field fieldIndex = Position.class.getDeclaredField("nextIndex");
    fieldIndex.setAccessible(true);

    Table table = new Table(source, "actor");
    TableAssert tableAssert = assertThat(table);
    Position position = (Position) fieldPosition.get(tableAssert);
    assertThat(fieldIndex.get(position)).isEqualTo(0);
    TableColumnAssert tableColumnAssert0 = tableAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(1);
    TableColumnAssert tableColumnAssert1 = tableAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(2);
    TableColumnAssert tableColumnAssert2 = tableAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(3);
    TableColumnAssert tableColumnAssert3 = tableAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(4);
    TableColumnAssert tableColumnAssert4 = tableAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(5);
    try {
      tableAssert.column();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      assertThat(e.getMessage()).isEqualTo("Index 5 out of the limits [0, 5[");
    }

    TableAssert tableAssertBis = assertThat(table);
    Position positionBis = (Position) fieldPosition.get(tableAssertBis);
    assertThat(fieldIndex.get(positionBis)).isEqualTo(0);
    TableColumnAssert tableColumnAssertBis0 = tableAssertBis.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(1);
    TableColumnAssert tableColumnAssertBis1 = tableColumnAssertBis0.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(2);
    TableColumnAssert tableColumnAssertBis2 = tableColumnAssertBis1.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(3);
    TableColumnAssert tableColumnAssertBis3 = tableColumnAssertBis2.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(4);
    TableColumnAssert tableColumnAssertBis4 = tableColumnAssertBis3.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(5);
    try {
      tableColumnAssertBis4.column();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      assertThat(e.getMessage()).isEqualTo("Index 5 out of the limits [0, 5[");
    }

    Column columnId0 = (Column) fieldColumn.get(tableColumnAssert0);
    Column columnId1 = (Column) fieldColumn.get(tableColumnAssert1);
    Column columnId2 = (Column) fieldColumn.get(tableColumnAssert2);
    Column columnId3 = (Column) fieldColumn.get(tableColumnAssert3);
    Column columnId4 = (Column) fieldColumn.get(tableColumnAssert4);
    Column columnIdBis0 = (Column) fieldColumn.get(tableColumnAssertBis0);
    Column columnIdBis1 = (Column) fieldColumn.get(tableColumnAssertBis1);
    Column columnIdBis2 = (Column) fieldColumn.get(tableColumnAssertBis2);
    Column columnIdBis3 = (Column) fieldColumn.get(tableColumnAssertBis3);
    Column columnIdBis4 = (Column) fieldColumn.get(tableColumnAssertBis4);

    assertThat(columnId0.getName()).isEqualTo(columnIdBis0.getName()).isEqualTo("ID");
    assertThat(columnId1.getName()).isEqualTo(columnIdBis1.getName()).isEqualTo("NAME");
    assertThat(columnId2.getName()).isEqualTo(columnIdBis2.getName()).isEqualTo("FIRSTNAME");
    assertThat(columnId3.getName()).isEqualTo(columnIdBis3.getName()).isEqualTo("BIRTH");
    assertThat(columnId4.getName()).isEqualTo(columnIdBis4.getName()).isEqualTo("ACTOR_IMDB");

    assertThat(columnId0.getValuesList().get(0).getValue()).isEqualTo(columnIdBis0.getValuesList().get(0).getValue()).isEqualTo(new BigDecimal("1"));
    assertThat(columnId0.getValuesList().get(1).getValue()).isEqualTo(columnIdBis0.getValuesList().get(1).getValue()).isEqualTo(new BigDecimal("2"));
    assertThat(columnId0.getValuesList().get(2).getValue()).isEqualTo(columnIdBis0.getValuesList().get(2).getValue()).isEqualTo(new BigDecimal("3"));
    assertThat(columnId1.getValuesList().get(0).getValue()).isEqualTo(columnIdBis1.getValuesList().get(0).getValue()).isEqualTo("Weaver");
    assertThat(columnId1.getValuesList().get(1).getValue()).isEqualTo(columnIdBis1.getValuesList().get(1).getValue()).isEqualTo("Phoenix");
    assertThat(columnId1.getValuesList().get(2).getValue()).isEqualTo(columnIdBis1.getValuesList().get(2).getValue()).isEqualTo("Worthington");
    assertThat(columnId2.getValuesList().get(0).getValue()).isEqualTo(columnIdBis2.getValuesList().get(0).getValue()).isEqualTo("Sigourney");
    assertThat(columnId2.getValuesList().get(1).getValue()).isEqualTo(columnIdBis2.getValuesList().get(1).getValue()).isEqualTo("Joaquim");
    assertThat(columnId2.getValuesList().get(2).getValue()).isEqualTo(columnIdBis2.getValuesList().get(2).getValue()).isEqualTo("Sam");
    assertThat(columnId3.getValuesList().get(0).getValue()).isEqualTo(columnIdBis3.getValuesList().get(0).getValue()).isEqualTo(Date.valueOf("1949-10-08"));
    assertThat(columnId3.getValuesList().get(1).getValue()).isEqualTo(columnIdBis3.getValuesList().get(1).getValue()).isEqualTo(Date.valueOf("1974-10-28"));
    assertThat(columnId3.getValuesList().get(2).getValue()).isEqualTo(columnIdBis3.getValuesList().get(2).getValue()).isEqualTo(Date.valueOf("1976-08-02"));
    assertThat(columnId4.getValuesList().get(0).getValue()).isEqualTo(columnIdBis4.getValuesList().get(0).getValue()).isEqualTo(UUID.fromString("30b443ae-c0c9-4790-9bec-ce1380808435"));
    assertThat(columnId4.getValuesList().get(1).getValue()).isEqualTo(columnIdBis4.getValuesList().get(1).getValue()).isEqualTo(UUID.fromString("16319617-ae95-4087-9264-d3d21bf611b6"));
    assertThat(columnId4.getValuesList().get(2).getValue()).isEqualTo(columnIdBis4.getValuesList().get(2).getValue()).isEqualTo(UUID.fromString("d735221b-5de5-4112-aa1e-49090cb75ada"));
  }

  /**
   * This method tests the {@code column} navigation method.
   */
  @Test
  public void test_column_from_request() throws Exception {
    Field fieldPosition = AbstractDbAssert.class.getDeclaredField("columnPosition");
    fieldPosition.setAccessible(true);
    Field fieldColumn = AbstractColumnAssert.class.getDeclaredField("column");
    fieldColumn.setAccessible(true);
    Field fieldIndex = Position.class.getDeclaredField("nextIndex");
    fieldIndex.setAccessible(true);

    Request request = new Request(source, "select * from actor");
    RequestAssert requestAssert = assertThat(request);
    Position position = (Position) fieldPosition.get(requestAssert);
    assertThat(fieldIndex.get(position)).isEqualTo(0);
    RequestColumnAssert requestColumnAssert0 = requestAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(1);
    RequestColumnAssert requestColumnAssert1 = requestAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(2);
    RequestColumnAssert requestColumnAssert2 = requestAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(3);
    RequestColumnAssert requestColumnAssert3 = requestAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(4);
    RequestColumnAssert requestColumnAssert4 = requestAssert.column();
    assertThat(fieldIndex.get(position)).isEqualTo(5);
    try {
      requestAssert.column();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      assertThat(e.getMessage()).isEqualTo("Index 5 out of the limits [0, 5[");
    }

    RequestAssert requestAssertBis = assertThat(request);
    Position positionBis = (Position) fieldPosition.get(requestAssertBis);
    assertThat(fieldIndex.get(positionBis)).isEqualTo(0);
    RequestColumnAssert requestColumnAssertBis0 = requestAssertBis.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(1);
    RequestColumnAssert requestColumnAssertBis1 = requestColumnAssertBis0.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(2);
    RequestColumnAssert requestColumnAssertBis2 = requestColumnAssertBis1.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(3);
    RequestColumnAssert requestColumnAssertBis3 = requestColumnAssertBis2.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(4);
    RequestColumnAssert requestColumnAssertBis4 = requestColumnAssertBis3.column();
    assertThat(fieldIndex.get(positionBis)).isEqualTo(5);
    try {
      requestColumnAssertBis4.column();
      fail("An exception must be raised");
    } catch (AssertJDBException e) {
      assertThat(e.getMessage()).isEqualTo("Index 5 out of the limits [0, 5[");
    }

    Column columnId0 = (Column) fieldColumn.get(requestColumnAssert0);
    Column columnId1 = (Column) fieldColumn.get(requestColumnAssert1);
    Column columnId2 = (Column) fieldColumn.get(requestColumnAssert2);
    Column columnId3 = (Column) fieldColumn.get(requestColumnAssert3);
    Column columnId4 = (Column) fieldColumn.get(requestColumnAssert4);
    Column columnIdBis0 = (Column) fieldColumn.get(requestColumnAssertBis0);
    Column columnIdBis1 = (Column) fieldColumn.get(requestColumnAssertBis1);
    Column columnIdBis2 = (Column) fieldColumn.get(requestColumnAssertBis2);
    Column columnIdBis3 = (Column) fieldColumn.get(requestColumnAssertBis3);
    Column columnIdBis4 = (Column) fieldColumn.get(requestColumnAssertBis4);

    assertThat(columnId0.getName()).isEqualTo(columnIdBis0.getName()).isEqualTo("ID");
    assertThat(columnId1.getName()).isEqualTo(columnIdBis1.getName()).isEqualTo("NAME");
    assertThat(columnId2.getName()).isEqualTo(columnIdBis2.getName()).isEqualTo("FIRSTNAME");
    assertThat(columnId3.getName()).isEqualTo(columnIdBis3.getName()).isEqualTo("BIRTH");
    assertThat(columnId4.getName()).isEqualTo(columnIdBis4.getName()).isEqualTo("ACTOR_IMDB");

    assertThat(columnId0.getValuesList().get(0).getValue()).isEqualTo(columnIdBis0.getValuesList().get(0).getValue()).isEqualTo(new BigDecimal("1"));
    assertThat(columnId0.getValuesList().get(1).getValue()).isEqualTo(columnIdBis0.getValuesList().get(1).getValue()).isEqualTo(new BigDecimal("2"));
    assertThat(columnId0.getValuesList().get(2).getValue()).isEqualTo(columnIdBis0.getValuesList().get(2).getValue()).isEqualTo(new BigDecimal("3"));
    assertThat(columnId1.getValuesList().get(0).getValue()).isEqualTo(columnIdBis1.getValuesList().get(0).getValue()).isEqualTo("Weaver");
    assertThat(columnId1.getValuesList().get(1).getValue()).isEqualTo(columnIdBis1.getValuesList().get(1).getValue()).isEqualTo("Phoenix");
    assertThat(columnId1.getValuesList().get(2).getValue()).isEqualTo(columnIdBis1.getValuesList().get(2).getValue()).isEqualTo("Worthington");
    assertThat(columnId2.getValuesList().get(0).getValue()).isEqualTo(columnIdBis2.getValuesList().get(0).getValue()).isEqualTo("Sigourney");
    assertThat(columnId2.getValuesList().get(1).getValue()).isEqualTo(columnIdBis2.getValuesList().get(1).getValue()).isEqualTo("Joaquim");
    assertThat(columnId2.getValuesList().get(2).getValue()).isEqualTo(columnIdBis2.getValuesList().get(2).getValue()).isEqualTo("Sam");
    assertThat(columnId3.getValuesList().get(0).getValue()).isEqualTo(columnIdBis3.getValuesList().get(0).getValue()).isEqualTo(Date.valueOf("1949-10-08"));
    assertThat(columnId3.getValuesList().get(1).getValue()).isEqualTo(columnIdBis3.getValuesList().get(1).getValue()).isEqualTo(Date.valueOf("1974-10-28"));
    assertThat(columnId3.getValuesList().get(2).getValue()).isEqualTo(columnIdBis3.getValuesList().get(2).getValue()).isEqualTo(Date.valueOf("1976-08-02"));
    assertThat(columnId4.getValuesList().get(0).getValue()).isEqualTo(columnIdBis4.getValuesList().get(0).getValue()).isEqualTo(UUID.fromString("30b443ae-c0c9-4790-9bec-ce1380808435"));
    assertThat(columnId4.getValuesList().get(1).getValue()).isEqualTo(columnIdBis4.getValuesList().get(1).getValue()).isEqualTo(
            UUID.fromString("16319617-ae95-4087-9264-d3d21bf611b6"));
    assertThat(columnId4.getValuesList().get(2).getValue()).isEqualTo(columnIdBis4.getValuesList().get(2).getValue()).isEqualTo(
            UUID.fromString("d735221b-5de5-4112-aa1e-49090cb75ada"));
  }
}
