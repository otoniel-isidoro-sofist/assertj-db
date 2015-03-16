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
package org.assertj.db.api.assertions.impl;

import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Failures;
import org.assertj.db.api.AbstractAssert;
import org.assertj.db.type.Change;
import org.assertj.db.type.DataType;

import static org.assertj.db.error.ShouldBeDataType.shouldBeDataType;
import static org.assertj.db.error.ShouldBeOnTable.shouldBeOnTable;

/**
 * Implements the assertion methods on the type of data (from a table or from a request).
 * <p>The different type of data are enumerated in {@link org.assertj.db.type.DataType}.</p>
 *
 * @author Régis Pouiller
 * @see org.assertj.db.api.assertions.AssertOnDataType
 */
public class AssertionsOnDataType {

  /**
   * To notice failures in the assertion.
   */
  private final static Failures failures = Failures.instance();

  /**
   * Private constructor.
   */
  private AssertionsOnDataType() {
    // Empty
  }

  /**
   * Verifies that the data type on which is the change is equal to the type in parameter.
   *
   * @param <A>       The type of the assertion which call this method.
   * @param assertion The assertion which call this method.
   * @param info      Info on the object to assert.
   * @param change    The change.
   * @param expected  The expected type to compare to.
   * @return {@code this} assertion object.
   * @throws AssertionError If the type is different to the type in parameter.
   */
  public static <A extends AbstractAssert> A isOnDataType(A assertion, WritableAssertionInfo info, Change change,
                                                          DataType expected) {
    DataType dataType = change.getDataType();
    if (dataType != expected) {
      throw failures.failure(info, shouldBeDataType(expected, dataType));
    }
    return assertion;
  }

  /**
   * Verifies that the data type on which is the change is a table.
   *
   * @param <A>       The type of the assertion which call this method.
   * @param assertion The assertion which call this method.
   * @param info      Info on the object to assert.
   * @param change    The change.
   * @return {@code this} assertion object.
   * @throws AssertionError If the type of data is not table.
   */
  public static <A extends AbstractAssert> A isOnTable(A assertion, WritableAssertionInfo info, Change change) {
    return isOnDataType(assertion, info, change, DataType.TABLE);
  }

  /**
   * Verifies that the data type on which is the change is a request.
   *
   * @param <A>       The type of the assertion which call this method.
   * @param assertion The assertion which call this method.
   * @param info      Info on the object to assert.
   * @param change    The change.
   * @return {@code this} assertion object.
   * @throws AssertionError If the type of data is not request.
   */
  public static <A extends AbstractAssert> A isOnRequest(A assertion, WritableAssertionInfo info, Change change) {
    return isOnDataType(assertion, info, change, DataType.REQUEST);
  }

  /**
   * Verifies that the change is on a table with the name in parameter.
   *
   * @param <A>       The type of the assertion which call this method.
   * @param assertion The assertion which call this method.
   * @param info      Info on the object to assert.
   * @param change    The change.
   * @param name      The name of the table on which is the change.
   * @return {@code this} assertion object.
   * @throws AssertionError                 If the type of data is not table or if the table have a different name.
   * @throws java.lang.NullPointerException If the name in parameter is {@code null}.
   */
  public static <A extends AbstractAssert> A isOnTable(A assertion, WritableAssertionInfo info, Change change,
                                                       String name) {
    if (name == null) {
      throw new NullPointerException("Table name must be not null");
    }
    isOnTable(assertion, info, change);
    String dataName = change.getDataName();
    if (!dataName.equals(name.toUpperCase())) {
      throw failures.failure(info, shouldBeOnTable(name, dataName));
    }
    return assertion;
  }
}
