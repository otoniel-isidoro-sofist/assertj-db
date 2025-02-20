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
package org.assertj.db.api.assertions.impl;

import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Failures;
import org.assertj.db.api.AbstractAssert;
import org.assertj.db.type.Change;
import org.assertj.db.type.Value;
import org.assertj.db.util.Values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.db.error.ShouldHavePksNames.shouldHavePksNames;
import static org.assertj.db.error.ShouldHavePksValues.shouldHavePksValues;

/**
 * Implements the assertion methods on a primary key.
 *
 * @author Régis Pouiller
 * @see org.assertj.db.api.assertions.AssertOnPrimaryKey
 */
public class AssertionsOnPrimaryKey {

  /**
   * To notice failures in the assertion.
   */
  private final static Failures failures = Failures.instance();

  /**
   * Private constructor.
   */
  private AssertionsOnPrimaryKey() {
    // Empty
  }

  /**
   * Verifies that the columns og the primary key of the rows of the change is the same as the parameters.
   *
   * @param <A>       The type of the assertion which call this method.
   * @param assertion The assertion which call this method.
   * @param info      Writable information about an assertion.
   * @param change    The change.
   * @param names     The names of the primary key associated with the rows of the change.
   * @return {@code this} assertion object.
   * @throws AssertionError                 If the columns of the primary key are different to the names in parameters.
   * @throws java.lang.NullPointerException If one of the names in parameters is {@code null}.
   */
  public static <A extends AbstractAssert> A hasPksNames(A assertion, WritableAssertionInfo info, Change change,
                                                         String... names) {
    if (names == null) {
      throw new NullPointerException("Columns names must be not null");
    }

    // Create a sorted list from the primary keys columns
    List<String> pksNameList = change.getPksNameList();
    List<String> pksList = new ArrayList<>(pksNameList);
    Collections.sort(pksList);

    // Create a sorted list from the parameters
    List<String> namesList = new ArrayList<>();
    for (String name : names) {
      if (name == null) {
        throw new NullPointerException("Column name must be not null");
      }
      namesList.add(name.toUpperCase());
    }
    Collections.sort(namesList);

    // Compare each list
    if (!namesList.equals(pksList)) {
      String[] pksNames = pksNameList.toArray(new String[pksNameList.size()]);
      throw failures.failure(info, shouldHavePksNames(pksNames, names));
    }

    return assertion;
  }

  /**
   * Verifies that the values of the primary key of the rows of the change are the same as the parameters.
   *
   * @param <A>       The type of the assertion which call this method.
   * @param assertion The assertion which call this method.
   * @param info      Writable information about an assertion.
   * @param change    The change.
   * @param values    The values of the primary key associated with the rows of the change.
   * @return {@code this} assertion object.
   * @throws AssertionError If the values of the primary key are different to the values in parameters.
   */
  public static <A extends AbstractAssert> A hasPksValues(A assertion, WritableAssertionInfo info, Change change,
                                                          Object... values) {
    // Create a array from the primary keys columns
    List<Value> pksValueList = change.getPksValueList();
    Value[] pksValues = pksValueList.toArray(new Value[pksValueList.size()]);

    // If the length of the values is different than the length of the expected values
    if (values.length != pksValues.length) {
      Object[] representationsValues = Values.getRepresentationsFromValuesInFrontOfExpected(pksValues, values);
      throw failures.failure(info, shouldHavePksValues(representationsValues, values));
    }

    // Compare each list
    int index = 0;
    for (Value pkValue : pksValueList) {
      Object value = values[index];
      if (!Values.areEqual(pkValue, value)) {
        Object[] representationsValues = Values.getRepresentationsFromValuesInFrontOfExpected(pksValues, values);
        throw failures.failure(info, shouldHavePksValues(representationsValues, values));
      }
      index++;
    }

    return assertion;
  }
}
