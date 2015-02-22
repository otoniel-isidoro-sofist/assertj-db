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
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.db.api;

import org.assertj.db.common.AbstractTest;
import org.assertj.db.common.NeedReload;
import org.assertj.db.type.Changes;
import org.assertj.db.type.ValueType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Test on the type methods ({@code isOfType}, ...) on value of a change.
 *
 * @author Régis Pouiller
 *
 */
public class ChangeColumnValueAssert_Type_Test extends AbstractTest {

  /**
   * This method tests the {@code isOfType} assertion method.
   */
  @Test
  @NeedReload
  public void test_isOfType_assertion() {
    Changes changes = new Changes(source).setStartPointNow();
    updateChangesForTests();
    changes.setEndPointNow();

    assertThat(changes).change()
                       .column().valueAtEndPoint().isOfType(ValueType.NUMBER)
                       .column().valueAtEndPoint().isOfType(ValueType.TEXT)
                       .column().valueAtEndPoint().isOfType(ValueType.TEXT)
                       .column().valueAtEndPoint().isOfType(ValueType.DATE);
  }

  /**
   * This method tests the {@code isOfAnyOfTypes} assertion method.
   */
  @Test
  @NeedReload
  public void test_isOfAnyOfTypes_assertion() {
    Changes changes = new Changes(source).setStartPointNow();
    updateChangesForTests();
    changes.setEndPointNow();

    assertThat(changes).change()
                       .column().valueAtEndPoint().isOfAnyOfTypes(ValueType.NUMBER, ValueType.DATE)
                       .column().valueAtEndPoint().isOfAnyOfTypes(ValueType.TEXT)
                       .column().valueAtEndPoint().isOfAnyOfTypes(ValueType.TEXT, ValueType.TIME)
                       .column().valueAtEndPoint().isOfAnyOfTypes(ValueType.DATE);
  }

  /**
   * This method should fail because the type of the value is {@code ValueType.Number}.
   */
  @Test
  @NeedReload
  public void should_fail_isOfAnyOfTypes_assertion_because_value_is_number() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      updateChangesForTests();
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("col1 type").isOfAnyOfTypes(ValueType.BOOLEAN,
                                                                                             ValueType.DATE);

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[col1 type] \n" + "Expecting:\n" + "  <4>\n" + "to be of type\n"
                                                    + "  <[BOOLEAN, DATE]>\n" + "but was of type\n" + "  <NUMBER>");
    }
  }

  /**
   * This method should fail because the type of the value is {@code ValueType.Number}.
   */
  @Test
  @NeedReload
  public void should_fail_isOfType_assertion_because_value_is_number() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      updateChangesForTests();
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("col1 type").isOfType(ValueType.BOOLEAN);

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[col1 type] \n" +
                                                    "Expecting:\n" +
                                                    "  <4>\n" +
                                                    "to be of type\n" +
                                                    "  <BOOLEAN>\n" +
                                                    "but was of type\n" +
                                                    "  <NUMBER>");
    }
  }

  /**
   * This method tests the result of {@code isNumber}, {@code isBoolean} and others methods.
   */
  @Test
  @NeedReload
  public void test_the_types_with_the_methods_to_test_that() {
    Changes changes = new Changes(source).setStartPointNow();
    update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
           + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
           + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
    changes.setEndPointNow();

    assertThat(changes).change()
                       .column().valueAtEndPoint().as("var1").isNumber()
                       .column().valueAtEndPoint().as("var2").isBoolean()
                       .column().valueAtEndPoint().as("var3").isNumber()
                       .column().valueAtEndPoint().as("var4").isNumber()
                       .column().valueAtEndPoint().as("var5").isNumber()
                       .column().valueAtEndPoint().as("var6").isNumber()
                       .column().valueAtEndPoint().as("var7").isNumber()
                       .column().valueAtEndPoint().as("var8").isTime()
                       .column().valueAtEndPoint().as("var9").isDate()
                       .column().valueAtEndPoint().as("var10").isDateTime()
                       .column().valueAtEndPoint().as("var11").isBytes()
                       .column().valueAtEndPoint().as("var12").isText()
                       .column().valueAtEndPoint().as("var13").isNumber()
                       .column().valueAtEndPoint().as("var14").isNumber();
  }

  /**
   * This method should fail because var2 is a boolean and not a number.
   */
  @Test
  public void should_fail_because_var2_is_not_a_number() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
             + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
             + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
      changes.setEndPointNow();

      assertThat(changes).change()
                         .column().valueAtEndPoint().as("var1").isNumber()
                         .column().valueAtEndPoint().as("var2").isNumber();

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[var2] \n" +
                                                                                    "Expecting:\n" +
                                                                                    "  <false>\n" +
                                                                                    "to be of type\n" +
                                                                                    "  <NUMBER>\n" +
                                                                                    "but was of type\n" +
                                                                                    "  <BOOLEAN>");
    }
  }

  /**
   * This method should fail because var1 is a boolean and not a number.
   */
  @Test
  public void should_fail_because_var1_is_not_a_boolean() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
             + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
             + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("var1").isBoolean();

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[var1] \n" +
                                                                                    "Expecting:\n" +
                                                                                    "  <101>\n" +
                                                                                    "to be of type\n" +
                                                                                    "  <BOOLEAN>\n" +
                                                                                    "but was of type\n" +
                                                                                    "  <NUMBER>");
    }
  }

  /**
   * This method should fail because var1 is a boolean and not a time.
   */
  @Test
  public void should_fail_because_var1_is_not_a_time() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
             + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
             + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("var1").isTime();

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[var1] \n" +
                                                                                    "Expecting:\n" +
                                                                                    "  <101>\n" +
                                                                                    "to be of type\n" +
                                                                                    "  <TIME>\n" +
                                                                                    "but was of type\n" +
                                                                                    "  <NUMBER>");
    }
  }

  /**
   * This method should fail because var1 is a boolean and not a date.
   */
  @Test
  public void should_fail_because_var1_is_not_a_date() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
             + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
             + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("var1").isDate();

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[var1] \n" +
                                                                                    "Expecting:\n" +
                                                                                    "  <101>\n" +
                                                                                    "to be of type\n" +
                                                                                    "  <DATE>\n" +
                                                                                    "but was of type\n" +
                                                                                    "  <NUMBER>");
    }
  }

  /**
   * This method should fail because var1 is a boolean and not a date/time.
   */
  @Test
  public void should_fail_because_var1_is_not_a_datetime() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
             + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
             + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("var1").isDateTime();

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[var1] \n" +
                                                                                    "Expecting:\n" +
                                                                                    "  <101>\n" +
                                                                                    "to be of type\n" +
                                                                                    "  <DATE_TIME>\n" +
                                                                                    "but was of type\n" +
                                                                                    "  <NUMBER>");
    }
  }

  /**
   * This method should fail because var1 is a boolean and not a array of bytes.
   */
  @Test
  public void should_fail_because_var1_is_not_a_array_of_byte() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
             + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
             + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("var1").isBytes();

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[var1] \n" +
                                                                                    "Expecting:\n" +
                                                                                    "  <101>\n" +
                                                                                    "to be of type\n" +
                                                                                    "  <BYTES>\n" +
                                                                                    "but was of type\n" +
                                                                                    "  <NUMBER>");
    }
  }

  /**
   * This method should fail because var1 is a boolean and not a text.
   */
  @Test
  public void should_fail_because_var1_is_not_a_text() {
    try {
      Changes changes = new Changes(source).setStartPointNow();
      update("insert into test values (101, false, 26, 301, 401, 501.6, 701.8, PARSEDATETIME('12:29:50', 'HH:mm:ss'), "
             + "PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), PARSEDATETIME('30/06/2014', 'dd/MM/yyyy'), "
             + "FILE_READ('classpath:logo-dev.jpg'), 'text again', 501, 701)");
      changes.setEndPointNow();

      assertThat(changes).change().column().valueAtEndPoint().as("var1").isText();

      fail("An exception must be raised");
    }
    catch (AssertionError e) {
      assertThat(e.getLocalizedMessage()).isEqualTo("[var1] \n" +
                                                                                    "Expecting:\n" +
                                                                                    "  <101>\n" +
                                                                                    "to be of type\n" +
                                                                                    "  <TEXT>\n" +
                                                                                    "but was of type\n" +
                                                                                    "  <NUMBER>");
    }
  }
}
